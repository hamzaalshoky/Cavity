package net.itshamza.cavity.entity.client;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.cavity.entity.animations.ModAnimationDefinitions;
import net.itshamza.cavity.entity.custom.theoneandonly.BundleMonsterEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BundleMonsterModel<T extends BundleMonsterEntity> extends HierarchicalModel<T> {
	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart head;

	public BundleMonsterModel(ModelPart root) {
		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.head = this.body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -4.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.75F, -15.0F, 9.0F, 7.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(36, 33).addBox(-1.5F, -3.75F, -17.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 25).addBox(-4.5F, -14.75F, -6.0F, 9.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(0, 44).addBox(-3.5F, -6.75F, -13.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(36, 39).addBox(1.5F, -8.75F, -13.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.25F, 12.0F));

		PartDefinition hand_l = body.addOrReplaceChild("hand_l", CubeListBuilder.create().texOffs(36, 25).addBox(-1.0F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 0.25F, -11.5F));

		PartDefinition hand_r = body.addOrReplaceChild("hand_r", CubeListBuilder.create().texOffs(36, 25).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 0.25F, -12.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(59.5F, -31.75F, -12.0F));

		PartDefinition leg_r = all.addOrReplaceChild("leg_r", CubeListBuilder.create().texOffs(36, 25).addBox(-2.0F, -1.0F, 5.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -4.0F, 0.5F));

		PartDefinition leg_l = all.addOrReplaceChild("leg_l", CubeListBuilder.create().texOffs(36, 25).addBox(-1.0F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -4.0F, 7.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(BundleMonsterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimationDefinitions.BUNDLE_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, ModAnimationDefinitions.BUNDLE_IDLE, ageInTicks, 1f);
		//this.animate(entity.swimmingAnimationState, ModAnimationDefinitions.BEAVER_SWIM, ageInTicks, 1f);
	}
	private void applyHeadRotation(BundleMonsterEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}


	public ModelPart root() {
		return all;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}