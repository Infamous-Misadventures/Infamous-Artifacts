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
    private static final ObjectArrayList<Supplier<Item>> ARTIFACTS = new ObjectArrayList<>();
    private static final Item.Properties ARTIFACT_PROPERTIES = new Item.Properties();

    public static final Supplier<Item> BOOTS_OF_SWIFTNESS = registerArtifact("boots_of_swiftness", () -> new AbstractArtifact(ARTIFACT_PROPERTIES));
    public static final Supplier<Item> DEATH_CAP_MUSHROOM = registerArtifact("death_cap_mushroom", () -> new AbstractArtifact(ARTIFACT_PROPERTIES));
    public static final Supplier<Item> GONG_OF_WEAKENING = registerArtifact("gong_of_weakening", () -> new AbstractArtifact(ARTIFACT_PROPERTIES));
    public static final Supplier<Item> IRON_HIDE_AMULET = registerArtifact("iron_hide_amulet", () -> new AbstractArtifact(ARTIFACT_PROPERTIES));
    public static final Supplier<Item> DIRT = registerArtifact("dirt_artifact", () -> new AbstractArtifact(ARTIFACT_PROPERTIES));

    public static void register() {
    }

    public static ImmutableList<Supplier<Item>> getArtifacts() {
        return ImmutableList.copyOf(ARTIFACTS);
    }

    private static Supplier<Item> registerArtifact(String id, Supplier<Item> attribSup) {
        Supplier<Item> itemSupplier = Services.REGISTRAR.registerObject(modLoc(id), attribSup, BuiltInRegistries.ITEM);
        ARTIFACTS.add(itemSupplier);
        return itemSupplier;
    }

    private static Supplier<Item> registerItem(String id, Supplier<Item> attribSup) {
        return Services.REGISTRAR.registerObject(modLoc(id), attribSup, BuiltInRegistries.ITEM);
    }
}
