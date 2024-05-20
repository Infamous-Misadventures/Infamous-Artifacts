package com.infamousmisadventures.infamousartifacts.platform;

import com.infamousmisadventures.infamousartifacts.platform.services.IRegistrar;
import com.infamousmisadventures.infamousartifacts.registry.IAAttributes;
import com.infamousmisadventures.infamousartifacts.registry.IAItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class FabricRegistrar implements IRegistrar {

    @Override
    public void setupRegistrar() {
        IAAttributes.register();
        IAItems.register();
    }

    @Override
    public <T> Supplier<T> registerObject(ResourceLocation objId, Supplier<T> objSup, Registry<T> targetRegistry) {
        T targetObject = Registry.register(targetRegistry, objId, objSup.get());
        return () -> targetObject;
    }
}
