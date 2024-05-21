package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting;

import com.mojang.serialization.Codec;

public class TargettingComponentType<P extends TargettingComponent> {
    private final Codec<P> codec;

    public TargettingComponentType(Codec<P> codec) {
        this.codec = codec;
    }

    public Codec<P> codec() {
        return this.codec;
    }
}
