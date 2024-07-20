package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.network.packets.RegisterPackets;
import commonnetwork.api.Network;
import net.minecraft.server.level.ServerPlayer;

public class FabricNetworkHandler implements INetworkHandler {

    @Override
    public void setupNetworkHandler() {
        RegisterPackets.registerPackets();
    }

    @Override
    public <MSG> void sendToServer(MSG packet) {
        Network.getNetworkHandler().sendToServer(packet);
    }

    @Override
    public <MSG> void sendToClient(MSG packet, ServerPlayer player) {
        Network.getNetworkHandler().sendToClient(packet, player);
    }

}
