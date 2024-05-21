/*

The MIT License (MIT)

Copyright (c) 2020 Joseph Bettendorff a.k.a. "Commoble"

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */

package com.infamousmisadventures.infamousartifacts.util.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * Codec-based data manager for loading data.
 * This works best if initialized during your mod's construction.
 * After creating the manager, subscribeAsSyncable can optionally be called on it to subscribe the manager
 * to the forge events necessary for syncing datapack data to clients.
 *
 * @param <T> The type of the objects that the codec is parsing jsons as
 */
public class CodecJsonDataManager<T> extends SimpleJsonResourceReloadListener implements CodecDataManager<T> {
    // default gson if unspecified
    private static final Gson STANDARD_GSON = new Gson();
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * The codec we use to convert jsonelements to Ts
     **/
    private final Codec<T> codec;

    private final ResourceLocation dataName;
    private final String folderName;

    /**
     * The raw data that we parsed from json last time resources were reloaded
     **/
    protected Map<ResourceLocation, T> data = new HashMap<>();

    /**
     * Creates a data manager with a standard gson parser
     *
     * @param folderName The name of the data folder that we will load from, vanilla folderNames are "recipes", "loot_tables", etc<br>
     *                   Jsons will be read from data/all_modids/folderName/all_jsons<br>
     *                   folderName can include subfolders, e.g. "some_mod_that_adds_lots_of_data_loaders/cheeses"
     * @param codec      A codec to deserialize the json into your T, see javadocs above class
     */
    public CodecJsonDataManager(ResourceLocation dataName, String folderName, Codec<T> codec) {
        this(dataName, folderName, codec, STANDARD_GSON);
    }

    /**
     * As above but with a custom GSON
     *
     * @param folderName The name of the data folder that we will load from, vanilla folderNames are "recipes", "loot_tables", etc<br>
     *                   Jsons will be read from data/all_modids/folderName/all_jsons<br>
     *                   folderName can include subfolders, e.g. "some_mod_that_adds_lots_of_data_loaders/cheeses"
     * @param codec      A codec to deserialize the json into your T, see javadocs above class
     * @param gson       A gson for parsing the raw json data into JsonElements. JsonElement-to-T conversion will be done by the codec,
     *                   so gson type adapters shouldn't be necessary here
     */
    public CodecJsonDataManager(ResourceLocation dataName, String folderName, Codec<T> codec, Gson gson) {
        super(gson, folderName);
        this.dataName = dataName;
        this.folderName = folderName; // superclass has this but it's a private field
        this.codec = codec;
    }

    /**
     * @return The data entries
     */
    public Map<ResourceLocation, T> getData() {
        return this.data;
    }

    public void setData(Map<ResourceLocation, T> data) {
        this.data = data;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        LOGGER.info("Beginning loading of data for data loader: {}", this.folderName);
        Map<ResourceLocation, T> newMap = new HashMap<>();

        for (Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonElement element = entry.getValue();
            // if we fail to parse json, log an error and continue
            // if we succeeded, add the resulting T to the map
            this.codec.decode(JsonOps.INSTANCE, element)
                    .get()
                    .ifLeft(result -> newMap.put(key, result.getFirst()))
                    .ifRight(partial -> LOGGER.error("Failed to parse data json for {} due to: {}", key, partial.message()));
        }

        this.data = newMap;
        LOGGER.info("Data loader for {} loaded {} jsons", this.folderName, this.data.size());
    }


    public Codec<T> byNameCodec() {
        Codec<T> codec = ResourceLocation.CODEC.flatXmap((p_206084_) -> {
            return Optional.ofNullable(this.data.get(p_206084_)).map(DataResult::success).orElseGet(() -> {
                return DataResult.error(() -> "Unknown data registry key in " + this.folderName + ": " + p_206084_);
            });
        }, (p_206094_) -> {
            return this.getResourceKey(p_206094_).map(ResourceKey::location).map(DataResult::success).orElseGet(() -> {
                return DataResult.error(() -> "Unknown registry element in " + this.folderName + ":" + p_206094_);
            });
        });
        return codec;
//        Codec<T> codec1 = ExtraCodecs.idResolverCodec((p_235816_) -> {
//            return this.getResourceKey(p_235816_).isPresent() ? this.getId(p_235816_) : -1;
//        }, this::byId, -1);
//        return ExtraCodecs.overrideLifecycle(ExtraCodecs.orCompressed(codec, codec1), this::lifecycle, (p_235810_) -> {
//            return this.lifecycle;
//        });
    }

    private Optional<ResourceKey<T>> getResourceKey(T p_206094_) {
        return this.data.entrySet().stream().filter((p_206092_) -> {
            return p_206092_.getValue() == p_206094_;
        }).map(Entry::getKey).map(resourceLocation -> (ResourceKey<T>) ResourceKey.create(ResourceKey.createRegistryKey(this.dataName), resourceLocation)).findFirst();
    }
}