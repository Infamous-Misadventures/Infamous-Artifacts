package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.BlockTargettedComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.EntityTargettedComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.TargettedComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

import static com.infamousmisadventures.infamousartifacts.registry.IATargettingComponentTypes.SELF_TARGETTING_COMPONENT;

public record SelfTargettingComponent(List<TargettedComponent> effects) implements TargettingComponent {
    public static Codec<SelfTargettingComponent> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    TargettedComponent.CODEC.listOf().fieldOf("effects").forGetter(SelfTargettingComponent::effects)
            ).apply(instance, SelfTargettingComponent::new));

    @Override
    public TargettingComponentType<?> type() {
        return SELF_TARGETTING_COMPONENT;
    }

    @Override
    public void target(ArtifactUseContext context) {
        effects.forEach(effect -> applyEffect(context, effect));
    }

    private void applyEffect(ArtifactUseContext context, TargettedComponent effect) {
        if(effect instanceof EntityTargettedComponent entityTargettedEffect){
            entityTargettedEffect.apply(context, context.artifactUser());
        }else if(effect instanceof BlockTargettedComponent blockTargettedEffect){
            blockTargettedEffect.apply(context, context.artifactUser().blockPosition());
        }
    }
}
