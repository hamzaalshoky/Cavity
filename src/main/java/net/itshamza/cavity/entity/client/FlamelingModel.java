package net.itshamza.cavity.entity.client;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.cavity.entity.animations.ModAnimationDefinitions;
import net.itshamza.cavity.entity.custom.theoneandonly.FlamelingEntity;
import net.itshamza.cavity.entity.custom.theoneandonly.IceMonsterEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class FlamelingModel<T extends FlamelingEntity> extends HierarchicalModel<T> {
	private final ModelPart all;

	public FlamelingModel(ModelPart root) {
		this.all = root.getChild("all");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(FlamelingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		//this.animateWalk(ModAnimationDefinitions.BEAVER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, ModAnimationDefinitions.FLAMELING_IDLE, ageInTicks, 1f);
		//this.animate(entity.swimmingAnimationState, ModAnimationDefinitions.BEAVER_SWIM, ageInTicks, 1f);
	}
	private void applyHeadRotation(FlamelingEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {

	}


	public ModelPart root() {
		return all;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}