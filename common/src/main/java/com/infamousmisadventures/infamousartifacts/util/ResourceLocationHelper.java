package com.infamousmisadventures.infamousartifacts.util;

import net.minecraft.resources.ResourceLocation;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;

public class ResourceLocationHelper {

    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
