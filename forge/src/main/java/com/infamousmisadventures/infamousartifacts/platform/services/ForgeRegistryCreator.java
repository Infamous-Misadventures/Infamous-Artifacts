package com.infamousmisadventures.infamousartifacts.platform.services;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;

public class ForgeRegistryCreator implements IRegistryCreator {

    @Override
    public void setupRegistryCreator() {
    }

    public <P> Registry<P> createRegistry(Class<P> type, ResourceLocation registryName) {
        ResourceKey<Registry<P>> registryKey = ResourceKey.createRegistryKey(registryName);
        DeferredRegister<P> deferredRegister = DeferredRegister.create(registryKey, MOD_ID);
        Supplier<IForgeRegistry<P>> iForgeRegistrySupplier = deferredRegister.makeRegistry(RegistryBuilder::new);
        return new MappedRegistry<>(registryKey, Lifecycle.stable());
    }
}
