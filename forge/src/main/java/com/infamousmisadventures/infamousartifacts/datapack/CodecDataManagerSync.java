package com.infamousmisadventures.infamousartifacts.datapack;

import com.infamousmisadventures.infamousartifacts.platform.Services;
import com.infamousmisadventures.infamousartifacts.util.data.CodecDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.OnDatapackSyncEvent;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class CodecDataManagerSync {

    /**
     * This should be called at most once, during construction of your mod (static init of your main mod class is fine)
     * Calling this method automatically subscribes a packet-sender to {@link OnDatapackSyncEvent}.
     *
     * @param <PACKET>      the packet type that will be sent on the given channel
     * @param packetFactory A packet constructor or factory method that converts the given map to a packet object to send on the given channel
     */
    public static <PACKET, T> void subscribeAsSyncable(final Function<Map<ResourceLocation, T>, PACKET> packetFactory,
                                                       CodecDataManager<T> dataManager) {
        MinecraftForge.EVENT_BUS.addListener(getDatapackSyncListener(packetFactory, dataManager));
    }

    /**
     * Generate an event listener function for the on-datapack-sync event
     **/
    private static <PACKET, T> Consumer<OnDatapackSyncEvent> getDatapackSyncListener(final Function<Map<ResourceLocation, T>, PACKET> packetFactory,
                                                                                     CodecDataManager<T> dataManager) {
        return event -> {
            ServerPlayer player = event.getPlayer();
            PACKET packet = packetFactory.apply(dataManager.getData());
            Services.NETWORK_HANDLER.sendToClient(packet, player);
        };
    }
}
