package net.itshamza.cavity.entity.custom.theoneandonly;

import net.itshamza.cavity.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.core.registries.Registries.DAMAGE_TYPE;

public class RidableMonsterEntity extends MonsterEntity implements PlayerRideable {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(RidableMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    private static final EntityDataAccessor<Boolean> CHARGING =
            SynchedEntityData.defineId(RidableMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    private int chargeCooldown = 0;
    private int chargeDuration = 0;
    public final AnimationState chargeAnimationState = new AnimationState();
    public int chargeAnimationTimeout = 0;


    public RidableMonsterEntity(EntityType<? extends MonsterEntity> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.setSecondsOnFire(-1);
        this.setRemainingFireTicks(0);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MEDIUM_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.MEDIUM_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEDIUM_DEATH.get();
    }

    protected void registerGoals() {
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
                .add(Attributes.MAX_HEALTH, 60D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 6f);
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return !this.hasCustomName();
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 1F;
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
        if (this.isCharging() && chargeAnimationTimeout <= 0) {
            chargeAnimationTimeout = 30;
            chargeAnimationState.start(this.tickCount);
        } else {
            --this.chargeAnimationTimeout;
        }

        if (!this.isCharging()) {
            chargeAnimationState.stop();
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

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
        if (chargeCooldown > 0) {
            chargeCooldown--;
        }

        if (chargeDuration > 0) {
            chargeDuration--;
            if (chargeDuration == 0) {
                stopCharging();
            }
        }

        if (this.isCharging()) {
            this.chargeAttack();
        }
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            this.controlMovement(player);
        }else if(!this.isVehicle()){
            this.setNoAi(false);
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
        this.entityData.define(CHARGING, false);
        this.entityData.define(ATTACKING, false);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && !player.isCrouching()) {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        if (passenger instanceof Player) {
            double xOffset = 0.0D;
            double yOffset = 1.0D;
            double zOffset = 0.0D;

            moveFunction.accept(passenger, this.getX() + xOffset, this.getY() + yOffset, this.getZ() + zOffset);
        }
    }

    private void chargeAttack() {
        List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(1.5, 0.5, 1.5));

        for (LivingEntity target : nearbyEntities) {
            if (target != this.getControllingPassenger() && target != this) {
                ResourceKey<DamageType> chargeDamage = DamageTypes.MOB_ATTACK;
                DamageSource damageSource = new DamageSource(this.level().registryAccess()
                        .registryOrThrow(DAMAGE_TYPE)
                        .getHolderOrThrow(chargeDamage));

                target.hurt(damageSource, 8.0F);

                Vec3 knockback = target.position().subtract(this.position()).normalize().scale(1.5);
                target.setDeltaMovement(knockback);
            }
        }
    }

    public void stopCharging() {
        this.entityData.set(CHARGING, false);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.setNoAi(false);
    }

    private void controlMovement(Player player) {
        this.setNoAi(true);

        float forward = player.zza;
        float strafe = player.xxa;

        if (this.isCharging()) {
            this.setSpeed(0.4F);
        } else {
            this.setSpeed(0.2F);
        }

        this.setYRot(player.getYRot());
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        this.setDeltaMovement(this.getDeltaMovement().multiply(0.9, 1.0, 0.9));

        if (forward != 0 || strafe != 0) {
            Vec3 movementVector = new Vec3(strafe, 0, forward).normalize().scale(this.getSpeed());
            this.setDeltaMovement(this.getDeltaMovement().add(movementVector));
        }
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }

    public void startCharging() {
        if (chargeCooldown <= 0 && !isCharging()) {
            setCharging(true);
            this.playSound(SoundEvents.RAVAGER_ROAR, 1.0F, 1.0F);
            chargeCooldown = 60;
            this.setDeltaMovement(this.getDeltaMovement().x, 0.1, this.getDeltaMovement().z);
        }
    }

    @Override
    public void travel(Vec3 movementInput) {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            this.setNoAi(true);

            float forward = player.zza;

            if (isCharging()) {
                float speed = 1.5F;
                Vec3 forwardDir = Vec3.directionFromRotation(0, this.getYRot());
                Vec3 movementVector = forwardDir.scale(speed);
                this.setDeltaMovement(movementVector.x, 0.1, movementVector.z);
            } else {
                float speed = 0.3F;
                Vec3 forwardDir = Vec3.directionFromRotation(0, this.getYRot());
                Vec3 movementVector = forwardDir.scale(speed);
                this.setDeltaMovement(movementVector.x, this.getDeltaMovement().y, movementVector.z);
            }

            super.travel(new Vec3(0, this.getDeltaMovement().y, 0));
        } else {
            super.travel(movementInput);
        }
    }
    @Override
    public void aiStep() {
        super.aiStep();

        if (isCharging()) {
            this.move(MoverType.SELF, this.getDeltaMovement());

            if (this.horizontalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().x, 0.2, this.getDeltaMovement().z);
            }
            chargeCooldown--;
            if (chargeCooldown <= 0) {
               setCharging(false);
            }
        }
    }
}
