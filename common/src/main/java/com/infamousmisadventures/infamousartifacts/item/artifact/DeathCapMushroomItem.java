package com.infamousmisadventures.infamousartifacts.item.artifact;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DeathCapMushroomItem extends AbstractArtifact {
    public DeathCapMushroomItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useArtifact(ArtifactUseContext c) {
        LivingEntity playerIn = c.artifactUser();
        ItemStack itemstack = c.itemStack();

        MobEffectInstance haste = new MobEffectInstance(MobEffects.DIG_SPEED, 180, 3);
        MobEffectInstance swiftness = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 180, 1);
        playerIn.addEffect(haste);
        playerIn.addEffect(swiftness);

        return InteractionResult.SUCCESS;
    }
}
