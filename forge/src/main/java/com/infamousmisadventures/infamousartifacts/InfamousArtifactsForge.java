package com.infamousmisadventures.infamousartifacts;

import com.infamousmisadventures.infamousartifacts.datapack.CodecDataManagerSync;
import com.infamousmisadventures.infamousartifacts.datapack.DatapackReloadListener;
import com.infamousmisadventures.infamousartifacts.network.packets.ArtifactGearConfigSyncPacket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.infamousmisadventures.infamousartifacts.registry.ArtifactGearConfigRegistry.ARTIFACT_GEAR_CONFIGS;

@Mod(IAConstants.MOD_ID)
public class InfamousArtifactsForge {
    
    public InfamousArtifactsForge() {
        InfamousArtifacts.init();
        setupDatapackFormats();
        setupEvents();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setupDatapackFormats() {
        CodecDataManagerSync.subscribeAsSyncable(ArtifactGearConfigSyncPacket::new, ARTIFACT_GEAR_CONFIGS);
    }

    public void setupEvents() {
        MinecraftForge.EVENT_BUS.addListener(DatapackReloadListener::onAddReloadListeners);
    }

    private void setupClient(final FMLClientSetupEvent event) {
        event.enqueueWork(InfamousArtifactsForgeClient::setupClient);
    }
}