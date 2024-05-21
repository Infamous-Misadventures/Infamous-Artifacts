package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.cost;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;

/**
 * Represents a cost that will be consumed when an artifact is used
 */
public interface CostComponent {

    /**
     * Validates whether the cost can be consumed in the given context
     * @param artifactUseContext the context in which the artifact is being used
     * @return
     */
    boolean validate(ArtifactUseContext artifactUseContext);

    /**
     * Consumes the cost in the given context
     * @param artifactUseContext the context in which the artifact is being used
     */
    void consume(ArtifactUseContext artifactUseContext);
}
