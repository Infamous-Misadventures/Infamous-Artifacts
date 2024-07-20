package com.infamousmisadventures.infamousartifacts.inventory;

import com.infamousmisadventures.infamousartifacts.network.packets.ArtifactStorageInfoPacket;
import com.infamousmisadventures.infamousartifacts.network.packets.ScrollWindowUpdatePacket;
import com.infamousmisadventures.infamousartifacts.platform.Services;
import com.infamousmisadventures.infamousartifacts.registry.IABlocks;
import com.infamousmisadventures.infamousartifacts.registry.IAMenuTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import static com.infamousmisadventures.infamousartifacts.client.gui.screens.inventory.PlayerArtifactStorageScreen.SCROLL_WINDOW_COLUMNS;
import static com.infamousmisadventures.infamousartifacts.client.gui.screens.inventory.PlayerArtifactStorageScreen.SCROLL_WINDOW_ROWS;

public class PlayerArtifactStorageMenu
        extends AbstractContainerMenu {
    private static final int ACTIVE_SLOT_1 = 0;
    private static final int ACTIVE_SLOT_2 = 1;
    private static final int ACTIVE_SLOT_3 = 2;
    private static final int SCROLLER_SLOT_START = 3;
    private static final int SCROLLER_SLOT_END = 22;
    private static final int INV_SLOT_START = 23;
    private static final int INV_SLOT_END = 50;
    private static final int USE_ROW_SLOT_START = 51;
    private static final int USE_ROW_SLOT_END = 60;
    private final Level level;
    private final Inventory inventory;
    private int containerSize;
    PlayerArtifacts artifacts;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private final Slot activeSlot1;
    private final Slot activeSlot2;
    private final Slot activeSlot3;
    public final SimpleContainer scrollWindow = new SimpleContainer(20);
    int scrollIndex = 0;
    /**
     * The inventory that stores the output of the crafting recipe.
     */
    final ResultContainer resultContainer = new ResultContainer();

    public PlayerArtifactStorageMenu(int i, Inventory inventory) {
        this(i, inventory, ContainerLevelAccess.NULL, ((IPlayerArtifactsDataHolder) inventory.player).getOrCreatePlayerArtifacts());
    }

    public PlayerArtifactStorageMenu(int i, Inventory inventory, final ContainerLevelAccess containerLevelAccess, PlayerArtifacts playerArtifacts) {
        super(IAMenuTypes.PLAYER_ARTIFACT_STORAGE_MENU.get(), i);
        access = containerLevelAccess;
        int j;
        this.level = inventory.player.level();
        this.inventory = inventory;
        artifacts = playerArtifacts;
        PlayerArtifactStorageContainer container = artifacts.getContainer();
        PlayerActiveArtifacts activeArtifacts = artifacts.getActiveArtifacts();
        this.activeSlot1 = this.addSlot(new Slot(activeArtifacts, 0, 118, 45));
        this.activeSlot2 = this.addSlot(new Slot(activeArtifacts, 1, 136, 45));
        this.activeSlot3 = this.addSlot(new Slot(activeArtifacts, 2, 154, 45));
        for (j = 0; j < 4; ++j) {
            for (int k = 0; k < 5; ++k) {
                this.addSlot(new ScrollingWindowSlot(this, scrollWindow, k + j * 5, 8 + k * 18, 18 + j * 18));
            }
        }
        for (j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18));
            }
        }
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, 161));
        }
        if(!this.level.isClientSide()){
            registerUpdateListener(this::containerChanged);
            scrollTo(0);
        }
    }

    private void containerChanged(Container container) {
        if (this.level.isClientSide()) {
            return;
        }
        if (container == artifacts.getContainer() && inventory.player instanceof ServerPlayer player) {
            Services.NETWORK_HANDLER.sendToClient(new ArtifactStorageInfoPacket(container.getContainerSize(), scrollIndex), player);
            this.setContainerSize(container.getContainerSize());
        }
    }

    public int getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(int containerSize) {
        this.containerSize = containerSize;
    }

    public int getScrollIndex() {
        return scrollIndex;
    }

    public void setScrollIndex(int scrollIndex) {
        this.scrollIndex = scrollIndex;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, IABlocks.ARTIFACT_STORAGE.get());
    }

    @Override
    public void slotsChanged(Container container) {
    }


    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return false;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (index == 1) {
                item.onCraftedBy(itemStack2, player.level(), player);
                if (!this.moveItemStackTo(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (index == 0 ? !this.moveItemStackTo(itemStack2, 2, 38, false) : (this.level.getRecipeManager().getRecipeFor(RecipeType.STONECUTTING, new SimpleContainer(itemStack2), this.level).isPresent() ? !this.moveItemStackTo(itemStack2, 0, 1, false) : (index >= 2 && index < 29 ? !this.moveItemStackTo(itemStack2, 29, 38, false) : index >= 29 && index < 38 && !this.moveItemStackTo(itemStack2, 2, 29, false)))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            slot.setChanged();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemStack2);
            this.broadcastChanges();
        }
        return itemStack;
    }

    public void registerUpdateListener(ContainerListener containerChanged) {
        artifacts.getContainer().addListener(containerChanged);
    }

    public void scrollTo(int i) {
        if(this.level.isClientSide()){
            ScrollWindowUpdatePacket packet = new ScrollWindowUpdatePacket(i);
            Services.NETWORK_HANDLER.sendToServer(packet);
        }else {
            this.scrollWindow.clearContent();
            scrollIndex = i;
            PlayerArtifactStorageContainer storage = this.artifacts.getContainer();
            for (int j = 0; j < SCROLL_WINDOW_ROWS; ++j) {
                for (int k = 0; k < SCROLL_WINDOW_COLUMNS; ++k) {
                    int l = k + (j + i) * SCROLL_WINDOW_COLUMNS;
                    int m = k + j * SCROLL_WINDOW_COLUMNS;
                    if (l >= 0 && l < storage.getContainerSize()) {
                        this.getSlot(m + 3).set(storage.getItem(l));
                    } else {
                        this.getSlot(m + 3).set(ItemStack.EMPTY);
                    }
                }
            }
            containerChanged(storage);
        }
    }

    public void removeFromContainer(int index) {
        int slotIndex = index + scrollIndex * SCROLL_WINDOW_COLUMNS;
        artifacts.getContainer().removeItem(slotIndex, 1);
        scrollTo(scrollIndex);
    }

    public void addToContainer(ItemStack itemStack, int containerSlot) {
        int slotIndex = containerSlot + scrollIndex * SCROLL_WINDOW_COLUMNS;
        ItemStack item = artifacts.getContainer().getItem(slotIndex);
        if (item.equals(itemStack) || itemStack.isEmpty()) {
            return;
        }
        artifacts.getContainer().addItem(itemStack);
        scrollTo(scrollIndex);
    }

    private static class ScrollingWindowSlot extends Slot {
        private final PlayerArtifactStorageMenu menu;

        public ScrollingWindowSlot(PlayerArtifactStorageMenu menu, Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
            this.menu = menu;
        }

        @Override
        public ItemStack remove(int $$0) {
            ItemStack removed = super.remove($$0);
            if (this.menu.level instanceof ServerLevel) {
                this.menu.removeFromContainer(this.getContainerSlot());
            }
            return removed;
        }

        @Override
        public void set(ItemStack $$0) {
            super.set($$0);
            if (this.menu.level instanceof ServerLevel) {
                menu.addToContainer($$0, this.getContainerSlot());
            }
        }
    }
}