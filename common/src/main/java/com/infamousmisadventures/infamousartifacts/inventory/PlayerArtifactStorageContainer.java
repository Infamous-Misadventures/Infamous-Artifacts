package com.infamousmisadventures.infamousartifacts.inventory;

import com.google.common.collect.Lists;
import com.infamousmisadventures.infamousartifacts.item.artifact.AbstractArtifact;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerArtifactStorageContainer implements Container{

    private static int MAX_CONTAINER_SIZE = 300;

    public final NonNullList<ItemStack> items = NonNullList.create();
    private List<ContainerListener> listeners;

    public void addListener(ContainerListener pListener) {
        if (this.listeners == null) {
            this.listeners = Lists.newArrayList();
        }
        this.listeners.add(pListener);
    }

    /**
     * Removes the specified {@link net.minecraft.world.ContainerListener} from receiving further change notices.
     */
    public void removeListener(ContainerListener pListener) {
        if (this.listeners != null) {
            this.listeners.remove(pListener);
        }
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getItem(int i) {
        return i >= this.getContainerSize() ? ItemStack.EMPTY : this.items.get(i);
    }

    @Override
    public @NotNull ItemStack removeItem(int pIndex, int pCount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.items, pIndex, pCount);
        if (!itemstack.isEmpty()) {
            this.items.remove(pIndex);
            this.setChanged();
        }
        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    public ItemStack addItem(ItemStack pStack) {
        if (pStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack = pStack.copy();
            items.add(itemstack);
            this.setChanged();
            return itemstack;
        }

    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        addItem(pStack);
    }

    @Override
    public void setChanged() {
        if (this.listeners != null) {
            for(ContainerListener containerlistener : this.listeners) {
                containerlistener.containerChanged(this);
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.setChanged();
    }

    public boolean canAddItem(ItemStack pStack) {
        return pStack.getItem() instanceof AbstractArtifact && items.size() < MAX_CONTAINER_SIZE;
    }

    public ListTag createTag() {
        ListTag listtag = new ListTag();

        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                listtag.add(itemstack.save(new CompoundTag()));
            }
        }

        return listtag;
    }

    public void fromTag(ListTag pContainerNbt) {
        this.clearContent();

        for(int i = 0; i < pContainerNbt.size(); ++i) {
            ItemStack itemstack = ItemStack.of(pContainerNbt.getCompound(i));
            if (!itemstack.isEmpty()) {
                this.addItem(itemstack);
            }
        }
    }
}