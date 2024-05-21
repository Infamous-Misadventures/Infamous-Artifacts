package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting;

import com.infamousmisadventures.infamousartifacts.registry.IARegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class TargettingComponentType<P extends TargettingComponent> {
    public static final TargettingComponentType<SelfTargettingComponent> SELF_TARGETTING_COMPONENT = register("self", SelfTargettingComponent.CODEC);
    private final Codec<P> codec;

    private static <P extends TargettingComponent> TargettingComponentType<P> register(String pKey, Codec<P> pCodec) {

        return Registry.register(IARegistries.TARGETTING_COMPONENT_TYPE, modLoc(pKey), new TargettingComponentType<>(pCodec));
    }

    public TargettingComponentType(Codec<P> codec) {
        this.codec = codec;
    }

    public Codec<P> codec() {
        return this.codec;
    }

    public static void init() {
    }
}
