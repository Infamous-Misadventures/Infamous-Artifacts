package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted;

import com.mojang.serialization.Codec;

public class TargettedComponentType<P extends TargettedComponent> {
    private final Codec<P> codec;

    public TargettedComponentType(Codec<P> codec) {
        this.codec = codec;
    }

    public Codec<P> codec() {
        return this.codec;
    }
}
