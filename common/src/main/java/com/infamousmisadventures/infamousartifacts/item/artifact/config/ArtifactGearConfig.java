package com.infamousmisadventures.infamousartifacts.item.artifact.config;

import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.cost.CostComponent;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting.TargettingComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public record ArtifactGearConfig(List<ConfigAttributeModifier> attributes, int cooldown, int durability,
                                 List<CostComponent> costComponentList,
                                 List<TargettingComponent> targetingComponentList) {

    public static final ArtifactGearConfig DEFAULT = new ArtifactGearConfig(new ArrayList<>(), 20, 64, new ArrayList<>(), new ArrayList<>());

    public static final Codec<ArtifactGearConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ConfigAttributeModifier.CODEC.listOf().optionalFieldOf("attributes", new ArrayList<>()).forGetter(ArtifactGearConfig::attributes),
            Codec.INT.optionalFieldOf("cooldown", 20).forGetter(ArtifactGearConfig::cooldown),
            Codec.INT.optionalFieldOf("durability", 64).forGetter(ArtifactGearConfig::durability),
            TargettingComponent.CODEC.listOf().optionalFieldOf("targettingComponents", new ArrayList<>()).forGetter(ArtifactGearConfig::targetingComponentList)
    ).apply(instance, (attributes, cooldown, durability, targettingComponents) -> new ArtifactGearConfig(attributes, cooldown, durability, new ArrayList<>(), targettingComponents)));

}