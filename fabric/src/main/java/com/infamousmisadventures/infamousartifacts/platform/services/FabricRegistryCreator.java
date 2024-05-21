package com.infamousmisadventures.infamousartifacts.platform.services;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class FabricRegistryCreator implements IRegistryCreator {

    @Override
    public void setupRegistryCreator() {
    }

    @Override
    public <P> Registry<P> createRegistry(Class<P> type, ResourceLocation registryName) {
        return FabricRegistryBuilder.createSimple(type, registryName)
                .attribute(RegistryAttribute.SYNCED)
                .buildAndRegister();
    }

}
