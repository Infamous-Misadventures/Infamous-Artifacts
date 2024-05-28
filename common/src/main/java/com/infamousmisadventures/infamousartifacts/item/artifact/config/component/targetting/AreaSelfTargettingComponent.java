package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.BlockTargettedComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.EntityTargettedComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.TargettedComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import static com.infamousmisadventures.infamousartifacts.registry.IATargettingComponentTypes.AREA_SELF_TARGETTING_COMPONENT;
import static net.minecraft.world.entity.MobCategory.MONSTER;

public record AreaSelfTargettingComponent(int radius, TargettingType targettingType, boolean affectsSelf, List<TargettedComponent> effects) implements TargettingComponent {
    public static Codec<AreaSelfTargettingComponent> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("radius").forGetter(AreaSelfTargettingComponent::radius),
                    TargettingType.CODEC.optionalFieldOf("targetting_type", TargettingType.ALL).forGetter(AreaSelfTargettingComponent::targettingType),
                    Codec.BOOL.optionalFieldOf("affects_self", false).forGetter(AreaSelfTargettingComponent::affectsSelf),
                    TargettedComponent.CODEC.listOf().fieldOf("effects").forGetter(AreaSelfTargettingComponent::effects)
            ).apply(instance, AreaSelfTargettingComponent::new));

    @Override
    public TargettingComponentType<?> type() {
        return AREA_SELF_TARGETTING_COMPONENT;
    }

    @Override
    public void target(ArtifactUseContext context) {
        List<Entity> entities = findEntities(context);
        entities.forEach(entity -> applyEffects(context, entity));
    }

    private void applyEffects(ArtifactUseContext context, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            for (TargettedComponent effect : effects) {
                if(effect instanceof EntityTargettedComponent entityTargettedEffect){
                    entityTargettedEffect.apply(context, livingEntity);
                }else if(effect instanceof BlockTargettedComponent blockTargettedEffect){
                    blockTargettedEffect.apply(context, livingEntity.blockPosition());
                }
            }
        }
    }

    private List<Entity> findEntities(ArtifactUseContext context) {
        List<Entity> entities = context.artifactUser().level().getEntities(context.artifactUser(), context.artifactUser().getBoundingBox().inflate(radius), this::filterByTargettingType);
        if(affectsSelf && !entities.contains(context.artifactUser())){
            entities.add(context.artifactUser());
        }
        return entities;
    }

    private boolean filterByTargettingType(Entity entity) {
        return switch (targettingType) {
            case ENEMY -> MONSTER.equals(entity.getType().getCategory());
            case NEUTRAL -> !(entity instanceof Player) && !MONSTER.equals(entity.getType().getCategory());
            case ALLY -> entity instanceof Player || (entity instanceof TamableAnimal tamable && tamable.isTame());
            default -> true;
        };
    }
}
