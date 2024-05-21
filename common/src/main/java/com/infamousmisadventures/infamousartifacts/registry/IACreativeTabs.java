package com.infamousmisadventures.infamousartifacts.registry;

import com.infamousmisadventures.infamousartifacts.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class IACreativeTabs {
    public static final Supplier<CreativeModeTab> ARTIFACTS_TAB = registerCreativeTab("infamous_artifacts",
            IACreativeTabs::buildArtifactsTab);

    private static @NotNull CreativeModeTab buildArtifactsTab() {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .title(Component.translatable("itemGroup.infamous_artifacts")).icon(() -> new ItemStack(IAItems.BOOTS_OF_SWIFTNESS.get()));
        builder.displayItems((p_270033_, p_270034_) -> {
            IAItems.getArtifacts().forEach((item) -> {
                p_270034_.accept(new ItemStack(item.get()));
            });
        });
        return builder.build();
    }

    public static void register() {
    }

    private static Supplier<CreativeModeTab> registerCreativeTab(String id, Supplier<CreativeModeTab> supplier) {
        return Services.REGISTRAR.registerObject(modLoc(id), supplier, BuiltInRegistries.CREATIVE_MODE_TAB);
    }
}
