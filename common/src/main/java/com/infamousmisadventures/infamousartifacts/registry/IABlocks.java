package com.infamousmisadventures.infamousartifacts.registry;

import com.google.common.collect.ImmutableList;
import com.infamousmisadventures.infamousartifacts.block.ArtifactStorageBlock;
import com.infamousmisadventures.infamousartifacts.platform.Services;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class IABlocks {
    private static final ObjectArrayList<Supplier<Block>> BLOCKS = new ObjectArrayList<>();

    public static final Supplier<Block> ARTIFACT_STORAGE = registerBlock("artifact_storage", () -> new ArtifactStorageBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.STONE)));

    public static void register() {
    }

    public static ImmutableList<Supplier<Block>> getArtifacts() {
        return ImmutableList.copyOf(BLOCKS);
    }

    private static Supplier<Block> registerBlock(String id, Supplier<Block> attribSup) {
        Supplier<Block> blockSupplier = Services.REGISTRAR.registerObject(modLoc(id), attribSup, BuiltInRegistries.BLOCK);
        BLOCKS.add(blockSupplier);
        return blockSupplier;
    }
}
