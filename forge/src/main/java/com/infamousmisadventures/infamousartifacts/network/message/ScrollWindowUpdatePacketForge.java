package com.infamousmisadventures.infamousartifacts.network.message;

import com.infamousmisadventures.infamousartifacts.network.packets.ScrollWindowUpdatePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScrollWindowUpdatePacketForge {

    private final ScrollWindowUpdatePacket packet;

    public ScrollWindowUpdatePacketForge(ScrollWindowUpdatePacket packet) {
        this.packet = packet;
    }

    public static ScrollWindowUpdatePacketForge decode(FriendlyByteBuf buffer) {
        return new ScrollWindowUpdatePacketForge(new ScrollWindowUpdatePacket(buffer));
    }

    public void encode(FriendlyByteBuf buf) {
        packet.encode(buf);
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        packet.handle();
    }
}
