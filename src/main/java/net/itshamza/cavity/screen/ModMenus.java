package net.itshamza.cavity.screen;

import net.itshamza.cavity.Cavity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Cavity.MOD_ID);

    public static final RegistryObject<MenuType<BundleMonsterMenu>> BUNDLE_MONSTER_MENU =
            MENUS.register("bundle_monster_menu", () ->
                    new MenuType<>((id, inv) -> new BundleMonsterMenu(id, inv), FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
