package com.infamousmisadventures.infamousartifacts.item.artifact;

import com.google.common.collect.Multimap;
import com.infamousmisadventures.infamousartifacts.mixins.UseOnContextAccessor;
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

import java.util.UUID;

import static com.infamousmisadventures.infamousartifacts.registry.AttributeInit.ARTIFACT_COOLDOWN_MULTIPLIER;
import static com.infamousmisadventures.infamousartifacts.tag.ItemTagWrappers.ARTIFACT_REPAIR_ITEMS;

public abstract class AbstractArtifact extends Item { //implements IReloadableGear {
    protected final UUID SLOT0_UUID = UUID.fromString("7037798e-ac2c-4711-aa72-ba73589f1411");
    protected final UUID SLOT1_UUID = UUID.fromString("1906bae9-9f26-4194-bb8a-ef95b8cad134");
    protected final UUID SLOT2_UUID = UUID.fromString("b99aa930-03d0-4b2d-aa69-7b5d943dd75c");

    private Multimap<Attribute, AttributeModifier> defaultModifiers;
    protected boolean procOnItemUse = false;
    //private ArtifactGearConfig artifactGearConfig;

    public AbstractArtifact(Properties properties) {
        super(properties.defaultDurability(64));
        //reload();
    }

    //@Override
    /*public void reload() {
        artifactGearConfig = ArtifactGearConfigRegistry.getConfig(ForgeRegistries.ITEMS.getKey(this));
        ((ItemAccessor) this).setMaxDamage(artifactGearConfig.getDurability());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        artifactGearConfig.getAttributes().forEach(attributeModifier -> {
            Attribute attribute = ATTRIBUTES.getValue(attributeModifier.getAttributeResourceLocation());
            if (attribute != null) {
                UUID uuid = randomUUID();
                builder.put(attribute, new AttributeModifier(uuid, "Artifact modifier", attributeModifier.getAmount(), attributeModifier.getOperation()));
            }
        });
        this.defaultModifiers = builder.build();
    }*/

    public static void putArtifactOnCooldown(Player playerIn, Item item) {
        int cooldownInTicks = item instanceof AbstractArtifact ? 40 : 0;
                //((AbstractArtifact) item).getCooldownInSeconds() * 20 : 0;

        AttributeInstance artifactCooldownMultiplierAttribute = playerIn.getAttribute(ARTIFACT_COOLDOWN_MULTIPLIER.get());
        double attributeModifier = artifactCooldownMultiplierAttribute != null ? artifactCooldownMultiplierAttribute.getValue() : 1.0D;
        playerIn.getCooldowns().addCooldown(item, Math.max(0, (int) (cooldownInTicks * attributeModifier)));
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
        //return useArtifactBase(new ArtifactUseContext(level, player, player.getItemInHand(interactionHand), interactionHand, player.pick()));
        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(interactionHand));
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

    /*public int getCooldownInSeconds() {
        return artifactGearConfig.getCooldown();
    }

    public int getDurationInSeconds() {
        return artifactGearConfig.getDuration();
    }*/

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