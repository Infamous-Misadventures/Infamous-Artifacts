package com.infamousmisadventures.infamousartifacts.item.artifact;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DeathCapMushroomItem extends AbstractArtifact {
    public DeathCapMushroomItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useArtifact(ArtifactUseContext c) {
        Player playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();

        MobEffectInstance haste = new MobEffectInstance(MobEffects.DIG_SPEED, 180, 3);
        MobEffectInstance swiftness = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 180, 1);
        playerIn.addEffect(haste);
        playerIn.addEffect(swiftness);

        //itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemstack)));

        putArtifactOnCooldown(playerIn);
        return InteractionResult.SUCCESS;
    }


    @Override
    public int getCooldownInSeconds() {
        return 2;
    }

//    @Override
//    public int getDurationInSeconds() {
//        return 9;
//    }
}
