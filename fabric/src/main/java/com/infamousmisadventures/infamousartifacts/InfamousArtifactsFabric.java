package com.infamousmisadventures.infamousartifacts;

import net.fabricmc.api.ModInitializer;

import static com.infamousmisadventures.infamousartifacts.platform.Services.PLATFORM;

public class InfamousArtifactsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        InfamousArtifacts.init();
    }
}
