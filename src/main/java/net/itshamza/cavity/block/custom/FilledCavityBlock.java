package net.itshamza.cavity.block.custom;

import net.itshamza.cavity.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class FilledCavityBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    ItemStack itemToUse;

    public FilledCavityBlock(Properties properties, ItemStack storedItem) {
        super(properties);
        itemToUse = storedItem;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            if (itemStack.getItem() instanceof ShearsItem) {
                Block newBlock = getTransformedBlock(itemToUse);

                world.setBlockAndUpdate(pos, newBlock.defaultBlockState());

                itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.sidedSuccess(world.isClientSide());
    }

    private Block getTransformedBlock(ItemStack storedItem) {
        if (storedItem.is(Items.BLAZE_POWDER)) return ModBlocks.BLAZE_CHEST_CAVITY.get();
        if (storedItem.is(Items.GUNPOWDER)) return ModBlocks.GUNPOWDER_CHEST_CAVITY.get();
        if (storedItem.is(Items.SPIDER_EYE)) return ModBlocks.SPIDER_EYE_CHEST_CAVITY.get();
        if (storedItem.is(Items.ENDER_EYE)) return ModBlocks.ENDER_EYE_CHEST_CAVITY.get();
        if (storedItem.is(Items.BUNDLE)) return ModBlocks.BUNDLE_CHEST_CAVITY.get();
        if (storedItem.is(Items.COPPER_INGOT)) return ModBlocks.COPPER_CHEST_CAVITY.get();
        if (storedItem.is(Items.ICE)) return ModBlocks.ICE_CHEST_CAVITY.get();
        if (storedItem.is(Items.ROTTEN_FLESH)) return ModBlocks.ROTTEN_FLESH_CHEST_CAVITY.get();
        return ModBlocks.SEWN_CHEST_CAVITY.get();
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_51377_) {
        return this.defaultBlockState().setValue(FACING, p_51377_.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51385_) {
        p_51385_.add(FACING);
    }
}