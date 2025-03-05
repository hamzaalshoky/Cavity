package net.itshamza.cavity.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.entity.custom.theoneandonly.CyclopsMonsterEntity;
import net.itshamza.cavity.entity.layer.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CyclopsMonsterRenderer extends MobRenderer<CyclopsMonsterEntity, CyclopsMonsterModel<CyclopsMonsterEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(Cavity.MOD_ID,"textures/entity/monster/cyclops_monster.png");

    public CyclopsMonsterRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CyclopsMonsterModel<>(pContext.bakeLayer(ModModelLayers.CYCLOPS_MONSTER_LAYER)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(CyclopsMonsterEntity pEntity) {
        return LOCATION;
    }

    @Override
    public void render(CyclopsMonsterEntity pEntity, float pEntityYaw, float pPartialTicks,
                       PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.45f, 0.45f, 0.45f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
