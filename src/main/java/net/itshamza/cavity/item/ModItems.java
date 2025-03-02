package net.itshamza.cavity.item;

import net.itshamza.cavity.Cavity;
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

    /*public static final RegistryObject<Item> OAK_BARK = ITEMS.register("oak_bark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPRUCE_BARK = ITEMS.register("spruce_bark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BIRCH_BARK = ITEMS.register("birch_bark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> JUNGLE_BARK = ITEMS.register("jungle_bark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ACACIA_BARK = ITEMS.register("acacia_bark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DARK_OAK_BARK = ITEMS.register("dark_oak_bark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MANGROVE_BARK = ITEMS.register("mangrove_bark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHERRY_BARK = ITEMS.register("cherry_bark",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARK_BOOTS = ITEMS.register("bark_boots",
            () -> new BarkArmorItem(ModArmorMaterials.BARK, ArmorItem.Type.BOOTS, new Item.Properties()));*/
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
