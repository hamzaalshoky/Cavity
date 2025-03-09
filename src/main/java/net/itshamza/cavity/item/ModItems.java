package net.itshamza.cavity.item;

import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.entity.ModEntityCreator;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Cavity.MOD_ID);

    public static final RegistryObject<Item> ABOMINATION_SPAWN_EGG = ITEMS.register("abomination_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.MONSTER, 5925960, 3155237,
                    new Item.Properties()));
    public static final RegistryObject<Item> BLAZING_ABOMINATION_SPAWN_EGG = ITEMS.register("blazing_abomination_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.BLAZING_MONSTER, 8618307, 10707506,
                    new Item.Properties()));
    public static final RegistryObject<Item> STORAGE_ABOMINATION_SPAWN_EGG = ITEMS.register("storage_abomination_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.BUNDLE_MONSTER, 4211766, 5129516,
                    new Item.Properties()));
    public static final RegistryObject<Item> CYCLOPS_ABOMINATION_SPAWN_EGG = ITEMS.register("cyclops_abomination_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.CYCLOPS_MONSTER, 4543039, 2170661,
                    new Item.Properties()));
    public static final RegistryObject<Item> ELECTRIC_ABOMINATION_SPAWN_EGG = ITEMS.register("electric_abomination_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.ELECTRIC_MONSTER, 5925960, 14916715,
                    new Item.Properties()));
    public static final RegistryObject<Item> EXPLODING_ABOMINATION_SPAWN_EGG = ITEMS.register("exploding_abomination_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.EXPLODING_MONSTER, 5925960, 8671559,
                    new Item.Properties()));
    public static final RegistryObject<Item> HUNCHBACK_ABOMINATION_SPAWN_EGG = ITEMS.register("hunchback_abomination_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.RIDABLE_MONSTER, 5925960, 3494509,
                    new Item.Properties()));
    public static final RegistryObject<Item> ROTTEN_ABOMINATION_SPAWN_EGG = ITEMS.register("rotten_abomination_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityCreator.ROTTEN_MONSTER, 5398083, 10058841,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
