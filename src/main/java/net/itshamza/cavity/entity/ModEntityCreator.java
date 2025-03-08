package net.itshamza.cavity.entity;

import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.entity.client.*;
import net.itshamza.cavity.entity.custom.theoneandonly.*;
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
                    .sized(0.6f, 2.5f).build("monster"));
    public static final RegistryObject<EntityType<BlazingMonsterEntity>> BLAZING_MONSTER =
            ENTITY_TYPES.register("blazing_monster", () -> EntityType.Builder.of(BlazingMonsterEntity::new, MobCategory.MONSTER)
                    .sized(1.4f, 2.2f).build("blazing_monster"));
    public static final RegistryObject<EntityType<BundleMonsterEntity>> BUNDLE_MONSTER =
            ENTITY_TYPES.register("bundle_monster", () -> EntityType.Builder.of(BundleMonsterEntity::new, MobCategory.MONSTER)
                    .sized(0.8f, 1.2f).build("bundle_monster"));
    public static final RegistryObject<EntityType<CyclopsMonsterEntity>> CYCLOPS_MONSTER =
            ENTITY_TYPES.register("cyclops_monster", () -> EntityType.Builder.of(CyclopsMonsterEntity::new, MobCategory.MONSTER)
                    .sized(1f, 3f).build("cyclops_monster"));
    public static final RegistryObject<EntityType<ElectricMonsterEntity>> ELECTRIC_MONSTER =
            ENTITY_TYPES.register("electric_monster", () -> EntityType.Builder.of(ElectricMonsterEntity::new, MobCategory.MONSTER)
                    .sized(1.3f, 2f).build("electric_monster"));
    public static final RegistryObject<EntityType<ExplodingMonsterEntity>> EXPLODING_MONSTER =
            ENTITY_TYPES.register("exploding_monster", () -> EntityType.Builder.of(ExplodingMonsterEntity::new, MobCategory.MONSTER)
                    .sized(1f, 1.7f).build("exploding_monster"));
    public static final RegistryObject<EntityType<RidableMonsterEntity>> RIDABLE_MONSTER =
            ENTITY_TYPES.register( "ridable_monster", () -> EntityType.Builder.of(RidableMonsterEntity::new, MobCategory.MONSTER)
                    .sized(0.9f, 2.3f).build("ridable_monster"));
    public static final RegistryObject<EntityType<RottenMonsterEntity>> ROTTEN_MONSTER =
            ENTITY_TYPES.register("rotten_monster", () -> EntityType.Builder.of(RottenMonsterEntity::new, MobCategory.MONSTER)
                    .sized(0.9f, 3.2f).build("rotten_monster"));
    public static final RegistryObject<EntityType<FlamelingEntity>> FLAMELING =
            ENTITY_TYPES.register("flameling", () -> EntityType.Builder.of(FlamelingEntity::new, MobCategory.MONSTER)
                    .sized(0.4f, 0.5f).build("flameling"));


    //RENDERERS

    @SubscribeEvent
    public static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityCreator.MONSTER.get(), MonsterRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.BLAZING_MONSTER.get(), BlazingMonsterRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.BUNDLE_MONSTER.get(), BundleMonsterRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.CYCLOPS_MONSTER.get(), CyclopsMonsterRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.ELECTRIC_MONSTER.get(), ElectricMonsterRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.EXPLODING_MONSTER.get(), ExplodingMonsterRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.RIDABLE_MONSTER.get(), RidableMonsterRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.ROTTEN_MONSTER.get(), RottenMonsterRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.ROTTEN_MONSTER.get(), RottenMonsterRenderer::new);
        event.registerEntityRenderer(ModEntityCreator.FLAMELING.get(), FlamelingRenderer::new);
    }

    //LAYERS

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.MONSTER_LAYER, MonsterModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BLAZING_MONSTER_LAYER, BlazingMonsterModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BUNDLE_MONSTER_LAYER, BundleMonsterModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.CYCLOPS_MONSTER_LAYER, CyclopsMonsterModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ELECTRIC_MONSTER_LAYER, ElectricMonsterModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.EXPLODING_MONSTER_LAYER, ExplodingMonsterModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.RIDABLE_MONSTER_LAYER, RidableMonsterModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ROTTEN_MONSTER_LAYER, RottenMonsterModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.FLAMELING_LAYER, FlamelingModel::createBodyLayer);
    }

    //ATTRIBUTES

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityCreator.MONSTER.get(), MonsterEntity.createAttributes().build());
        event.put(ModEntityCreator.BLAZING_MONSTER.get(), BlazingMonsterEntity.createAttributes().build());
        event.put(ModEntityCreator.BUNDLE_MONSTER.get(), BundleMonsterEntity.createAttributes().build());
        event.put(ModEntityCreator.CYCLOPS_MONSTER.get(), CyclopsMonsterEntity.createAttributes().build());
        event.put(ModEntityCreator.ELECTRIC_MONSTER.get(), ElectricMonsterEntity.createAttributes().build());
        event.put(ModEntityCreator.EXPLODING_MONSTER.get(), ExplodingMonsterEntity.createAttributes().build());
        event.put(ModEntityCreator.RIDABLE_MONSTER.get(), RidableMonsterEntity.createAttributes().build());
        event.put(ModEntityCreator.ROTTEN_MONSTER.get(), RottenMonsterEntity.createAttributes().build());
        event.put(ModEntityCreator.FLAMELING.get(), FlamelingEntity.createAttributes().build());
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
