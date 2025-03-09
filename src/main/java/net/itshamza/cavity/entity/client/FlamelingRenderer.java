package net.itshamza.cavity.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.itshamza.cavity.Cavity;
import net.itshamza.cavity.entity.custom.theoneandonly.FlamelingEntity;
import net.itshamza.cavity.entity.layer.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FlamelingRenderer extends MobRenderer<FlamelingEntity, FlamelingModel<FlamelingEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(Cavity.MOD_ID,"textures/entity/flameling.png");

    public FlamelingRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FlamelingModel<>(pContext.bakeLayer(ModModelLayers.FLAMELING_LAYER)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(FlamelingEntity pEntity) {
        return LOCATION;
    }

    @Override
    public void render(FlamelingEntity pEntity, float pEntityYaw, float pPartialTicks,
                       PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.45f, 0.45f, 0.45f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
