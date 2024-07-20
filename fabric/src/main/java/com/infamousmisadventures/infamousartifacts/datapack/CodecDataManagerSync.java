package com.infamousmisadventures.infamousartifacts.datapack;

import com.infamousmisadventures.infamousartifacts.platform.Services;
import com.infamousmisadventures.infamousartifacts.util.data.CodecDataManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.function.Function;

public class CodecDataManagerSync {

    /**
     * This should be called at most once, during construction of your mod (static init of your main mod class is fine)
     * Calling this method automatically subscribes a packet-sender to ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.
     *
     * @param <PACKET>      the packet type that will be sent on the given channel
     * @param packetFactory A packet constructor or factory method that converts the given map to a packet object to send on the given channel
     */
    public static <PACKET, T> void subscribeAsSyncable(final Function<Map<ResourceLocation, T>, PACKET> packetFactory,
                                                       CodecDataManager<T> dataManager) {
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(getDatapackSyncListener(packetFactory, dataManager));
    }

    /**
     * Generate an event listener function for the on-datapack-sync event
     **/
    private static <PACKET, T> ServerLifecycleEvents.SyncDataPackContents getDatapackSyncListener(final Function<Map<ResourceLocation, T>, PACKET> packetFactory,
                                                                                                  CodecDataManager<T> dataManager) {
        return (player, joined) -> {
            PACKET packet = packetFactory.apply(dataManager.getData());
            Services.NETWORK_HANDLER.sendToClient(packet, player);
        };
    }
}
