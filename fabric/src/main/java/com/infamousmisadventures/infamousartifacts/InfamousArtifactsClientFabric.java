package com.infamousmisadventures.infamousartifacts;

import com.infamousmisadventures.infamousartifacts.client.gui.screens.inventory.PlayerArtifactStorageScreen;
import com.infamousmisadventures.infamousartifacts.platform.Services;
import com.infamousmisadventures.infamousartifacts.registry.IAMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class InfamousArtifactsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(IAMenuTypes.PLAYER_ARTIFACT_STORAGE_MENU.get(), PlayerArtifactStorageScreen::new);
    }
}
