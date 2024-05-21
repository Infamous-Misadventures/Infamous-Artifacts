package com.infamousmisadventures.infamousartifacts.network;

import com.infamousmisadventures.infamousartifacts.network.message.ArtifactGearConfigSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;

public class NetworkHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
                    new ResourceLocation(MOD_ID, "network"))
            .clientAcceptedVersions("1"::equals)
            .serverAcceptedVersions("1"::equals)
            .networkProtocolVersion(() -> "1")
            .simpleChannel();

    protected static int PACKET_COUNTER = 0;

    public NetworkHandler() {
    }

    public static void init() {
        INSTANCE.messageBuilder(ArtifactGearConfigSyncPacket.class, incrementAndGetPacketCounter())
                .encoder(ArtifactGearConfigSyncPacket::encode).decoder(ArtifactGearConfigSyncPacket::decode)
                .consumerMainThread(ArtifactGearConfigSyncPacket::onPacketReceived)
                .add();
    }

    public static int incrementAndGetPacketCounter() {
        return PACKET_COUNTER++;
    }
}
