package net.itshamza.cavity.entity.client;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.cavity.entity.animations.ModAnimationDefinitions;
import net.itshamza.cavity.entity.custom.theoneandonly.FlamelingEntity;
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

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(-3.0F, 23.0F, -3.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(-1.0F, -4.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.0F, 3.0F));

		PartDefinition leg = all.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(8, 12).mirror().addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -2.0F, 3.5F));

		PartDefinition leg2 = all.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(8, 12).addBox(-0.5F, -0.0872F, -0.4962F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -2.0F, 3.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(FlamelingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(ModAnimationDefinitions.FLAMELING_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
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