package com.infamousmisadventures.infamousartifacts.platform.services;

import com.infamousmisadventures.infamousartifacts.network.packets.base.ILoaderAgnosticPacket;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHandler {
    void setupNetworkHandler();

    <MSG> void sendToServer(MSG packet);

    <MSG> void sendToClient(MSG packet, ServerPlayer player);
}
