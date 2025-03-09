package net.itshamza.cavity.entity.custom.theoneandonly;

import net.itshamza.cavity.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
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
import org.jetbrains.annotations.Nullable;

public class ElectricMonsterEntity extends MonsterEntity {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(ElectricMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;


    public ElectricMonsterEntity(EntityType<? extends MonsterEntity> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.setSecondsOnFire(-1);
        this.setRemainingFireTicks(0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new HurtByTargetGoal(this));
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(1, new ElectricMeleeAttackGoal(this, 1.2F, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 3f);
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

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.LIGHTNING_BOLT)) {
            return false;
        }
        return super.hurt(source, amount);
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
    }


    public class ElectricMeleeAttackGoal extends MeleeAttackGoal {
        private final ElectricMonsterEntity entity;

        public ElectricMeleeAttackGoal(ElectricMonsterEntity entity, double speed, boolean longMemory) {
            super(entity, speed, longMemory);
            this.entity = entity;
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target, double distanceSquared) {
            double attackReach = this.getAttackReachSqr(target);
            if (distanceSquared <= attackReach && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                this.mob.swing(InteractionHand.MAIN_HAND);

                if (this.entity.level().random.nextFloat() < 0.4f) {
                    this.entity.summonLightningAttack();
                } else {
                    this.mob.doHurtTarget(target);
                }
            }
        }
    }

    public void summonLightningAttack() {
        if (!this.level().isClientSide()) {
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(this.level());
            if (lightning != null) {
                lightning.moveTo(this.getX(), this.getY(), this.getZ() + 1);
                this.level().addFreshEntity(lightning);
            }
        }
    }
}
