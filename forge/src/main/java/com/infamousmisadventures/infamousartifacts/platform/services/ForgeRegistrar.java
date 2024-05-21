package com.infamousmisadventures.infamousartifacts.platform.services;

import com.google.common.collect.ImmutableMap;
import com.infamousmisadventures.infamousartifacts.registry.IAAttributes;
import com.infamousmisadventures.infamousartifacts.registry.IAItems;
import com.infamousmisadventures.infamousartifacts.registry.IATargettedComponentTypes;
import com.infamousmisadventures.infamousartifacts.registry.IATargettingComponentTypes;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;

public class ForgeRegistrar implements IRegistrar {
    private static final Object2ObjectLinkedOpenHashMap<ResourceKey, DeferredRegister> CACHED_REGISTRIES = new Object2ObjectLinkedOpenHashMap<>(); // Faster lookups + menial memory usage increase over a map backed by arrays :p

    @Override
    public void setupRegistrar() {
        IAAttributes.register(); //TODO Find a way to automate this goofy ahh classloading
        IAItems.register();
        IATargettingComponentTypes.register();
        IATargettedComponentTypes.register();
    }

    @Override
    public <T> RegistryObject<T> registerObject(ResourceLocation objId, Supplier<T> objSup, Registry<T> targetRegistry) {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus(); // Should not be null at the time this method is called

        ResourceKey<? extends Registry<T>> targetRegistryKey = targetRegistry.key();

        DeferredRegister<T> existingDefReg = (DeferredRegister<T>) CACHED_REGISTRIES.computeIfAbsent(targetRegistryKey, defReg -> {
            DeferredRegister<T> cachedDefReg = DeferredRegister.create(targetRegistryKey, MOD_ID);
            cachedDefReg.register(modBus);
            return cachedDefReg;
        });
        return existingDefReg.register(objId.getPath(), objSup);
    }

    public static ImmutableMap<ResourceKey, DeferredRegister> getCachedRegistries() {
        return ImmutableMap.copyOf(CACHED_REGISTRIES);
    }
}
