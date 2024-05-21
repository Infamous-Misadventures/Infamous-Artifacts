package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted;

import com.infamousmisadventures.infamousartifacts.registry.IARegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public class TargettedComponentType<P extends TargettedComponent> {
    public static final TargettedComponentType<ApplyMobEffectTargettedComponent> APPLY_MOB_EFFECT = register("apply_mob_effect", ApplyMobEffectTargettedComponent.CODEC);
    private final Codec<P> codec;

    private static <P extends TargettedComponent> TargettedComponentType<P> register(String key, Codec<P> codec) {
        return (TargettedComponentType)Registry.register(IARegistries.TARGETTED_COMPONENT_TYPE, key, new TargettedComponentType(codec));
    }

    public TargettedComponentType(Codec<P> codec) {
        this.codec = codec;
    }

    public Codec<P> codec() {
        return this.codec;
    }
}
