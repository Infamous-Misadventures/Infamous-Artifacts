package com.infamousmisadventures.infamousartifacts.network.packets;

import commonnetwork.api.Network;

public class RegisterPackets {

    public static void registerPackets(){
        Network
                .registerPacket(ArtifactGearConfigSyncPacket.CHANNEL, ArtifactGearConfigSyncPacket.class, ArtifactGearConfigSyncPacket::encode, ArtifactGearConfigSyncPacket::new, ArtifactGearConfigSyncPacket::handle)
                .registerPacket(ScrollWindowUpdatePacket.CHANNEL, ScrollWindowUpdatePacket.class, ScrollWindowUpdatePacket::encode, ScrollWindowUpdatePacket::new, ScrollWindowUpdatePacket::handle)
                .registerPacket(ArtifactStorageInfoPacket.CHANNEL, ArtifactStorageInfoPacket.class, ArtifactStorageInfoPacket::encode, ArtifactStorageInfoPacket::new, ArtifactStorageInfoPacket::handle)
        ;
    }
}
