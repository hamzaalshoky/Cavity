package net.itshamza.cavity;

import com.mojang.logging.LogUtils;
import net.itshamza.cavity.block.ModBlocks;
import net.itshamza.cavity.entity.ModEntityCreator;
import net.itshamza.cavity.event.PacketHandler;
import net.itshamza.cavity.item.ModItems;
import net.itshamza.cavity.screen.ModMenus;
import net.itshamza.cavity.screen.ModScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Cavity.MOD_ID)
public class Cavity
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "cavity";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace


    public Cavity()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        ModEntityCreator.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModMenus.register(modEventBus);
        PacketHandler.register();

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.BLAZE_CHEST_CAVITY);
            event.accept(ModBlocks.BUNDLE_CHEST_CAVITY);
            event.accept(ModBlocks.CHEST_CAVITY);
            event.accept(ModBlocks.COPPER_CHEST_CAVITY);
            event.accept(ModBlocks.GUNPOWDER_CHEST_CAVITY);
            event.accept(ModBlocks.CORPSE_HEAD);
            event.accept(ModBlocks.ENDER_EYE_CHEST_CAVITY);
            event.accept(ModBlocks.FLESH_BLOCK);
            event.accept(ModBlocks.ICE_CHEST_CAVITY);
            event.accept(ModBlocks.RIDABLE_CHEST_CAVITY);
            event.accept(ModBlocks.ROTTEN_FLESH_CHEST_CAVITY);
            event.accept(ModBlocks.SEWN_CHEST_CAVITY);
            event.accept(ModBlocks.UNSEWN_BLAZE_CAVITY);
            event.accept(ModBlocks.UNSEWN_BUNDLE_CAVITY);
            event.accept(ModBlocks.UNSEWN_COPPER_CAVITY);
            event.accept(ModBlocks.UNSEWN_ENDER_EYE_CAVITY);
            event.accept(ModBlocks.UNSEWN_GUNPOWDER_CAVITY);
            event.accept(ModBlocks.UNSEWN_ICE_CAVITY);
            event.accept(ModBlocks.UNSEWN_RIDABLE_CAVITY);
            event.accept(ModBlocks.UNSEWN_ROTTEN_FLESH_CAVITY);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {

            });
        }
    }
}
