package com.infamousmisadventures.infamousartifacts.registry;

import com.infamousmisadventures.infamousartifacts.item.artifact.DeathCapMushroomItem;
import com.infamousmisadventures.infamousartifacts.platform.Services;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.Constants.MOD_ID;

public class ItemInit {
    public static final Item.Properties ARTIFACT_PROPERTIES = new Item.Properties();
    public static final List<Supplier<Item>> ALL_ARTIFACTS = new ArrayList<>();

    public static final Supplier<Item> ARTIFACT_COOLDOWN_MULTIPLIER  = registerArtifact("death_cap_mushroom",
            () -> new DeathCapMushroomItem(ARTIFACT_PROPERTIES));

    public static void init() {
    }

    private static Supplier<Item> registerArtifact(String name, Supplier<Item> supplier) {
        Supplier<Item> itemSupplier = register(name, supplier);
        ALL_ARTIFACTS.add(itemSupplier);
        return itemSupplier;
    }

    private static Supplier<Item> register(String id, Supplier<Item> supplier) {
        return Services.REGISTRY.registerItem(id, supplier);
    }
}
