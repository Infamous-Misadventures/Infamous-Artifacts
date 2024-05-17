package com.infamousmisadventures.infamousartifacts.registry;

import com.google.common.collect.ImmutableList;
import com.infamousmisadventures.infamousartifacts.platform.Services;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;

public class IAAttributes {
    private static final ObjectArrayList<Supplier<Attribute>> ATTRIBUTES = new ObjectArrayList<>();

    public static final Supplier<Attribute> ARTIFACT_COOLDOWN_MULTIPLIER  = registerAttribute("artifact_cooldown_multiplier",  1.0D,0.0D, 1024.0D);

    public static void register() {
    }

    public static ImmutableList<Supplier<Attribute>> getAttributes() {
        return ImmutableList.copyOf(ATTRIBUTES);
    }

    private static Supplier<Attribute> registerAttribute(String attributeName, double defaultValue, double minValue, double maxValue) {
        Supplier<Attribute> attribSupToRegister = registerAttribute(attributeName,
                () -> new RangedAttribute("attribute.name.generic."+ MOD_ID + "." + attributeName, defaultValue, minValue, maxValue)
                        .setSyncable(true));

        ATTRIBUTES.add(attribSupToRegister);
        return attribSupToRegister;
    }

    private static Supplier<Attribute> registerAttribute(String id, Supplier<Attribute> attribSup) {
        return Services.REGISTRAR.registerObject(id, attribSup, BuiltInRegistries.ATTRIBUTE);
    }
}
