package com.infamousmisadventures.infamousartifacts.registry;

import com.infamousmisadventures.infamousartifacts.inventory.PlayerArtifactStorageMenu;
import com.infamousmisadventures.infamousartifacts.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class IAMenuTypes {
    public static final Supplier<MenuType<PlayerArtifactStorageMenu>> PLAYER_ARTIFACT_STORAGE_MENU = registerMenuType("player_artifact_storage_menu",
            getSupplier(PlayerArtifactStorageMenu::new));

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> getSupplier(MenuType.MenuSupplier<T> factory) {
        return () -> new MenuType<T>(factory, FeatureFlags.VANILLA_SET);
    }

    public static void register() {
    }

    private static <U extends AbstractContainerMenu> Supplier<MenuType<U>> registerMenuType(String id, Supplier<MenuType<U>> supplier) {
        return Services.REGISTRAR.registerObject(modLoc(id), supplier, BuiltInRegistries.MENU);
    }
}
