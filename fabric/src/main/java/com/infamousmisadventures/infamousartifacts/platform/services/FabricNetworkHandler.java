package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.network.message.ArtifactGearConfigSyncPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.resources.ResourceLocation;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;

public class FabricNetworkHandler implements INetworkHandler {
    public static final SimpleChannel INSTANCE = new SimpleChannel(new ResourceLocation(MOD_ID, "network"));

    protected static int PACKET_COUNTER = 0;

    public FabricNetworkHandler() {
    }

    @Override
    public void setupNetworkHandler() {
        // Server to Client
        INSTANCE.registerS2CPacket(ArtifactGearConfigSyncPacket.class, incrementAndGetPacketCounter(), ArtifactGearConfigSyncPacket::decode);
    }

    public static int incrementAndGetPacketCounter() {
        return PACKET_COUNTER++;
    }
}
