package com.infamousmisadventures.infamousartifacts.network.packets;

import com.infamousmisadventures.infamousartifacts.IAConstants;
import com.infamousmisadventures.infamousartifacts.inventory.PlayerArtifactStorageMenu;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class ScrollWindowUpdatePacket {
    public static final ResourceLocation CHANNEL = modLoc("scroll_window_update");
    private final int newScrollIndex;

    public ScrollWindowUpdatePacket(int newScrollIndex) {
        this.newScrollIndex = newScrollIndex;
    }

    public ScrollWindowUpdatePacket(FriendlyByteBuf buffer) {
        newScrollIndex = buffer.readInt();
    }

    public int getNewScrollIndex() {
        return newScrollIndex;
    }

    public static ScrollWindowUpdatePacket decode(FriendlyByteBuf buffer) {
        return new ScrollWindowUpdatePacket(buffer);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(newScrollIndex);
    }

    public static void handle(PacketContext<ScrollWindowUpdatePacket> ctx) {
        if (Side.CLIENT.equals(ctx.side()))
        {
            IAConstants.LOGGER.error("ScrollWindowUpdatePacket received on the client");
        }
        else
        {
            if(ctx.sender().containerMenu instanceof PlayerArtifactStorageMenu menu)
            {
                menu.scrollTo(ctx.message().getNewScrollIndex());
            }
        }
    }
}