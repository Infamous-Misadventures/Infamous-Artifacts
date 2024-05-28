package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import net.minecraft.world.entity.LivingEntity;

public interface EntityTargettedComponent extends TargettedComponent {

    void apply(ArtifactUseContext context, LivingEntity entity);
}
