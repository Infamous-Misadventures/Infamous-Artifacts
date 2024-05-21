package com.infamousmisadventures.infamousartifacts.item.artifact.config;

import com.infamousmisadventures.infamousartifacts.item.artifact.AbstractArtifact;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import static net.minecraft.core.registries.BuiltInRegistries.ITEM;

public class GearConfigReloadListener implements ResourceManagerReloadListener {

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        reloadAllItems();
    }

    public static void reloadAllItems() {
        ITEM.stream().filter(registryKeyItemEntry -> registryKeyItemEntry.asItem() instanceof AbstractArtifact).map(registryKeyItemEntry -> (AbstractArtifact) registryKeyItemEntry.asItem()).forEach(AbstractArtifact::reload);
    }
}