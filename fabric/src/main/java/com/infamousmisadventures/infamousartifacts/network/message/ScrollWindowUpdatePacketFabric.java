package com.infamousmisadventures.infamousartifacts.network.message;

import com.infamousmisadventures.infamousartifacts.network.packets.ScrollWindowUpdatePacket;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class ScrollWindowUpdatePacketFabric implements C2SPacket {

    private final ScrollWindowUpdatePacket packet;

    public ScrollWindowUpdatePacketFabric(ScrollWindowUpdatePacket packet) {
        this.packet = packet;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        packet.encode(buf);
    }

    public static ScrollWindowUpdatePacketFabric decode(FriendlyByteBuf buffer) {
        return new ScrollWindowUpdatePacketFabric(new ScrollWindowUpdatePacket(buffer));
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {

    }
}
