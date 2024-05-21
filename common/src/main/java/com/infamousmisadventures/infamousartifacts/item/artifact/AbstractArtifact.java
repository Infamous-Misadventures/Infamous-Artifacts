package com.infamousmisadventures.infamousartifacts.item.artifact;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.ArtifactGearConfig;
import com.infamousmisadventures.infamousartifacts.item.artifact.config.component.cost.CostComponent;
import com.infamousmisadventures.infamousartifacts.mixins.ItemAccessor;
import com.infamousmisadventures.infamousartifacts.mixins.UseOnContextAccessor;
import com.infamousmisadventures.infamousartifacts.registry.ArtifactGearConfigRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
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
import static com.infamousmisadventures.infamousartifacts.registry.ItemTagWrappers.ARTIFACT_REPAIR_ITEMS;
import static java.util.UUID.randomUUID;

public class AbstractArtifact extends Item { //implements IReloadableGear {
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
        artifactGearConfig = ArtifactGearConfigRegistry.getConfig(BuiltInRegistries.ITEM.getKey(this));
        //artifactGearConfig = new ArtifactGearConfig(new ArrayList<>(), 20, 2, new ArrayList<>(), new ArrayList<>());
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

    public void putArtifactOnCooldown(ArtifactUseContext artifactUseContext) {
        int cooldownInTicks = getCooldownInSeconds() * 20;
        LivingEntity livingEntity = artifactUseContext.artifactUser();
        if(livingEntity instanceof Player playerIn) {
            AttributeInstance artifactCooldownMultiplierAttribute = playerIn.getAttribute(ARTIFACT_COOLDOWN_MULTIPLIER.get());
            double attributeModifier = artifactCooldownMultiplierAttribute != null ? artifactCooldownMultiplierAttribute.getValue() : 1.0D;
            playerIn.getCooldowns().addCooldown(this, Math.max(0, (int) (cooldownInTicks * attributeModifier)));
        }
    }

    public Rarity getRarity(ItemStack itemStack) {
        return Rarity.RARE;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ARTIFACT_REPAIR_ITEMS) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        return useArtifactBase(new ArtifactUseContext(useOnContext.getLevel(), useOnContext.getPlayer(), ((UseOnContextAccessor) useOnContext).getHitResult(), useOnContext.getItemInHand()));
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
        InteractionResult interactionResult = useArtifactBase(new ArtifactUseContext(level, player, blockHitResult, player.getItemInHand(interactionHand)));
        return new InteractionResultHolder<>(interactionResult, interactionResult == InteractionResult.SUCCESS ? player.getItemInHand(interactionHand) : ItemStack.EMPTY);
    }

    private InteractionResult useArtifactBase(ArtifactUseContext artifactUseContext) {
        if(isOnCooldown(artifactUseContext) || !areCostsValid(artifactUseContext)) return InteractionResult.PASS;
        for (CostComponent costComponent : artifactGearConfig.costComponentList()) {
            costComponent.consume(artifactUseContext);
        }
        InteractionResult useResult = useArtifact(artifactUseContext);
        /*if (useResult.getResult().consumesAction() && artifactUseContext.getPlayer() != null && !artifactUseContext.getLevel().isClientSide) {
            fireArtifactActivated(artifactUseContext.getPlayer(), artifactUseContext.getItemStack());
        }*/
        //itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemstack)));

        putArtifactOnCooldown(artifactUseContext);
        return useResult;
    }

    private boolean areCostsValid(ArtifactUseContext artifactUseContext) {
        for (CostComponent costComponent : artifactGearConfig.costComponentList()) {
            if (!costComponent.validate(artifactUseContext)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOnCooldown(ArtifactUseContext artifactUseContext) {
        if (artifactUseContext.artifactUser() != null) {
            ItemStack itemStack = artifactUseContext.itemStack();
            if (artifactUseContext.artifactUser() instanceof Player player && player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                return true;
            }
        }
        return false;
    }

    public InteractionResult useArtifact(ArtifactUseContext context){
        artifactGearConfig.targetingComponentList().forEach(targettingComponent -> {
                targettingComponent.target(context);
        });

        return InteractionResult.SUCCESS;
    }

    public int getCooldownInSeconds() {
        return artifactGearConfig.cooldown();
    }

    //Event Methods
    /*public static void triggerSynergy(Player player, ItemStack stack) {
        ArtifactEvent.Activated event = new ArtifactEvent.Activated(player, stack);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
    }*/

    //Attribute Methods
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
        return getAttributeModifiersForSlot(getUUIDForSlot(slotIndex));
    }

    private ImmutableMultimap<Attribute, AttributeModifier> getAttributeModifiersForSlot(UUID slot_uuid) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        artifactGearConfig.attributes().forEach(attributeModifier -> {
            Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(attributeModifier.getAttributeResourceLocation());
            if (attribute != null) {
                builder.put(attribute, new AttributeModifier(slot_uuid, "Artifact modifier", attributeModifier.getAmount(), attributeModifier.getOperation()));
            }
        });
        return builder.build();
    }

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