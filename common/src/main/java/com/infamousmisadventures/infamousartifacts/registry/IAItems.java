package com.infamousmisadventures.infamousartifacts.registry;

import com.google.common.collect.ImmutableList;
import com.infamousmisadventures.infamousartifacts.item.artifact.AbstractArtifact;
import com.infamousmisadventures.infamousartifacts.platform.Services;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class IAItems {
    private static final ObjectArrayList<Supplier<Item>> ITEMS = new ObjectArrayList<>();
    private static final Item.Properties ARTIFACT_PROPERTIES = new Item.Properties();

    public static final Supplier<Item> DEATH_CAP_MUSHROOM = registerItem("death_cap_mushroom", () -> new AbstractArtifact(ARTIFACT_PROPERTIES));
    public static final Supplier<Item> BOOTS_OF_SWIFTNESS = registerItem("boots_of_swiftness", () -> new AbstractArtifact(ARTIFACT_PROPERTIES));

    public static void register() {
    }

    public static ImmutableList<Supplier<Item>> getItems() {
        return ImmutableList.copyOf(ITEMS);
    }

    private static Supplier<Item> registerItem(String id, Supplier<Item> attribSup) {
        return Services.REGISTRAR.registerObject(modLoc(id), attribSup, BuiltInRegistries.ITEM);
    }
}
