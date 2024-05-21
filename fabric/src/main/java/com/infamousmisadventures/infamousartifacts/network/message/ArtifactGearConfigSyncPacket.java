package com.infamousmisadventures.infamousartifacts.network.message;

import com.infamousmisadventures.infamousartifacts.item.artifact.config.ArtifactGearConfig;
import com.infamousmisadventures.infamousartifacts.registry.ArtifactGearConfigRegistry;
import com.mojang.serialization.Codec;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

import java.util.HashMap;
import java.util.Map;

public class ArtifactGearConfigSyncPacket implements S2CPacket {
    private static final Codec<Map<ResourceLocation, ArtifactGearConfig>> MAPPER =
            Codec.unboundedMap(ResourceLocation.CODEC, ArtifactGearConfig.CODEC);

    public final Map<ResourceLocation, ArtifactGearConfig> data;

    public ArtifactGearConfigSyncPacket(Map<ResourceLocation, ArtifactGearConfig> data) {
        this.data = data;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.data).result().orElse(new CompoundTag())));
    }

    public static ArtifactGearConfigSyncPacket decode(FriendlyByteBuf buffer) {
        return new ArtifactGearConfigSyncPacket(MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handle(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        ArtifactGearConfigRegistry.setData(this.data);
    }
}
