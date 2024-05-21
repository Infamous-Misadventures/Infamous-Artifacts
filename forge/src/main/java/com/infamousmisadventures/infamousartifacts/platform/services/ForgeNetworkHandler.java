package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.network.message.ArtifactGearConfigSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;

public class ForgeNetworkHandler implements INetworkHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
                    new ResourceLocation(MOD_ID, "network"))
            .clientAcceptedVersions("1"::equals)
            .serverAcceptedVersions("1"::equals)
            .networkProtocolVersion(() -> "1")
            .simpleChannel();

    protected static int PACKET_COUNTER = 0;

    @Override
    public void setupNetworkHandler() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(this::registerPackets);
    }

    private void registerPackets() {
        INSTANCE.messageBuilder(ArtifactGearConfigSyncPacket.class, incrementAndGetPacketCounter())
                .encoder(ArtifactGearConfigSyncPacket::encode).decoder(ArtifactGearConfigSyncPacket::decode)
                .consumerMainThread(ArtifactGearConfigSyncPacket::onPacketReceived)
                .add();
    }

    public static int incrementAndGetPacketCounter() {
        return PACKET_COUNTER++;
    }
}
