package com.infamousmisadventures.advancedanimationutil;

import net.fabricmc.api.ModInitializer;

public class AdvancedAnimationUtilFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        AdvancedAnimationUtil.init();
    }
}
