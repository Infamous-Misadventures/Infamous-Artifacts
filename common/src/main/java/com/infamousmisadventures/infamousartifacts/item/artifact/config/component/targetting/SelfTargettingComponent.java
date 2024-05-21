package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.TargettedComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public class SelfTargettingComponent extends TargettingComponent {
    public static Codec<SelfTargettingComponent> CODEC =
         RecordCodecBuilder.create(instance -> instance.group(
                TargettedComponent.CODEC.listOf().fieldOf("effects").forGetter(SelfTargettingComponent::effects)
        ).apply(instance, SelfTargettingComponent::new));

    private final List<TargettedComponent> effects;

    public SelfTargettingComponent(List<TargettedComponent> effects) {
        this.effects = effects;
    }

    public List<TargettedComponent> effects() {
        return effects;
    }

    @Override
    protected TargettingComponentType<?> type() {
        return TargettingComponentType.SELF_TARGETTING_COMPONENT;
    }

    @Override
    public void target(ArtifactUseContext context) {
        effects.forEach(effect -> effect.apply(context, context.artifactUser()));
    }
}
