package com.infamousmisadventures.infamousartifacts.network.packets;

import com.infamousmisadventures.infamousartifacts.IAConstants;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class ArtifactStorageInfoPacket {
    public static final ResourceLocation CHANNEL = modLoc("artifact_storage_info");
    private final int containerSize;
    private final int scrollIndex;

    public ArtifactStorageInfoPacket(int containerSize, int scrollIndex) {
        this.containerSize = containerSize;
        this.scrollIndex = scrollIndex;
    }

    public ArtifactStorageInfoPacket(FriendlyByteBuf buffer) {
        containerSize = buffer.readInt();
        scrollIndex = buffer.readInt();
    }

    public int getContainerSize() {
        return containerSize;
    }

    public int getScrollIndex() {
        return scrollIndex;
    }

    public static ArtifactStorageInfoPacket decode(FriendlyByteBuf buffer) {
        return new ArtifactStorageInfoPacket(buffer);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(containerSize);
        buffer.writeInt(scrollIndex);
    }

    public static void handle(PacketContext<ArtifactStorageInfoPacket> ctx) {
        if (Side.CLIENT.equals(ctx.side()))
        {
            HandleClientPacket.handleArtifactStorageInfoPacket(ctx);
        }
        else
        {
            IAConstants.LOGGER.error("ScrollWindowUpdatePacket received on the server");
        }
    }
}
