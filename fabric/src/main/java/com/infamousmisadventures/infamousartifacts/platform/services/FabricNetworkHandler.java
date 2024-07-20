package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.network.message.ArtifactGearConfigSyncPacket;
import com.infamousmisadventures.infamousartifacts.network.packets.RegisterPackets;
import commonnetwork.api.Network;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static com.infamousmisadventures.infamousartifacts.IAConstants.MOD_ID;

public class FabricNetworkHandler implements INetworkHandler {
    public static final SimpleChannel INSTANCE = new SimpleChannel(new ResourceLocation(MOD_ID, "network"));

    protected static int PACKET_COUNTER = 0;

    public FabricNetworkHandler() {
    }

    @Override
    public void setupNetworkHandler() {
        RegisterPackets.registerPackets();
        // Server to Client
        INSTANCE.registerS2CPacket(ArtifactGearConfigSyncPacket.class, incrementAndGetPacketCounter(), ArtifactGearConfigSyncPacket::decode);
    }

    @Override
    public <MSG> void sendToServer(MSG packet) {
        Network.getNetworkHandler().sendToServer(packet);
    }

    @Override
    public <MSG> void sendToClient(MSG packet, ServerPlayer player) {
        Network.getNetworkHandler().sendToClient(packet, player);
    }

    public static int incrementAndGetPacketCounter() {
        return PACKET_COUNTER++;
    }
}
