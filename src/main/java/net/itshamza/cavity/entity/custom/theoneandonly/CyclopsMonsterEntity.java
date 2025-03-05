package net.itshamza.cavity.entity.custom.theoneandonly;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class CyclopsMonsterEntity extends MonsterEntity implements NeutralMob{
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(CyclopsMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(EnderMan.class, EntityDataSerializers.BOOLEAN);

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;


    public CyclopsMonsterEntity(EntityType<? extends MonsterEntity> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this,1.0D, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new EndermanLookForPlayerGoal(this, this::isAngryAt));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.2F, true));
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 8f);
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

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
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
        this.entityData.define(DATA_STARED_AT, false);
    }
    public void addAdditionalSaveData(CompoundTag p_32520_) {
        super.addAdditionalSaveData(p_32520_);
        this.addPersistentAngerSaveData(p_32520_);
    }

    public void readAdditionalSaveData(CompoundTag p_32511_) {
        super.readAdditionalSaveData(p_32511_);
        BlockState blockstate = null;
        this.readPersistentAngerSaveData(this.level(), p_32511_);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        if (super.doHurtTarget(target)) {
            this.playSound(SoundEvents.PHANTOM_BITE, 10.0f, 0.95f + this.random.nextFloat() * 0.1f);
            if (target instanceof LivingEntity entity && random.nextFloat() < 0.5) {
                teleportTarget(level(), entity, 24);
            }
            return true;
        } else {
            return false;
        }
    }


    public static void teleportTarget(Level level, LivingEntity target, int range) {
        if (level.isClientSide()) return;
        double x = target.getX();
        double y = target.getY();
        double z = target.getZ();

        for (int i = 0; i < range; i++) {
            double g = target.getX() + (level.random.nextDouble() - 0.5) * range;
            double h = Mth.clamp(target.getY() + (level.random.nextInt(range) - 8), level.getMinBuildHeight(), level.getMinBuildHeight() + ((ServerLevel) level).getLogicalHeight() - 1);
            double j = target.getZ() + (level.random.nextDouble() - 0.5) * range;
            if (target.isPassenger()) {
                target.stopRiding();
            }

            Vec3 position = target.position();
            if (target.randomTeleport(g, h, j, true)) {
                level.gameEvent(GameEvent.TELEPORT, position, GameEvent.Context.of(target));
                SoundEvent soundEvent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                level.playSound(null, x, y, z, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
                target.playSound(soundEvent, 1.0F, 1.0F);
                break;
            }
        }
    }

    boolean isLookingAtMe(Player p_32535_) {
        Vec3 vec3 = p_32535_.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(this.getX() - p_32535_.getX(), this.getEyeY() - p_32535_.getEyeY(), this.getZ() - p_32535_.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > 1.0D - 0.025D / d0 ? p_32535_.hasLineOfSight(this) : false;
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public void setRemainingPersistentAngerTime(int p_32515_) {
        this.remainingPersistentAngerTime = p_32515_;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setPersistentAngerTarget(@Nullable UUID p_32509_) {
        this.persistentAngerTarget = p_32509_;
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public boolean hasBeenStaredAt() {
        return this.entityData.get(DATA_STARED_AT);
    }
    public void setBeingStaredAt() {
        this.entityData.set(DATA_STARED_AT, true);
    }

    protected float getStandingEyeHeight(Pose p_32517_, EntityDimensions p_32518_) {
        return 2.8F;
    }

    static class EndermanLookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
        private final CyclopsMonsterEntity enderman;
        @Nullable
        private Player pendingTarget;
        private int aggroTime;
        private int teleportTime;
        private final TargetingConditions startAggroTargetConditions;
        private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();
        private final Predicate<LivingEntity> isAngerInducing;

        public EndermanLookForPlayerGoal(CyclopsMonsterEntity p_32573_, @Nullable Predicate<LivingEntity> p_32574_) {
            super(p_32573_, Player.class, 10, false, false, p_32574_);
            this.enderman = p_32573_;
            this.isAngerInducing = (p_269940_) -> {
                return (p_32573_.isLookingAtMe((Player)p_269940_) || p_32573_.isAngryAt(p_269940_)) && !p_32573_.hasIndirectPassenger(p_269940_);
            };
            this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.isAngerInducing);
        }

        public boolean canUse() {
            this.pendingTarget = this.enderman.level().getNearestPlayer(this.startAggroTargetConditions, this.enderman);
            return this.pendingTarget != null;
        }

        public void start() {
            this.aggroTime = this.adjustedTickDelay(5);
            this.teleportTime = 0;
            this.enderman.setBeingStaredAt();
        }

        public void stop() {
            this.pendingTarget = null;
            super.stop();
        }

        public boolean canContinueToUse() {
            if (this.pendingTarget != null) {
                if (!this.isAngerInducing.test(this.pendingTarget)) {
                    return false;
                } else {
                    this.enderman.lookAt(this.pendingTarget, 10.0F, 10.0F);
                    return true;
                }
            } else {
                if (this.target != null) {
                    if (this.enderman.hasIndirectPassenger(this.target)) {
                        return false;
                    }

                    if (this.continueAggroTargetConditions.test(this.enderman, this.target)) {
                        return true;
                    }
                }

                return super.canContinueToUse();
            }
        }

        public void tick() {
            if (this.enderman.getTarget() == null) {
                super.setTarget((LivingEntity)null);
            }

            if (this.pendingTarget != null) {
                if (--this.aggroTime <= 0) {
                    this.target = this.pendingTarget;
                    this.pendingTarget = null;
                    super.start();
                }
            }

        }
    }
}
