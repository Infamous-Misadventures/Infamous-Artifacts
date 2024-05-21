package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public enum TargettingType {
    ENEMY("enemy"),
    ALLY("ally"),
    NEUTRAL("neutral"),
    ALL("all");

    public static final Codec<TargettingType> CODEC = Codec.STRING.flatComapMap(s -> TargettingType.byName(s, null), d -> DataResult.success(d.getName()));

    private final String name;

    TargettingType(String name) {
        this.name = name;
    }

    public static TargettingType byName(String name, TargettingType defaultType) {
        for (TargettingType targettingType : values()) {
            if (targettingType.name.equals(name)) {
                return targettingType;
            }
        }
        return defaultType;
    }

    public String getName() {
        return name;
    }
}
