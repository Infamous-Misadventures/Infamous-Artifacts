package com.infamousmisadventures.infamousartifacts.item.artifact;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;


public class ArtifactUseContext {
    private final Player player;
    private final BlockHitResult hitResult;
    private final Level level;
    private final ItemStack itemStack;

    public ArtifactUseContext(Level level, Player player, ItemStack item, BlockHitResult hitResult) {
        this.player = player;
        this.hitResult = hitResult;
        this.itemStack = item;
        this.level = level;
    }

    protected final BlockHitResult getHitResult() {
        return this.hitResult;
    }

    public boolean isHitMiss() {
        return this.hitResult.getType() == BlockHitResult.Type.MISS;
    }

    public BlockPos getClickedPos() {
        return this.hitResult.getBlockPos();
    }

    public Direction getClickedFace() {
        return this.hitResult.getDirection();
    }

    public Vec3 getClickLocation() {
        return this.hitResult.getLocation();
    }

    public boolean isInside() {
        return this.hitResult.isInside();
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Level getLevel() {
        return this.level;
    }

    public Direction getHorizontalDirection() {
        return this.player == null ? Direction.NORTH : this.player.getDirection();
    }

    public boolean isSecondaryUseActive() {
        return this.player != null && this.player.isSecondaryUseActive();
    }

    public float getRotation() {
        return this.player == null ? 0.0F : this.player.getYRot();
    }
}
