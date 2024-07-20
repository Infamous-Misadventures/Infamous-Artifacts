package com.infamousmisadventures.infamousartifacts.platform.services;

import net.minecraft.server.level.ServerPlayer;

public interface INetworkHandler {
    void setupNetworkHandler();

    <MSG> void sendToServer(MSG packet);

    <MSG> void sendToClient(MSG packet, ServerPlayer player);
}
