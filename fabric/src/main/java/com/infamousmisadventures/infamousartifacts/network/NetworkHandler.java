package com.infamousmisadventures.infamousartifacts.network;

import com.infamousmisadventures.infamousartifacts.network.message.ArtifactGearConfigSyncPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.resources.ResourceLocation;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;

public class NetworkHandler {
    public static final SimpleChannel INSTANCE = new SimpleChannel(new ResourceLocation(MOD_ID, "network"));

    protected static int PACKET_COUNTER = 0;

    public NetworkHandler() {
    }

    public static void init() {
        // Server to Client
        INSTANCE.registerS2CPacket(ArtifactGearConfigSyncPacket.class, incrementAndGetPacketCounter(), ArtifactGearConfigSyncPacket::decode);
    }

    public static int incrementAndGetPacketCounter() {
        return PACKET_COUNTER++;
    }
}
