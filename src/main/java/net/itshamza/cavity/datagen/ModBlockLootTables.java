package net.itshamza.cavity.datagen;

import net.itshamza.cavity.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.CORPSE_HEAD.get());
        this.dropSelf(ModBlocks.FLESH_BLOCK.get());
        this.dropSelf(ModBlocks.CHEST_CAVITY.get());
        this.dropSelf(ModBlocks.SEWN_CHEST_CAVITY.get());

        this.dropSelf(ModBlocks.BLAZE_CHEST_CAVITY.get());
        this.dropSelf(ModBlocks.BUNDLE_CHEST_CAVITY.get());
        this.dropSelf(ModBlocks.ENDER_EYE_CHEST_CAVITY.get());
        this.dropSelf(ModBlocks.COPPER_CHEST_CAVITY.get());
        this.dropSelf(ModBlocks.GUNPOWDER_CHEST_CAVITY.get());
        this.dropSelf(ModBlocks.ICE_CHEST_CAVITY.get());
        this.dropSelf(ModBlocks.RIDABLE_CHEST_CAVITY.get());
        this.dropSelf(ModBlocks.ROTTEN_FLESH_CHEST_CAVITY.get());

        this.dropSelf(ModBlocks.UNSEWN_BLAZE_CAVITY.get());
        this.dropSelf(ModBlocks.UNSEWN_BUNDLE_CAVITY.get());
        this.dropSelf(ModBlocks.UNSEWN_ENDER_EYE_CAVITY.get());
        this.dropSelf(ModBlocks.UNSEWN_COPPER_CAVITY.get());
        this.dropSelf(ModBlocks.UNSEWN_GUNPOWDER_CAVITY.get());
        this.dropSelf(ModBlocks.UNSEWN_ICE_CAVITY.get());
        this.dropSelf(ModBlocks.UNSEWN_RIDABLE_CAVITY.get());
        this.dropSelf(ModBlocks.UNSEWN_ROTTEN_FLESH_CAVITY.get());

        /*LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.KOHLRABI_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(KohlrabiCropBlock.AGE, 6));
        this.add(ModBlocks.KOHLRABI_CROP.get(), this.createCropDrops(ModBlocks.KOHLRABI_CROP.get(),
                ModItems.KOHLRABI.get(), ModItems.KOHLRABI_SEEDS.get(), lootitemcondition$builder1));

        this.add(ModBlocks.WALNUT_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.WALNUT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.add(ModBlocks.WALNUT_SIGN.get(), block ->
                createSingleItemTable(ModItems.WALNUT_SIGN.get()));
        this.add(ModBlocks.WALNUT_WALL_SIGN.get(), block ->
                createSingleItemTable(ModItems.WALNUT_SIGN.get()));
        this.add(ModBlocks.WALNUT_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.WALNUT_HANGING_SIGN.get()));
        this.add(ModBlocks.WALNUT_WALL_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.WALNUT_HANGING_SIGN.get()));*/
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
