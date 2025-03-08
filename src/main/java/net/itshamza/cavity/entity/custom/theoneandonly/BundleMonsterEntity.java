package net.itshamza.cavity.entity.custom.theoneandonly;

import net.itshamza.cavity.screen.BundleMonsterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class BundleMonsterEntity extends MonsterEntity implements ContainerListener, HasCustomInventoryScreen {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(BundleMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    private static final EntityDataAccessor<Boolean> DATA_ID_CHEST = SynchedEntityData.defineId(BundleMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    public static final int INV_CHEST_COUNT = 15;
    protected SimpleContainer inventory = new SimpleContainer(27);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID =
            SynchedEntityData.defineId(BundleMonsterEntity.class, EntityDataSerializers.OPTIONAL_UUID);


    public SimpleContainer getInventory() {
        return inventory;
    }

    public BundleMonsterEntity(EntityType<? extends MonsterEntity> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.2D, 5.0F, 2.0F));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 4f);
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return !this.hasCustomName();
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = (int) 15;
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    public void openCustomInventoryScreen(Player p_218808_) {
        if (!this.level().isClientSide) {
            p_218808_.openMenu(new SimpleMenuProvider((id, inv, ply) ->
                    new BundleMonsterMenu(id, inv, this.getInventory()), Component.translatable("container.bundle_monster"))
            );
        }

    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (!hasChest()) {
            this.setChest(true);
        }
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(DATA_ID_CHEST, false);
        this.entityData.define(OWNER_UUID, Optional.empty());
    }

    protected void updateContainerEquipment() {

    }

    public void containerChanged(Container p_30548_) {
        this.updateContainerEquipment();

    }

    protected void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        this.updateContainerEquipment();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.core.Direction facing) {
        if (capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER && itemHandler != null && this.isAlive())
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (itemHandler != null) {
            net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
            itemHandler = null;
            oldHandler.invalidate();
        }
    }


    public boolean hasChest() {
        return this.entityData.get(DATA_ID_CHEST);
    }

    public void setChest(boolean p_30505_) {
        this.entityData.set(DATA_ID_CHEST, p_30505_);
    }

    protected int getInventorySize() {
        return 27;
    }

    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() - 0.25D;
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (!this.level().isClientSide) {
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty()) {
                    this.spawnAtLocation(itemstack);
                }
            }
        }
        if (this.hasChest()) {
            if (!this.level().isClientSide) {
                this.spawnAtLocation(Items.BUNDLE);
            }

            this.setChest(false);
        }

    }

    public void addAdditionalSaveData(CompoundTag p_30496_) {
        super.addAdditionalSaveData(p_30496_);
        p_30496_.putBoolean("ChestedMonster", this.hasChest());
        if (this.hasChest()) {
            ListTag listtag = new ListTag();

            for(int i = 2; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag compoundtag = new CompoundTag();
                    compoundtag.putByte("Slot", (byte)i);
                    itemstack.save(compoundtag);
                    listtag.add(compoundtag);
                }
            }

            p_30496_.put("Items", listtag);
        }
        entityData.get(OWNER_UUID).ifPresent(uuid -> p_30496_.putUUID("OwnerUUID", uuid));
    }

    public void readAdditionalSaveData(CompoundTag p_30488_) {
        super.readAdditionalSaveData(p_30488_);
        this.setChest(p_30488_.getBoolean("ChestedMonster"));
        this.createInventory();
        if (this.hasChest()) {
            ListTag listtag = p_30488_.getList("Items", 10);

            for(int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag = listtag.getCompound(i);
                int j = compoundtag.getByte("Slot") & 255;
                if (j >= 2 && j < this.inventory.getContainerSize()) {
                    this.inventory.setItem(j, ItemStack.of(compoundtag));
                }
            }
        }
        if (p_30488_.hasUUID("OwnerUUID")) {
            entityData.set(OWNER_UUID, Optional.of(p_30488_.getUUID("OwnerUUID")));
        }

        this.updateContainerEquipment();
    }

    public SlotAccess getSlot(int p_149479_) {
        return p_149479_ == 499 ? new SlotAccess() {
            public ItemStack get() {
                return BundleMonsterEntity.this.hasChest() ? new ItemStack(Items.BUNDLE) : ItemStack.EMPTY;
            }

            public boolean set(ItemStack p_149485_) {
                if (p_149485_.isEmpty()) {
                    if (BundleMonsterEntity.this.hasChest()) {
                        BundleMonsterEntity.this.setChest(false);
                        BundleMonsterEntity.this.createInventory();
                    }

                    return true;
                } else if (p_149485_.is(Items.BUNDLE)) {
                    if (!BundleMonsterEntity.this.hasChest()) {
                        BundleMonsterEntity.this.setChest(true);
                        BundleMonsterEntity.this.createInventory();
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } : super.getSlot(p_149479_);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        boolean flag = !this.isBaby() && player.isSecondaryUseActive();
        if (!this.isVehicle() && !flag) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (this.hasChest() && !this.level().isClientSide) {
                this.openCustomInventoryScreen(player);
                return InteractionResult.CONSUME;
            }

            return super.mobInteract(player, hand);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    private void equipChest(Player p_250937_, ItemStack p_251558_) {
        this.setChest(true);
        this.playChestEquipsSound();
        if (!p_250937_.getAbilities().instabuild) {
            p_251558_.shrink(1);
        }

        this.createInventory();
    }

    protected void playChestEquipsSound() {
        this.playSound(SoundEvents.DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    public int getInventoryColumns() {
        return 5;
    }

    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new BundleMonsterMenu(id, playerInventory);
    }

    public void setOwner(Player player) {
        this.entityData.set(OWNER_UUID, Optional.of(player.getUUID()));
    }

    @Nullable
    public Player getOwner() {
        UUID ownerUUID = this.entityData.get(OWNER_UUID).orElse(null);
        return ownerUUID == null ? null : this.level().getPlayerByUUID(ownerUUID);
    }

    public class FollowOwnerGoal extends Goal {
        private final BundleMonsterEntity monster;
        private Player owner;
        private final double speed;
        private final float minDist;
        private final float maxDist;

        public FollowOwnerGoal(BundleMonsterEntity monster, double speed, float maxDist, float minDist) {
            this.monster = monster;
            this.speed = speed;
            this.maxDist = maxDist;
            this.minDist = minDist;
        }

        @Override
        public boolean canUse() {
            this.owner = monster.getOwner();
            return this.owner != null && this.monster.distanceToSqr(this.owner) > (minDist * minDist);
        }

        @Override
        public void tick() {
            if (owner != null && monster.distanceToSqr(owner) > (maxDist * maxDist)) {
                this.monster.getNavigation().moveTo(owner, speed);
            }
        }
    }
}
