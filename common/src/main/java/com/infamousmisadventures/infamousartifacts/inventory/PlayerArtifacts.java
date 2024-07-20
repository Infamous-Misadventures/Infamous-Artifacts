package com.infamousmisadventures.infamousartifacts.inventory;

import com.infamousmisadventures.infamousartifacts.util.INBTSerializable;
import net.minecraft.nbt.CompoundTag;

public class PlayerArtifacts implements INBTSerializable<CompoundTag> {

    PlayerArtifactStorageContainer container = new PlayerArtifactStorageContainer();
    PlayerActiveArtifacts activeArtifacts = new PlayerActiveArtifacts();

    public PlayerArtifactStorageContainer getContainer() {
        return container;
    }

    public PlayerActiveArtifacts getActiveArtifacts() {
        return activeArtifacts;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("container", container.createTag());
        tag.put("activeArtifacts", activeArtifacts.createTag());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        container.fromTag(nbt.getList("container", 10));
        activeArtifacts.fromTag(nbt.getList("activeArtifacts", 10));
    }
}
