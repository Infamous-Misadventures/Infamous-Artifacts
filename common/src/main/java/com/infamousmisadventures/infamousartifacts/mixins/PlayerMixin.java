package com.infamousmisadventures.infamousartifacts.mixins;

import com.infamousmisadventures.infamousartifacts.inventory.IPlayerArtifactsDataHolder;
import com.infamousmisadventures.infamousartifacts.inventory.PlayerArtifacts;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class)
public abstract class PlayerMixin implements IPlayerArtifactsDataHolder {

    @Unique
    private PlayerArtifacts ia$playerArtifacts = null;

    public PlayerArtifacts getOrCreatePlayerArtifacts() {
        if (ia$playerArtifacts == null) {
            ia$playerArtifacts = new PlayerArtifacts();
        }
        return ia$playerArtifacts;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        nbt.put("PlayerArtifacts", this.getOrCreatePlayerArtifacts().serializeNBT());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        getOrCreatePlayerArtifacts().deserializeNBT(nbt.getCompound("PlayerArtifacts"));
    }

}
