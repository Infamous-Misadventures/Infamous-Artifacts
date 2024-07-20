package com.infamousmisadventures.infamousartifacts.util;

import net.minecraft.nbt.Tag;

public interface INBTSerializable<T extends Tag> {
    default T serializeNBT() {
        throw new RuntimeException("override serializeNBT!");
    }

    default void deserializeNBT(T nbt) {
        throw new RuntimeException("override deserializeNBT!");
    }
}