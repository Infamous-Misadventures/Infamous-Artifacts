package com.infamousmisadventures.infamousartifacts.item.artifact.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class ConfigAttributeModifier {

    public static final Codec<AttributeModifier.Operation> ATTRIBUTE_MODIFIER_OPERATION_CODEC = Codec.INT.flatComapMap(AttributeModifier.Operation::fromValue, d -> DataResult.success(d.toValue()));

    public static final Codec<ConfigAttributeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("attribute").forGetter(ConfigAttributeModifier::getAttributeResourceLocation),
            Codec.DOUBLE.fieldOf("amount").forGetter(ConfigAttributeModifier::getAmount),
            ATTRIBUTE_MODIFIER_OPERATION_CODEC.fieldOf("operation").forGetter(ConfigAttributeModifier::getOperation)
    ).apply(instance, ConfigAttributeModifier::new));

    private final ResourceLocation attributeResourceLocation;
    private final double amount;
    private final AttributeModifier.Operation operation;

    public ConfigAttributeModifier(ResourceLocation attributeResourceLocation, double amount, AttributeModifier.Operation operation) {
        this.attributeResourceLocation = attributeResourceLocation;
        this.amount = amount;
        this.operation = operation;
    }

    public ResourceLocation getAttributeResourceLocation() {
        return attributeResourceLocation;
    }

    public double getAmount() {
        return amount;
    }

    public AttributeModifier.Operation getOperation() {
        return operation;
    }

    public AttributeModifier toAttributeModifier(UUID uuid, String name){
        return new AttributeModifier(uuid, name, amount, operation);
    }

    public Attribute getAttribute(){
        return BuiltInRegistries.ATTRIBUTE.get(attributeResourceLocation);
    }
}
