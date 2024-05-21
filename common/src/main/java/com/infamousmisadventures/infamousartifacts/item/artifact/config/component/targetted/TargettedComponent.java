package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import com.infamousmisadventures.infamousartifacts.registry.IARegistries;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;

public interface TargettedComponent {
    public static final Codec<TargettedComponent> CODEC = IARegistries.TARGETTED_COMPONENT_TYPE.byNameCodec().dispatch(TargettedComponent::type, TargettedComponentType::codec);
    TargettedComponentType<?> type();

    void apply(ArtifactUseContext context, LivingEntity entity);
}
