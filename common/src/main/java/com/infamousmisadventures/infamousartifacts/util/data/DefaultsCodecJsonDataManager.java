package com.infamousmisadventures.infamousartifacts.util.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class DefaultsCodecJsonDataManager<T> extends CodecJsonDataManager<T> {

    private final Map<ResourceLocation, T> defaults = new HashMap<>();

    public DefaultsCodecJsonDataManager(ResourceLocation dataName, String folderName, Codec<T> codec) {
        super(dataName, folderName, codec);
    }

    public DefaultsCodecJsonDataManager(ResourceLocation dataName, String folderName, Codec<T> codec, Gson gson) {
        super(dataName, folderName, codec, gson);
    }


    public void addDefault(ResourceLocation resourceLocation, T object) {
        defaults.put(resourceLocation, object);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        super.apply(jsons, resourceManager, profiler);
        defaults.forEach((resourceLocation, t) -> this.data.putIfAbsent(resourceLocation, t));
    }
}