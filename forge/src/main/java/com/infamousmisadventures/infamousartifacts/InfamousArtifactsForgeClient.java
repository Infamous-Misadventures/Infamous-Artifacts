package com.infamousmisadventures.infamousartifacts;

import com.infamousmisadventures.infamousartifacts.client.gui.screens.inventory.PlayerArtifactStorageScreen;
import com.infamousmisadventures.infamousartifacts.registry.IAMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;

public class InfamousArtifactsForgeClient {

    public static void setupClient() {
        MenuScreens.register(IAMenuTypes.PLAYER_ARTIFACT_STORAGE_MENU.get(), PlayerArtifactStorageScreen::new);
    }
}
