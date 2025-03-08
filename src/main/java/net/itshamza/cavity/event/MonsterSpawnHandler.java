package net.itshamza.cavity.event;

import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.block.ModBlocks;
import net.itshamza.cavity.entity.ModEntityCreator;
import net.itshamza.cavity.entity.custom.theoneandonly.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cavity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MonsterSpawnHandler {

    public static boolean isValidMonsterStructure(Level world, BlockPos pos, Block blockUnder) {
        BlockPos down1 = pos.below();
        BlockPos down2 = pos.below(2);
        BlockPos west = down1.west();
        BlockPos east = down1.east();
        BlockPos north = down1.north();
        BlockPos south = down1.south();

        boolean vertical = world.getBlockState(down1).is(blockUnder)
                && world.getBlockState(down2).is(ModBlocks.FLESH_BLOCK.get());

        boolean horizontal1 = world.getBlockState(west).is(ModBlocks.FLESH_BLOCK.get())
                && world.getBlockState(east).is(ModBlocks.FLESH_BLOCK.get());

        boolean horizontal2 = world.getBlockState(north).is(ModBlocks.FLESH_BLOCK.get())
                && world.getBlockState(south).is(ModBlocks.FLESH_BLOCK.get());

        boolean result = vertical && (horizontal1 || horizontal2);

        System.out.println("[DEBUG] Structure check at " + pos + ": " + result);
        System.out.println("[DEBUG] Block under: " + blockUnder);

        return result;
    }

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Level world = event.getEntity().level();
        BlockPos pos = event.getPos();
        Block block = event.getPlacedBlock().getBlock();

        System.out.println("[DEBUG] Block placed at " + pos + ": " + block);

        if (event.getPlacedBlock().is(ModBlocks.CORPSE_HEAD.get())) {
            System.out.println("[DEBUG] Corpse Head placed at " + pos);

            Block blockUnder = world.getBlockState(pos.below()).getBlock();

            if (isValidMonsterStructure(world, pos, blockUnder)) {
                System.out.println("[DEBUG] Valid structure detected!");

                ArmorStand marker = new ArmorStand(EntityType.ARMOR_STAND, world);
                marker.setInvisible(true);
                marker.setInvulnerable(true);
                marker.setNoGravity(true);
                marker.setCustomName(Component.literal("GraniteMarker"));
                marker.setCustomNameVisible(false);
                marker.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);

                world.addFreshEntity(marker);
                System.out.println("[DEBUG] Marker placed at " + marker.blockPosition());
            }
        }
    }

    @SubscribeEvent
    public static void onEntityStruckByLightning(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        Level world = entity.level();

        if (entity instanceof ArmorStand marker && marker.hasCustomName() && "GraniteMarker".equals(marker.getCustomName().getString())) {
            BlockPos pos = marker.blockPosition().below();

            System.out.println("[DEBUG] Lightning struck marker at " + marker.blockPosition());

            Block blockUnder = world.getBlockState(pos.below()).getBlock();
            if (isValidMonsterStructure(world, pos, blockUnder)) {
                System.out.println("[DEBUG] Monster structure confirmed!");

                spawnMonster(world, pos, blockUnder);
            }

            marker.remove(Entity.RemovalReason.DISCARDED);
            System.out.println("[DEBUG] Marker removed.");
        }
    }


    private static void spawnMonster(Level world, BlockPos pos, Block blockUnder) {
        if (!world.isClientSide) {
            System.out.println("[DEBUG] Spawning monster at " + pos);

            MonsterEntity monster = null;
            if (blockUnder == (ModBlocks.BLAZE_CHEST_CAVITY.get())) {
                monster = new BlazingMonsterEntity(ModEntityCreator.BLAZING_MONSTER.get(), world);
            } else if (blockUnder == (ModBlocks.GUNPOWDER_CHEST_CAVITY.get())) {
                monster = new ExplodingMonsterEntity(ModEntityCreator.EXPLODING_MONSTER.get(), world);
            }else if (blockUnder == (ModBlocks.ENDER_EYE_CHEST_CAVITY.get())) {
                monster = new CyclopsMonsterEntity(ModEntityCreator.CYCLOPS_MONSTER.get(), world);
            } else if (blockUnder == (ModBlocks.BUNDLE_CHEST_CAVITY.get())) {
                monster = new BundleMonsterEntity(ModEntityCreator.BUNDLE_MONSTER.get(), world);

            } else if (blockUnder == (ModBlocks.COPPER_CHEST_CAVITY.get())) {
                monster = new ElectricMonsterEntity(ModEntityCreator.ELECTRIC_MONSTER.get(), world);
            } else if (blockUnder == (ModBlocks.ROTTEN_FLESH_CHEST_CAVITY.get())) {
                monster = new RottenMonsterEntity(ModEntityCreator.ROTTEN_MONSTER.get(), world);
            } else if (blockUnder == (ModBlocks.RIDABLE_CHEST_CAVITY.get())) {
                monster = new RidableMonsterEntity(ModEntityCreator.RIDABLE_MONSTER.get(), world);
            } else if (blockUnder == (ModBlocks.SEWN_CHEST_CAVITY.get())) {
                monster = new RidableMonsterEntity(ModEntityCreator.MONSTER.get(), world);
            }else {
                monster = null;
            }

            if (monster != null) {
                destroyStructure(world, pos);
                monster.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                world.addFreshEntity(monster);
                System.out.println("[DEBUG] " + monster.getClass().getSimpleName() + " spawned at " + pos);
            }
            for(ServerPlayer serverplayer : world.getEntitiesOfClass(ServerPlayer.class, monster.getBoundingBox().inflate(10.0D))) {
                if (monster instanceof BundleMonsterEntity bundleMonster) {
                    bundleMonster.setOwner(serverplayer);
                }
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, monster);
            }
        }
    }

    private static void destroyStructure(Level world, BlockPos pos) {
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below(2), Blocks.AIR.defaultBlockState(), 3);

        world.setBlock(pos.below().west(), Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below().east(), Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below().north(), Blocks.AIR.defaultBlockState(), 3);
        world.setBlock(pos.below().south(), Blocks.AIR.defaultBlockState(), 3);

        System.out.println("[DEBUG] Structure destroyed at " + pos);
    }
}