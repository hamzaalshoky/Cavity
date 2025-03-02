package net.itshamza.cavity.block.custom;

import net.itshamza.cavity.block.ModBlocks;
import net.itshamza.cavity.block.entity.CavityBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;

public class CavityBlock extends BaseEntityBlock {

    public CavityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CavityBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof CavityBlockEntity sewingBlock)) {
            return InteractionResult.PASS;
        }

        if (!world.isClientSide()) {
            BlockEntity entity = world.getBlockEntity(pos);
            if(entity instanceof CavityBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)player), (CavityBlockEntity)entity, pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        if (!world.isClientSide) {
            if (itemStack.getItem() instanceof ShearsItem) {
                ItemStack storedItem = sewingBlock.getStoredItem();
                Block newBlock = getTransformedBlock(storedItem);

                world.setBlockAndUpdate(pos, newBlock.defaultBlockState());
                sewingBlock.clearItem();

                itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                return InteractionResult.SUCCESS;
            } else {
                player.openMenu(sewingBlock);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
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

}