package com.infamousmisadventures.infamousartifacts.platform;

import com.infamousmisadventures.infamousartifacts.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.Constants.MOD_ID;
import static net.minecraft.core.registries.BuiltInRegistries.ATTRIBUTE;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
