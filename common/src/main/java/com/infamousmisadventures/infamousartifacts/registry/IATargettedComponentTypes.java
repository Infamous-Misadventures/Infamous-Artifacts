package com.infamousmisadventures.infamousartifacts.registry;

import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.ApplyMobEffectTargettedComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.TargettedComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.TargettedComponentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class IATargettedComponentTypes {
    public static final TargettedComponentType<ApplyMobEffectTargettedComponent> APPLY_MOB_EFFECT = register("apply_mob_effect", ApplyMobEffectTargettedComponent.CODEC);

    private static <P extends TargettedComponent> TargettedComponentType<P> register(String key, Codec<P> codec) {
        return (TargettedComponentType) Registry.register(IARegistries.TARGETTED_COMPONENT_TYPE, modLoc(key), new TargettedComponentType(codec));
    }

    public static void init() {
    }
}
