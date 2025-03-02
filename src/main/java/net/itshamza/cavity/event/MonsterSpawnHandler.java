package net.itshamza.cavity.event;

import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.entity.ModEntityCreator;
import net.itshamza.cavity.entity.client.MonsterModel;
import net.itshamza.cavity.entity.custom.theoneandonly.MonsterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Cavity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MonsterSpawnHandler {

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Level world = event.getEntity().level();
        BlockPos pos = event.getPos();
        Block block = event.getPlacedBlock().getBlock();

        if (block == Blocks.GRANITE) {
            // Summon an invisible marker entity
            ArmorStand marker = new ArmorStand(EntityType.ARMOR_STAND, world);
            marker.setInvisible(true);
            marker.setInvulnerable(true);
            marker.setNoGravity(true);
            marker.setCustomName(Component.literal("GraniteMarker"));
            marker.setCustomNameVisible(false);
            marker.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            world.addFreshEntity(marker);
        }
    }

    @SubscribeEvent
    public static void onEntityStruckByLightning(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        Level world = entity.level();

        if (entity instanceof ArmorStand marker && marker.hasCustomName() && "GraniteMarker".equals(marker.getCustomName().getString())) {
            BlockPos pos = marker.blockPosition().below(); // Granite block position

            if (world.getBlockState(pos).is(Blocks.GRANITE) && isValidWolverineStructure(world, pos)) {
                destroyStructure(world, pos);
                spawnWolverine(world, pos);
            }

            // Remove marker
            marker.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    private static boolean isValidWolverineStructure(Level world, BlockPos pos) {
        BlockPos down1 = pos.below();
        BlockPos down2 = pos.below();

        boolean vertical = world.getBlockState(down1).is(Blocks.IRON_BLOCK) && world.getBlockState(down2).is(Blocks.IRON_BLOCK);

        boolean horizontal1 = world.getBlockState(down1.west()).is(Blocks.IRON_BLOCK) && world.getBlockState(down1.east()).is(Blocks.IRON_BLOCK);
        boolean horizontal2 = world.getBlockState(down1.north()).is(Blocks.IRON_BLOCK) && world.getBlockState(down1.south()).is(Blocks.IRON_BLOCK);

        return vertical && (horizontal1 || horizontal2);
    }

    private static void destroyStructure(Level world, BlockPos pos) {
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below(2), Blocks.AIR.defaultBlockState(), 3);

        world.setBlock(pos.below().west(), Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below().east(), Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below().north(), Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below().south(), Blocks.AIR.defaultBlockState(), 3);
    }

    private static void spawnWolverine(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            MonsterEntity wolverine = new MonsterEntity(ModEntityCreator.MONSTER.get(), world);
            wolverine.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            world.addFreshEntity(wolverine);
        }
    }
}