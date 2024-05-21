package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ApplyMobEffectTargettedComponent extends TargettedComponent{
    public static Codec<ApplyMobEffectTargettedComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.MOB_EFFECT).fieldOf("effects").forGetter(comp -> ((ApplyMobEffectTargettedComponent) comp).effects()),
            Codec.INT.fieldOf("amplifier").forGetter(comp -> ((ApplyMobEffectTargettedComponent) comp).amplifier()),
            Codec.INT.fieldOf("duration").forGetter(comp -> ((ApplyMobEffectTargettedComponent) comp).duration())
    ).apply(instance, ApplyMobEffectTargettedComponent::new));

    private final HolderSet<MobEffect> effects;
    private final int amplifier;
    private final int duration;

    public ApplyMobEffectTargettedComponent(HolderSet<MobEffect> effects, int amplifier, int duration) {
        this.effects = effects;
        this.amplifier = amplifier;
        this.duration = duration;
    }

    public HolderSet<MobEffect> effects() {
        return effects;
    }

    public int amplifier() {
        return amplifier;
    }

    public int duration() {
        return duration;
    }

    @Override
    protected TargettedComponentType<?> type() {
        return TargettedComponentType.APPLY_MOB_EFFECT;
    }

    @Override
    public void apply(ArtifactUseContext context, LivingEntity entity) {
        effects.forEach(effect -> entity.addEffect(new MobEffectInstance(effect.value(), duration, amplifier)));
    }
}
