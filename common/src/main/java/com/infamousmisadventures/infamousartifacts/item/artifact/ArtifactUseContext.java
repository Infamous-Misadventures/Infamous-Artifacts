package com.infamousmisadventures.infamousartifacts.item.artifact;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;


public record ArtifactUseContext(Level level, LivingEntity artifactUser, BlockHitResult hitResult, ItemStack itemStack) {

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

    public Direction getHorizontalDirection() {
        return this.artifactUser == null ? Direction.NORTH : this.artifactUser.getDirection();
    }

    public float getRotation() {
        return this.artifactUser == null ? 0.0F : this.artifactUser.getYRot();
    }
}
