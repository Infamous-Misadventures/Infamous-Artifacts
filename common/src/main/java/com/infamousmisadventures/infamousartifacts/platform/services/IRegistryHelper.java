package com.infamousmisadventures.infamousartifacts.platform.services;

import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

public interface IRegistryHelper {

    void init();

    Supplier<Attribute> registerAttribute(String id, Supplier<Attribute> attributeSupplier);
}
