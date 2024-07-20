package com.infamousmisadventures.infamousartifacts.network.packets;

import com.infamousmisadventures.infamousartifacts.inventory.PlayerArtifactStorageMenu;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.client.Minecraft;

public class HandleClientPacket {
    public static void handleArtifactStorageInfoPacket(PacketContext<ArtifactStorageInfoPacket> ctx) {
        if(Minecraft.getInstance().player.containerMenu instanceof PlayerArtifactStorageMenu menu)
        {
            menu.setContainerSize(ctx.message().getContainerSize());
            menu.setScrollIndex(ctx.message().getScrollIndex());
        }
    }
}
