package com.infamousmisadventures.infamousartifacts.registry;

import com.infamousmisadventures.infamousartifacts.platform.Services;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.Constants.MOD_ID;

public class AttributeInit {

    public static final List<Supplier<Attribute>> ALL_ATTRIBUTES = new ArrayList<>();

    public static final Supplier<Attribute> ARTIFACT_COOLDOWN_MULTIPLIER  = registerAttribute("artifact_cooldown_multiplier",  1.0D,0.0D, 1024.0D);

    public static void init() {
    }

    private static Supplier<Attribute> registerAttribute(String attributeName, double defaultValue, double minValue, double maxValue) {
        Supplier<Attribute> attributeSupplier = register(attributeName + "_focus",
                () -> new RangedAttribute(
                        "attribute.name.generic."+ MOD_ID + "." + attributeName,
                        defaultValue,
                        minValue,
                        maxValue)
                        .setSyncable(true));
        ALL_ATTRIBUTES.add(attributeSupplier);
        return attributeSupplier;
    }

    private static Supplier<Attribute> register(String id, Supplier<Attribute> supplier) {
        return Services.REGISTRY.registerAttribute(id, supplier);
    }

}