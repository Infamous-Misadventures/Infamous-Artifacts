package com.infamousmisadventures.infamousartifacts.registry;

import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting.AreaSelfTargettingComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting.SelfTargettingComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting.TargettingComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting.TargettingComponentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class IATargettingComponentTypes {
    public static final TargettingComponentType<SelfTargettingComponent> SELF_TARGETTING_COMPONENT = register("self", SelfTargettingComponent.CODEC);
    public static final TargettingComponentType<AreaSelfTargettingComponent> AREA_SELF_TARGETTING_COMPONENT = register("area_self", AreaSelfTargettingComponent.CODEC);

    private static <P extends TargettingComponent> TargettingComponentType<P> register(String pKey, Codec<P> pCodec) {

        return Registry.register(IARegistries.TARGETTING_COMPONENT_TYPE, modLoc(pKey), new TargettingComponentType<>(pCodec));
    }

    public static void register() {
    }
}
