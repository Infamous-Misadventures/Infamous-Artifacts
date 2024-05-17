package com.infamousmisadventures.infamousartifacts.item.artifact.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public record ArtifactGearConfig(List<ConfigAttributeModifier> attributes, int cooldown, int durability, int duration) {

    public static final ArtifactGearConfig DEFAULT = new ArtifactGearConfig(new ArrayList<>(), 20, 64, 5);

    public static final Codec<ArtifactGearConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ConfigAttributeModifier.CODEC.listOf().optionalFieldOf("attributes", new ArrayList<>()).forGetter(ArtifactGearConfig::attributes),
            Codec.INT.optionalFieldOf("cooldown", 20).forGetter(ArtifactGearConfig::cooldown),
            Codec.INT.optionalFieldOf("durability", 64).forGetter(ArtifactGearConfig::durability),
            Codec.INT.optionalFieldOf("duration", 5).forGetter(ArtifactGearConfig::duration)
    ).apply(instance, ArtifactGearConfig::new));

}