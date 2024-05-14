package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.registry.AttributeInit;
import com.infamousmisadventures.infamousartifacts.registry.ItemInit;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.Constants.MOD_ID;
import static net.minecraft.core.registries.BuiltInRegistries.ATTRIBUTE;
import static net.minecraft.core.registries.BuiltInRegistries.ITEM;

public class FabricRegistryHelper implements IRegistryHelper {

    @Override
    public void init() {
        AttributeInit.init();
        ItemInit.init();
    }

    @Override
    public Supplier<Attribute> registerAttribute(String id, Supplier<Attribute> supplier) {
        Attribute attribute = Registry.register(ATTRIBUTE, MOD_ID + ":" + id, supplier.get());

        return () -> attribute;
    }

    @Override
    public Supplier<Item> registerItem(String id, Supplier<Item> supplier) {
        Item item = Registry.register(ITEM, MOD_ID + ":" + id, supplier.get());

        return () -> item;
    }
}
