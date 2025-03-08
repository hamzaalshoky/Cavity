package net.itshamza.cavity.entity.client;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.cavity.entity.animations.ModAnimationDefinitions;
import net.itshamza.cavity.entity.custom.theoneandonly.BundleMonsterEntity;
import net.itshamza.cavity.entity.custom.theoneandonly.RidableMonsterEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class RidableMonsterModel<T extends RidableMonsterEntity> extends HierarchicalModel<T> {
	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart head;

	public RidableMonsterModel(ModelPart root) {
		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.head = this.body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(28.0F, 24.0F, -1.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 24).addBox(-4.0F, -9.0F, -2.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.0F, -21.0F, -2.0F, 16.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-28.0F, -9.0F, -1.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 41).addBox(-1.0F, -5.0F, -7.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-4.0F, -7.0F, -5.0F, 8.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(44, 38).addBox(-2.0F, -12.0F, -5.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(13, 54).addBox(-9.0F, -6.0F, -4.0F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.0F, -1.0F));

		PartDefinition hand_l = body.addOrReplaceChild("hand_l", CubeListBuilder.create().texOffs(28, 38).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -12.0F, 4.0F));

		PartDefinition hand_r = body.addOrReplaceChild("hand_r", CubeListBuilder.create().texOffs(28, 38).mirror().addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -12.0F, 4.0F));

		PartDefinition leg_l = all.addOrReplaceChild("leg_l", CubeListBuilder.create().texOffs(0, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-26.0F, -10.0F, -1.0F));

		PartDefinition leg_r = all.addOrReplaceChild("leg_r", CubeListBuilder.create().texOffs(0, 41).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-30.0F, -10.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(RidableMonsterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if(entity.isCharging()){
			this.animateWalk(ModAnimationDefinitions.RIDEABLE_CHARGE, limbSwing, limbSwingAmount, 2f, 2.5f);
		}else{
			this.animateWalk(ModAnimationDefinitions.RIDEABLE_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		}
		this.animate(entity.idleAnimationState, ModAnimationDefinitions.RIDEABLE_IDLE, ageInTicks, 1f);
		this.animate(entity.chargeAnimationState, ModAnimationDefinitions.RIDEABLE_CHARGE, ageInTicks, 1f);
	}

	private void applyHeadRotation(RidableMonsterEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
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