package net.itshamza.cavity.item;

import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Cavity.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CAVITY_TAB = CREATIVE_MODE_TABS.register("cavity_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.CORPSE_HEAD.get()))
                    .title(Component.translatable("creativetab.cavity_tab"))
                    .displayItems((displayParameters, output) -> {
                        ModItems.ITEMS.getEntries().stream()
                                .map(RegistryObject::get)
                                .forEach(output::accept);

                        ModBlocks.BLOCKS.getEntries().stream()
                                .map(RegistryObject::get)
                                .forEach(output::accept);
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
