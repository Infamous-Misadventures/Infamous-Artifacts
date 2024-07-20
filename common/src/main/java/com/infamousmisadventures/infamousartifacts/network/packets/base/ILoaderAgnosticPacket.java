package com.infamousmisadventures.infamousartifacts.network.packets.base;

import net.minecraft.network.FriendlyByteBuf;

public interface ILoaderAgnosticPacket<T> {

    void encode(FriendlyByteBuf buffer);

    void handle();
}
