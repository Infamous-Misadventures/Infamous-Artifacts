package com.infamousmisadventures.infamousartifacts.block;

import com.infamousmisadventures.infamousartifacts.inventory.IPlayerArtifactsDataHolder;
import com.infamousmisadventures.infamousartifacts.inventory.PlayerArtifactStorageMenu;
import com.infamousmisadventures.infamousartifacts.inventory.PlayerArtifacts;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ArtifactStorageBlock extends Block {
    private static final Component CONTAINER_TITLE = Component.translatable("container.artifact_storage");

    public ArtifactStorageBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((p_277304_, p_277305_, p_277306_) -> {
            return new SmithingMenu(p_277304_, p_277305_, ContainerLevelAccess.create(pLevel, pPos));
        }, CONTAINER_TITLE);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        PlayerArtifacts playerArtifacts = new PlayerArtifacts();
        if (playerArtifacts != null) {
            if (pLevel.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
                    return new PlayerArtifactStorageMenu(p_53124_, p_53125_, ContainerLevelAccess.create(pLevel, pPos), ((IPlayerArtifactsDataHolder) p_53125_.player).getOrCreatePlayerArtifacts());
                }, CONTAINER_TITLE));
                return InteractionResult.CONSUME;
            }
        } else {
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }


}
