package net.itshamza.cavity.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.entity.custom.theoneandonly.ExplodingMonsterEntity;
import net.itshamza.cavity.entity.layer.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ExplodingMonsterRenderer extends MobRenderer<ExplodingMonsterEntity, ExplodingMonsterModel<ExplodingMonsterEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(Cavity.MOD_ID,"textures/entity/monster/exploding_monster.png");

    public ExplodingMonsterRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ExplodingMonsterModel<>(pContext.bakeLayer(ModModelLayers.EXPLODING_MONSTER_LAYER)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(ExplodingMonsterEntity pEntity) {
        return LOCATION;
    }

    @Override
    public void render(ExplodingMonsterEntity pEntity, float pEntityYaw, float pPartialTicks,
                       PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.45f, 0.45f, 0.45f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
