package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class FabricRegistrar implements IRegistrar {

    @Override
    public void setupRegistrar() {
        IAAttributes.register();
        IAItems.register();
        IABlocks.register();
        IAMenuTypes.register();
        IACreativeTabs.register();
        IATargettingComponentTypes.register();
        IATargettedComponentTypes.register();
    }

    @Override
    public <V, T extends V> Supplier<T> registerObject(ResourceLocation objId, Supplier<T> objSup, Registry<V> targetRegistry) {
        T targetObject = Registry.register(targetRegistry, objId, objSup.get());
        return () -> targetObject;
    }
}
