package com.infamousmisadventures.infamousartifacts.datapack;

import com.infamousmisadventures.infamousartifacts.registry.ArtifactGearConfigRegistry;
import net.minecraftforge.event.AddReloadListenerEvent;

public class DatapackReloadListener{

    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(ArtifactGearConfigRegistry.ARTIFACT_GEAR_CONFIGS);
    }
}
