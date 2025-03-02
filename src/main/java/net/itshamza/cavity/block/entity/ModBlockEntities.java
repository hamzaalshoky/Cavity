package net.itshamza.cavity.block.entity;

import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Cavity.MOD_ID);

    public static final RegistryObject<BlockEntityType<CavityBlockEntity>> CHEST_CAVITY_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("chest_cavity_block_entity", () ->
                    BlockEntityType.Builder.of(CavityBlockEntity::new,
                            ModBlocks.CHEST_CAVITY.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}