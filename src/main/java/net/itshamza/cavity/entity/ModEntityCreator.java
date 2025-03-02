package net.itshamza.cavity.entity;

import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.entity.client.MonsterModel;
import net.itshamza.cavity.entity.client.MonsterRenderer;
import net.itshamza.cavity.entity.custom.theoneandonly.MonsterEntity;
import net.itshamza.cavity.entity.layer.ModModelLayers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Cavity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityCreator {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Cavity.MOD_ID);

    //REGISTRIES

    public static final RegistryObject<EntityType<MonsterEntity>> MONSTER =
            ENTITY_TYPES.register("monster", () -> EntityType.Builder.of(MonsterEntity::new, MobCategory.MONSTER)
                    .sized(1f, 3f).build("monster"));


    //RENDERERS

    @SubscribeEvent
    public static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityCreator.MONSTER.get(), MonsterRenderer::new);
    }

    //LAYERS

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.MONSTER_LAYER, MonsterModel::createBodyLayer);
    }

    //ATTRIBUTES

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityCreator.MONSTER.get(), MonsterEntity.createAttributes().build());
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
