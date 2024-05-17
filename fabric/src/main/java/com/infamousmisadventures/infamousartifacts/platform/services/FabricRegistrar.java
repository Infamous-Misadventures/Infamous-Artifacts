package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.registry.IAAttributes;
import com.infamousmisadventures.infamousartifacts.registry.IAItems;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

public class FabricRegistrar implements IRegistrar {

    @Override
    public void setupRegistrar() {
        IAAttributes.register();
        IAItems.register();
    }

    @Override
    public <T> Supplier<T> registerObject(String id, Supplier<T> objSup, Registry<T> targetRegistry) {
        Supplier<T> targetObject = () -> Registry.register(targetRegistry, id, objSup.get());
        return targetObject;
    }
}
