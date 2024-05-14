package com.infamousmisadventures.infamousartifacts.platform.services;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface IRegistryHelper {

    void init();

    Supplier<Attribute> registerAttribute(String id, Supplier<Attribute> supplier);
    Supplier<Item> registerItem(String id, Supplier<Item> supplier);
}
