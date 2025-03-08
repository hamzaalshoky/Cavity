package net.itshamza.cavity.block;

import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.block.custom.CavityBlock;
import net.itshamza.cavity.block.custom.RotatingBlock;
import net.itshamza.cavity.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Cavity.MOD_ID);

    public static final RegistryObject<Block> FLESH_BLOCK = registerBlock("flesh_block",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> CORPSE_HEAD = registerBlock("corpse_head",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> CHEST_CAVITY = registerBlock("chest_cavity",
            () -> new CavityBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD), Items.ITEM_FRAME.getDefaultInstance()));
    public static final RegistryObject<Block> SEWN_CHEST_CAVITY = registerBlock("sewn_chest_cavity",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> UNSEWN_BLAZE_CAVITY = registerBlock("unsewn_blaze_cavity",
            () -> new CavityBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD), Items.BLAZE_POWDER.getDefaultInstance()));
    public static final RegistryObject<Block> BLAZE_CHEST_CAVITY = registerBlock("blaze_chest_cavity",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> UNSEWN_GUNPOWDER_CAVITY = registerBlock("unsewn_gunpowder_cavity",
            () -> new CavityBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD), Items.GUNPOWDER.getDefaultInstance()));
    public static final RegistryObject<Block> GUNPOWDER_CHEST_CAVITY = registerBlock("gunpowder_chest_cavity",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> UNSEWN_ENDER_EYE_CAVITY = registerBlock("unsewn_ender_eye_cavity",
            () -> new CavityBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD), Items.ENDER_EYE.getDefaultInstance()));
    public static final RegistryObject<Block> ENDER_EYE_CHEST_CAVITY = registerBlock("ender_eye_chest_cavity",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> UNSEWN_BUNDLE_CAVITY = registerBlock("unsewn_bundle_cavity",
            () -> new CavityBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD), Items.BUNDLE.getDefaultInstance()));
    public static final RegistryObject<Block> BUNDLE_CHEST_CAVITY = registerBlock("bundle_chest_cavity",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> UNSEWN_COPPER_CAVITY = registerBlock("unsewn_copper_cavity",
            () -> new CavityBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD), Items.COPPER_INGOT.getDefaultInstance()));
    public static final RegistryObject<Block> COPPER_CHEST_CAVITY = registerBlock("copper_chest_cavity",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> UNSEWN_ICE_CAVITY = registerBlock("unsewn_ice_cavity",
            () -> new CavityBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD), Items.ICE.getDefaultInstance()));
    public static final RegistryObject<Block> ICE_CHEST_CAVITY = registerBlock("ice_chest_cavity",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> UNSEWN_ROTTEN_FLESH_CAVITY = registerBlock("unsewn_rotten_flesh_cavity",
            () -> new CavityBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD), Items.ROTTEN_FLESH.getDefaultInstance()));
    public static final RegistryObject<Block> ROTTEN_FLESH_CHEST_CAVITY = registerBlock("rotten_flesh_chest_cavity",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> RIDABLE_CHEST_CAVITY = registerBlock("ridable_chest_cavity",
            () -> new RotatingBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD)));
    public static final RegistryObject<Block> UNSEWN_RIDABLE_CAVITY = registerBlock("unsewn_ridable_cavity",
            () -> new CavityBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).mapColor(MapColor.COLOR_GREEN).strength(1F).sound(SoundType.MUD), Items.SADDLE.getDefaultInstance()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
