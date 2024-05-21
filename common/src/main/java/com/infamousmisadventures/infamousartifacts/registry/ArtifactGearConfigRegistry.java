package com.infamousmisadventures.infamousartifacts.registry;

import com.infamousmisadventures.infamousartifacts.item.artifact.config.ArtifactGearConfig;
import com.infamousmisadventures.infamousartifacts.util.data.CodecJsonDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;


public class ArtifactGearConfigRegistry {
    public static final ResourceLocation ARTIFACT_GEAR_CONFIG_RESOURCELOCATION = new ResourceLocation(MOD_ID, "artifact_gear_config");

    public static final CodecJsonDataManager<ArtifactGearConfig> ARTIFACT_GEAR_CONFIGS = new CodecJsonDataManager<>(ARTIFACT_GEAR_CONFIG_RESOURCELOCATION, "artifact_config", ArtifactGearConfig.CODEC);


    public static ArtifactGearConfig getConfig(ResourceLocation resourceLocation) {
        return ARTIFACT_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, ArtifactGearConfig.DEFAULT);
    }

    public static boolean gearConfigExists(ResourceLocation resourceLocation) {
        return ARTIFACT_GEAR_CONFIGS.getData().containsKey(resourceLocation);
    }

    public static void setData(Map<ResourceLocation, ArtifactGearConfig> data) {
        ARTIFACT_GEAR_CONFIGS.setData(data);
    }
}