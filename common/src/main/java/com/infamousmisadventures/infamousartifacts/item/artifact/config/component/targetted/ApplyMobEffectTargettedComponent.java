package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.stream.Collectors;

import static com.infamousmisadventures.infamousartifacts.registry.IATargettedComponentTypes.APPLY_MOB_EFFECT;

public record ApplyMobEffectTargettedComponent(List<ResourceLocation> effectLocations, int amplifier,
                                               int duration) implements EntityTargettedComponent {
    public static Codec<ApplyMobEffectTargettedComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            //RegistryCodecs.homogeneousList(Registries.MOB_EFFECT).fieldOf("effects").forGetter(comp -> ((ApplyMobEffectTargettedComponent) comp).effects()),
            ResourceLocation.CODEC.listOf().fieldOf("effects").forGetter(ApplyMobEffectTargettedComponent::effectLocations),
            Codec.INT.fieldOf("amplifier").forGetter(ApplyMobEffectTargettedComponent::amplifier),
            Codec.INT.fieldOf("duration").forGetter(ApplyMobEffectTargettedComponent::duration)
    ).apply(instance, ApplyMobEffectTargettedComponent::new));

    private List<MobEffect> effects() {
        return effectLocations.stream().map(BuiltInRegistries.MOB_EFFECT::get).collect(Collectors.toList());
    }

    @Override
    public TargettedComponentType<?> type() {
        return APPLY_MOB_EFFECT;
    }

    @Override
    public void apply(ArtifactUseContext context, LivingEntity entity) {
        effects().forEach(effect -> entity.addEffect(new MobEffectInstance(effect, duration, amplifier)));
    }
}
