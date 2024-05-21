package com.infamousmisadventures.infamousartifacts;

import com.infamousmisadventures.infamousartifacts.datapack.CodecDataManagerSync;
import com.infamousmisadventures.infamousartifacts.network.NetworkHandler;
import com.infamousmisadventures.infamousartifacts.network.message.ArtifactGearConfigSyncPacket;
import com.infamousmisadventures.infamousartifacts.registry.ArtifactGearConfigRegistry;
import net.fabricmc.api.ModInitializer;

public class InfamousArtifactsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        InfamousArtifacts.init();
        setupDatapackFormats();
        NetworkHandler.init();
    }

    private void setupDatapackFormats() {
        CodecDataManagerSync.subscribeAsSyncable(NetworkHandler.INSTANCE, ArtifactGearConfigSyncPacket::new, ArtifactGearConfigRegistry.ARTIFACT_GEAR_CONFIGS);
    }
}
