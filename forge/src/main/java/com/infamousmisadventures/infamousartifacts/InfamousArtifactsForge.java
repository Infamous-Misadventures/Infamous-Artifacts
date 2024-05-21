package com.infamousmisadventures.infamousartifacts;

import com.infamousmisadventures.infamousartifacts.datapack.CodecDataManagerSync;
import com.infamousmisadventures.infamousartifacts.datapack.DatapackReloadListener;
import com.infamousmisadventures.infamousartifacts.network.NetworkHandler;
import com.infamousmisadventures.infamousartifacts.network.message.ArtifactGearConfigSyncPacket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.infamousmisadventures.infamousartifacts.registry.ArtifactGearConfigRegistry.ARTIFACT_GEAR_CONFIGS;

@Mod(IAConstants.MOD_ID)
public class InfamousArtifactsForge {
    
    public InfamousArtifactsForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        InfamousArtifacts.init();
        setupDatapackFormats();
        setupEvents();
    }

    private void setupDatapackFormats() {
        CodecDataManagerSync.subscribeAsSyncable(NetworkHandler.INSTANCE, ArtifactGearConfigSyncPacket::new, ARTIFACT_GEAR_CONFIGS);
    }

    public void setupEvents() {
        MinecraftForge.EVENT_BUS.addListener(DatapackReloadListener::onAddReloadListeners);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
    }
}