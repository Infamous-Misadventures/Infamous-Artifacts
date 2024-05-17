package com.infamousmisadventures.infamousartifacts.item.artifact;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.ArtifactGearConfig;
import com.infamousmisadventures.infamousartifacts.mixins.ItemAccessor;
import com.infamousmisadventures.infamousartifacts.mixins.UseOnContextAccessor;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.UUID;

import static com.infamousmisadventures.infamousartifacts.registry.IAAttributes.ARTIFACT_COOLDOWN_MULTIPLIER;
import static com.infamousmisadventures.infamousartifacts.tag.ItemTagWrappers.ARTIFACT_REPAIR_ITEMS;
import static java.util.UUID.randomUUID;

public abstract class AbstractArtifact extends Item { //implements IReloadableGear {
    protected final UUID SLOT0_UUID = UUID.fromString("7037798e-ac2c-4711-aa72-ba73589f1411");
    protected final UUID SLOT1_UUID = UUID.fromString("1906bae9-9f26-4194-bb8a-ef95b8cad134");
    protected final UUID SLOT2_UUID = UUID.fromString("b99aa930-03d0-4b2d-aa69-7b5d943dd75c");

    private Multimap<Attribute, AttributeModifier> defaultModifiers;
    protected boolean procOnItemUse = false;
    private ArtifactGearConfig artifactGearConfig;

    public AbstractArtifact(Properties properties) {
        super(properties.defaultDurability(64));
        reload();
    }

    //@Override
    public void reload() {
        //artifactGearConfig = ArtifactGearConfigRegistry.getConfig(ForgeRegistries.ITEMS.getKey(this));
        artifactGearConfig = new ArtifactGearConfig(new ArrayList<>(), 20, 2, 0);
        ((ItemAccessor) this).setMaxDamage(artifactGearConfig.durability());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        artifactGearConfig.attributes().forEach(attributeModifier -> {
            Attribute attribute = attributeModifier.getAttribute();
            if (attribute != null) {
                UUID uuid = randomUUID();
                builder.put(attribute, new AttributeModifier(uuid, "Artifact modifier", attributeModifier.getAmount(), attributeModifier.getOperation()));
            }
        });
        this.defaultModifiers = builder.build();
    }

    public void putArtifactOnCooldown(Player playerIn) {
        int cooldownInTicks = getCooldownInSeconds() * 20;

        AttributeInstance artifactCooldownMultiplierAttribute = playerIn.getAttribute(ARTIFACT_COOLDOWN_MULTIPLIER.get());
        double attributeModifier = artifactCooldownMultiplierAttribute != null ? artifactCooldownMultiplierAttribute.getValue() : 1.0D;
        playerIn.getCooldowns().addCooldown(this, Math.max(0, (int) (cooldownInTicks * attributeModifier)));
    }

    /*public static void triggerSynergy(Player player, ItemStack stack) {
        ArtifactEvent.Activated event = new ArtifactEvent.Activated(player, stack);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
    }*/

    public Rarity getRarity(ItemStack itemStack) {
        return Rarity.RARE;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ARTIFACT_REPAIR_ITEMS) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        return useArtifactBase(new ArtifactUseContext(useOnContext.getLevel(), useOnContext.getPlayer(), useOnContext.getItemInHand(), ((UseOnContextAccessor) useOnContext).getHitResult()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        HitResult hitResult = player.pick(30D, 1.0F, false);
        BlockHitResult blockHitResult = null;
        if (hitResult == null || hitResult.getType() == HitResult.Type.MISS) {
            blockHitResult = new BlockHitResult(player.position(), Direction.UP, player.blockPosition(), false);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            blockHitResult = (BlockHitResult) hitResult;
        } else if (hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            blockHitResult = new BlockHitResult(entityHitResult.getEntity().position(), Direction.UP, entityHitResult.getEntity().blockPosition(), false);
        }
        InteractionResult interactionResult = useArtifactBase(new ArtifactUseContext(level, player, player.getItemInHand(interactionHand), blockHitResult));
        return new InteractionResultHolder<>(interactionResult, interactionResult == InteractionResult.SUCCESS ? player.getItemInHand(interactionHand) : ItemStack.EMPTY);
    }

    private InteractionResult useArtifactBase(ArtifactUseContext artifactUseContext) {
        if (artifactUseContext.getPlayer() != null) {
            ItemStack itemStack = artifactUseContext.getItemStack();
            if (artifactUseContext.getPlayer().getCooldowns().isOnCooldown(itemStack.getItem())) {
                return InteractionResult.SUCCESS;
            }
        }
        InteractionResult useResult = useArtifact(artifactUseContext);
        /*if (useResult.getResult().consumesAction() && artifactUseContext.getPlayer() != null && !artifactUseContext.getLevel().isClientSide) {
            triggerSynergy(artifactUseContext.getPlayer(), artifactUseContext.getItemStack());
        }*/
        return useResult;
    }

    public abstract InteractionResult useArtifact(ArtifactUseContext context);

    public int getCooldownInSeconds() {
        return artifactGearConfig.cooldown();
    }

//    public int getDurationInSeconds() {
//        return artifactGearConfig.getDuration();
//    }

    /*public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
        return getAttributeModifiersForSlot(getUUIDForSlot(slotIndex));
    }

    private ImmutableMultimap<Attribute, AttributeModifier> getAttributeModifiersForSlot(UUID slot_uuid) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        artifactGearConfig.getAttributes().forEach(attributeModifier -> {
            Attribute attribute = ATTRIBUTES.getValue(attributeModifier.getAttributeResourceLocation());
            if (attribute != null) {
                builder.put(attribute, new AttributeModifier(slot_uuid, "Artifact modifier", attributeModifier.getAmount(), attributeModifier.getOperation()));
            }
        });
        return builder.build();
    }*/

    protected UUID getUUIDForSlot(int slotIndex) {
        switch (slotIndex) {
            case 0:
                return SLOT0_UUID;
            case 1:
                return SLOT1_UUID;
            case 2:
                return SLOT2_UUID;
            default:
                return SLOT2_UUID;
        }
    }
}