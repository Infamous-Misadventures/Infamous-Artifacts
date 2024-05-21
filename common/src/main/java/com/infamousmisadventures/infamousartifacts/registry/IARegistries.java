package com.infamousmisadventures.infamousartifacts.registry;

import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted.TargettedComponentType;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetting.TargettingComponentType;
import com.infamousmisadventures.infamousartifacts.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class IARegistries {
    public static Registry<TargettedComponentType> TARGETTED_COMPONENT_TYPE = createRegistry(TargettedComponentType.class, modLoc("targetted_component_type"));

    public static Registry<TargettingComponentType> TARGETTING_COMPONENT_TYPE = createRegistry(TargettingComponentType.class, modLoc("targetting_component_type"));


    private static <P> Registry<P> createRegistry(Class<P> classType, ResourceLocation registryLocation) {
        return Services.REGISTRY_CREATOR.createRegistry(classType, registryLocation);
    }

}
