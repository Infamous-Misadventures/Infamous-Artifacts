package com.infamousmisadventures.infamousartifacts.platform;

import com.infamousmisadventures.infamousartifacts.IAConstants;
import com.infamousmisadventures.infamousartifacts.platform.services.INetworkHandler;
import com.infamousmisadventures.infamousartifacts.platform.services.IPlatformHelper;
import com.infamousmisadventures.infamousartifacts.platform.services.IRegistrar;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IRegistrar REGISTRAR = load(IRegistrar.class);
    public static final INetworkHandler NETWORK_HANDLER = load(INetworkHandler.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        IAConstants.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);

        return loadedService;
    }
}