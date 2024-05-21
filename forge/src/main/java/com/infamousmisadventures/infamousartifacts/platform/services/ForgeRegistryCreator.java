package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.item.artifact.config.ArtifactGearConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;
import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class ForgeRegistryCreator implements IRegistryCreator {

    @Override
    public void setupRegistryCreator() {
    }

    public <P> Registry<P> createRegistry(Class<P> type, ResourceLocation registryName) {
        ResourceKey<Registry<P>> registryKey = ResourceKey.createRegistryKey(registryName);
        DeferredRegister<P> deferredRegister = DeferredRegister.create(registryKey, MOD_ID);
        Supplier<IForgeRegistry<P>> iForgeRegistrySupplier = deferredRegister.makeRegistry(RegistryBuilder::new);
        //return new Registry<>(registryKey, iForgeRegistrySupplier);
        return null;
    }
}
