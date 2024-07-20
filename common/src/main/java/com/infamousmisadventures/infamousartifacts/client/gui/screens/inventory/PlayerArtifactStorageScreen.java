package com.infamousmisadventures.infamousartifacts.client.gui.screens.inventory;

import com.infamousmisadventures.infamousartifacts.inventory.PlayerArtifactStorageMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

import static com.infamousmisadventures.infamousartifacts.util.ResourceLocationHelper.modLoc;

public class PlayerArtifactStorageScreen
        extends AbstractContainerScreen<PlayerArtifactStorageMenu> implements MenuAccess<PlayerArtifactStorageMenu> {
    private static final ResourceLocation BG_LOCATION = modLoc("textures/gui/container/player_artifact_storage_screen.png");
    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    public static final int SCROLL_WINDOW_COLUMNS = 5;
    public static final int SCROLL_WINDOW_ROWS = 4;
    private static final int SCROLLER_FULL_HEIGHT = 70;

    private static final int RECIPES_X = 52;
    private static final int RECIPES_Y = 14;
    private float scrollOffs = 0;
    private boolean scrolling;

    public PlayerArtifactStorageScreen(PlayerArtifactStorageMenu playerArtifactStorageMenu, Inventory inventory, Component component) {
        super(playerArtifactStorageMenu, inventory, component);
        this.imageWidth = 176;
        this.imageHeight = 185;
        this.inventoryLabelY = this.imageHeight - 94;
        this.menu.scrollTo(0);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(BG_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int k = (int)((SCROLLER_FULL_HEIGHT - SCROLLER_HEIGHT) * this.scrollOffs);
        guiGraphics.blit(BG_LOCATION, i + 102, j + 18 + k, 176 + (this.isScrollBarActive() ? 0 : SCROLLER_WIDTH), 0, SCROLLER_WIDTH, SCROLLER_HEIGHT);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        int i = ((this.width - this.imageWidth) / 2) + 102;
        int j = ((this.height - this.imageHeight) / 2) + 18;
        if (mouseX >= (double)i && mouseX < (double)(i + SCROLLER_WIDTH) && mouseY >= (double)j && mouseY < (double)(j + SCROLLER_FULL_HEIGHT)) {
            this.scrolling = true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling && this.isScrollBarActive()) {
            int i = ((this.height - this.imageHeight) / 2) + 18;
            int j = i + SCROLLER_FULL_HEIGHT;
            this.scrollOffs = ((float)mouseY - (float)i - 7.5f) / ((float)(j - i) - 15.0f);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0f, 1.0f);
            menu.scrollTo(getRowIndexForScroll(this.scrollOffs));
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.isScrollBarActive()) {
            int i = this.calculateRowCount();
            float f = (float)delta / (float)i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0f, 1.0f);
            menu.scrollTo(getRowIndexForScroll(this.scrollOffs));
        }
        return true;
    }

    protected int getRowIndexForScroll(float pScrollOffs) {
        return Math.max((int)((double)(pScrollOffs * (float)this.calculateRowCount()) + 0.5D), 0);
    }

    protected int calculateRowCount() {
        return Mth.positiveCeilDiv(this.menu.getContainerSize() + 1, SCROLL_WINDOW_COLUMNS) - SCROLL_WINDOW_ROWS;
    }

    private boolean isScrollBarActive() {
        return this.menu.getContainerSize() >= 20;
    }
}
