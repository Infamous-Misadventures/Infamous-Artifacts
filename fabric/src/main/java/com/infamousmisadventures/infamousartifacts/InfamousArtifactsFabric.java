package com.infamousmisadventures.infamousartifacts;

import com.infamousmisadventures.infamousartifacts.datapack.CodecDataManagerSync;
import com.infamousmisadventures.infamousartifacts.network.packets.ArtifactGearConfigSyncPacket;
import net.fabricmc.api.ModInitializer;

import static com.infamousmisadventures.infamousartifacts.registry.ArtifactGearConfigRegistry.ARTIFACT_GEAR_CONFIGS;

public class InfamousArtifactsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        InfamousArtifacts.init();
        setupDatapackFormats();
    }

    private void setupDatapackFormats() {
        CodecDataManagerSync.subscribeAsSyncable(ArtifactGearConfigSyncPacket::new, ARTIFACT_GEAR_CONFIGS);
    }
}
