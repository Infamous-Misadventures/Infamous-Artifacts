package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.registry.AttributeInit;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.Constants.MOD_ID;
import static net.minecraft.core.registries.BuiltInRegistries.ATTRIBUTE;

public class FabricRegistryHelper implements IRegistryHelper {

    @Override
    public void init() {
        AttributeInit.init();
    }

    @Override
    public Supplier<Attribute> registerAttribute(String id, Supplier<Attribute> supplier) {
        Attribute attribute = Registry.register(ATTRIBUTE, MOD_ID + ":" + id, supplier.get());

        return () -> attribute;
    }
}
