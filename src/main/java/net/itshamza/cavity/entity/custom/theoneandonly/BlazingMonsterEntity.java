package net.itshamza.cavity.entity.custom.theoneandonly;

import net.itshamza.cavity.entity.ModEntityCreator;
import net.itshamza.cavity.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BlazingMonsterEntity extends MonsterEntity {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(BlazingMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    private int inflationCooldown = 0;
    private static final int INFLATION_COOLDOWN_TIME = 200;
    private static final EntityDataAccessor<Boolean> INFLATED =
            SynchedEntityData.defineId(BlazingMonsterEntity.class, EntityDataSerializers.BOOLEAN);

    private int inflationTicks = 0;
    private static final int INFLATION_DURATION = 200;
    private int spawnTicks = 50;


    public BlazingMonsterEntity(EntityType<? extends MonsterEntity> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.setSecondsOnFire(-1);
        this.setRemainingFireTicks(0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 80D)
                .add(Attributes.MOVEMENT_SPEED, 0D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 6f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1f);
    }

    // ANIMATIONS //

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 1F;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.LARGE_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.LARGE_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.LARGE_DEATH.get();
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

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
        if (!this.level().isClientSide) {
            LivingEntity target = this.getTarget();

            if (target != null) {
                double distance = this.distanceToSqr(target);

                if (distance > 2.0D && !this.isInflated() && inflationCooldown <= 0) {
                    this.setInflated(true);
                    this.inflationTicks = INFLATION_DURATION;
                    this.inflationCooldown = INFLATION_COOLDOWN_TIME;
                }

                if (this.isInflated()) {
                    if (--this.inflationTicks <= 0) {
                        this.setInflated(false);
                    }
                }

                if (inflationCooldown > 0) {
                    inflationCooldown--;
                }
            }
        }
        if (spawnTicks > 0) {
            spawnTicks--;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (spawnTicks > 0) {
            return true;
        }
        if (source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.ON_FIRE) ||
                source.is(DamageTypes.LAVA) || source.is(DamageTypes.HOT_FLOOR) ||
                source.is(DamageTypes.FIREBALL)) {
            return false;
        }
        if (this.isInflated()) {
            this.spawnFlamelings();
            this.setInflated(false);
            return false;
        }
        return super.hurt(source, amount);
    }

    private void spawnFlamelings() {
        for (int i = 0; i < 3; i++) {
            FlamelingEntity flameling = new FlamelingEntity(ModEntityCreator.FLAMELING.get(), this.level());
            flameling.setPos(this.getX() + random.nextFloat() - 0.5, this.getY(), this.getZ() + random.nextFloat() - 0.5);
            this.level().addFreshEntity(flameling);
        }
    }

    public boolean isInflated() {
        return this.entityData.get(INFLATED);
    }

    public void setInflated(boolean inflated) {
        this.entityData.set(INFLATED, inflated);
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
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
        this.entityData.define(INFLATED, false);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }
    @Override
    public boolean doHurtTarget(Entity target) {
        boolean success = super.doHurtTarget(target);

        if (success) {
            target.setSecondsOnFire(5);
        }

        return success;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance() && spawnTicks <= 0) {
            this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
        }
        super.travel(Vec3.ZERO);
    }

    @Override
    public int getTeamColor() {
        if (this.isInflated()) {
            return 0xD8D8D8;
        }
        return super.getTeamColor();
    }

}
