package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import net.minecraft.core.BlockPos;

public interface BlockTargettedComponent extends TargettedComponent {
    void apply(ArtifactUseContext context, BlockPos pos);
}
