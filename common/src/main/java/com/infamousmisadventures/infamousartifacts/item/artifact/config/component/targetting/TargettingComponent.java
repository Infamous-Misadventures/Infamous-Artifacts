package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import com.infamousmisadventures.infamousartifacts.registry.IARegistries;
import com.mojang.serialization.Codec;

public abstract class TargettingComponent {
    public static final Codec<TargettingComponent> CODEC = IARegistries.TARGETTING_COMPONENT_TYPE.byNameCodec().dispatch(TargettingComponent::type, TargettingComponentType::codec);
    protected abstract TargettingComponentType<?> type();

    public abstract void target(ArtifactUseContext context);
}
