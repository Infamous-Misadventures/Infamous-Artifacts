package com.infamousmisadventures.infamousartifacts.datapack;

import com.infamousmisadventures.infamousartifacts.registry.ArtifactGearConfigRegistry;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.List;

public class DatapackReloadListener {

    public static List<PreparableReloadListener> reloadListeners() {
        return List.of(
                ArtifactGearConfigRegistry.ARTIFACT_GEAR_CONFIGS
        );
    }
}
