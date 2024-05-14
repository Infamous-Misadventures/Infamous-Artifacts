package com.infamousmisadventures.infamousartifacts.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.infamousmisadventures.infamousartifacts.Constants.MOD_ID;
import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class ItemTagWrappers {

    public static final TagKey<Item> ARTIFACT_REPAIR_ITEMS = tag(modLoc("artifact_repair_items"));

    private static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, name));
    }

    private static TagKey<Item> tag(ResourceLocation resourceLocation) {
        return TagKey.create(Registries.ITEM, resourceLocation);
    }

    public static void init() {
    }
}