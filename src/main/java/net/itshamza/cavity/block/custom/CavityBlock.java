package net.itshamza.cavity.block.custom;

import net.itshamza.cavity.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CavityBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    ItemStack itemToUse;

    public CavityBlock(Properties properties, ItemStack storedItem) {
        super(properties);
        itemToUse = storedItem;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            Block transformedBlock = getUnsewnVersion(itemStack.getItem());

            if (transformedBlock != null) {
                BlockState newState = transformedBlock.defaultBlockState()
                        .setValue(FACING, state.getValue(FACING));
                world.setBlockAndUpdate(pos, newState);

                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }

                return InteractionResult.SUCCESS;
            }
        }
        if (!world.isClientSide) {
            if (itemStack.getItem() instanceof ShearsItem) {
                Block newBlock = getTransformedBlock(itemToUse);

                BlockState newState = newBlock.defaultBlockState()
                        .setValue(FACING, state.getValue(FACING));

                world.setBlockAndUpdate(pos, newState);

                itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.sidedSuccess(world.isClientSide());
    }

    private Block getUnsewnVersion(Item item) {
        if (item == Items.BLAZE_POWDER) return ModBlocks.UNSEWN_BLAZE_CAVITY.get();
        if (item == Items.GUNPOWDER) return ModBlocks.UNSEWN_GUNPOWDER_CAVITY.get();
        if (item == Items.ENDER_EYE) return ModBlocks.UNSEWN_ENDER_EYE_CAVITY.get();
        if (item == Items.BUNDLE) return ModBlocks.UNSEWN_BUNDLE_CAVITY.get();
        if (item == Items.COPPER_INGOT) return ModBlocks.UNSEWN_COPPER_CAVITY.get();
        if (item == Items.ICE) return ModBlocks.UNSEWN_ICE_CAVITY.get();
        if (item == Items.ROTTEN_FLESH) return ModBlocks.UNSEWN_ROTTEN_FLESH_CAVITY.get();
        if (item == Items.SADDLE) return ModBlocks.UNSEWN_RIDABLE_CAVITY.get();

        return null;
    }

    private Block getTransformedBlock(ItemStack storedItem) {
        if (storedItem.is(Items.BLAZE_POWDER)) return ModBlocks.BLAZE_CHEST_CAVITY.get();
        if (storedItem.is(Items.GUNPOWDER)) return ModBlocks.GUNPOWDER_CHEST_CAVITY.get();
        if (storedItem.is(Items.ENDER_EYE)) return ModBlocks.ENDER_EYE_CHEST_CAVITY.get();
        if (storedItem.is(Items.BUNDLE)) return ModBlocks.BUNDLE_CHEST_CAVITY.get();
        if (storedItem.is(Items.COPPER_INGOT)) return ModBlocks.COPPER_CHEST_CAVITY.get();
        if (storedItem.is(Items.ICE)) return ModBlocks.ICE_CHEST_CAVITY.get();
        if (storedItem.is(Items.ROTTEN_FLESH)) return ModBlocks.ROTTEN_FLESH_CHEST_CAVITY.get();
        if (storedItem.is(Items.SADDLE)) return ModBlocks.RIDABLE_CHEST_CAVITY.get();
        return ModBlocks.SEWN_CHEST_CAVITY.get();
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_51377_) {
        return this.defaultBlockState().setValue(FACING, p_51377_.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51385_) {
        p_51385_.add(FACING);
    }
}