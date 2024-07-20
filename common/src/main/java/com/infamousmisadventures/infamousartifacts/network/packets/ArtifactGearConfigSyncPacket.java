package com.infamousmisadventures.infamousartifacts.network.packets;

import com.infamousmisadventures.infamousartifacts.IAConstants;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.ArtifactGearConfig;
import com.infamousmisadventures.infamousartifacts.registry.ArtifactGearConfigRegistry;
import com.mojang.serialization.Codec;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class ArtifactGearConfigSyncPacket {
    public static final ResourceLocation CHANNEL = modLoc("artifact_gear_config_sync");
    private static final Codec<Map<ResourceLocation, ArtifactGearConfig>> MAPPER =
            Codec.unboundedMap(ResourceLocation.CODEC, ArtifactGearConfig.CODEC);

    public final Map<ResourceLocation, ArtifactGearConfig> data;

    public ArtifactGearConfigSyncPacket(Map<ResourceLocation, ArtifactGearConfig> data) {
        this.data = data;
    }

    public ArtifactGearConfigSyncPacket(FriendlyByteBuf buffer) {
        this.data = MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.data).result().orElse(new CompoundTag())));
    }

    public Map<ResourceLocation, ArtifactGearConfig> getData() {
        return data;
    }

    public static void handle(PacketContext<ArtifactGearConfigSyncPacket> ctx) {
        if (Side.CLIENT.equals(ctx.side()))
        {
            ArtifactGearConfigRegistry.setData(ctx.message().getData());
        }
        else
        {
            IAConstants.LOGGER.error("ScrollWindowUpdatePacket received on the server");
        }
    }
}
