package com.infamousmisadventures.infamousartifacts.item.artifact.config.component.targetted;

import com.infamousmisadventures.infamousartifacts.item.artifact.ArtifactUseContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

import static com.infamousmisadventures.infamousartifacts.registry.IATargettedComponentTypes.SPAWN_ITEM;
import static net.minecraft.core.registries.BuiltInRegistries.ITEM;

public record SpawnItemTargettedComponent(List<Item> items) implements BlockTargettedComponent {
    public static Codec<SpawnItemTargettedComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ITEM.byNameCodec().listOf().fieldOf("items").forGetter(SpawnItemTargettedComponent::items)
    ).apply(instance, SpawnItemTargettedComponent::new));

    @Override
    public TargettedComponentType<?> type() {
        return SPAWN_ITEM;
    }

    @Override
    public void apply(ArtifactUseContext context, BlockPos blockPos) {
        Optional<Item> randomElement = getRandomElement(items, context.artifactUser().getRandom());
        randomElement.ifPresent(item -> spawnItem(context, blockPos, item));
    }

    private Optional<Item> getRandomElement(List<Item> items, RandomSource random) {
        if (items.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(items.get(random.nextInt(items.size())));
    }

    private void spawnItem(ArtifactUseContext context, BlockPos blockPos, Item item) {
        Level level = context.level();
        ItemStack itemStack = new ItemStack(item);
        ItemEntity itementity = new ItemEntity(level, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), itemStack);
        itementity.setDefaultPickUpDelay();
        level.addFreshEntity(itementity);
    }
}
