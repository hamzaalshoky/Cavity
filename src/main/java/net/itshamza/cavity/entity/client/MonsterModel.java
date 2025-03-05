package net.itshamza.cavity.entity.client;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.cavity.entity.animations.ModAnimationDefinitions;
import net.itshamza.cavity.entity.custom.theoneandonly.MonsterEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class MonsterModel<T extends MonsterEntity> extends HierarchicalModel<T> {
	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart head;

	public MonsterModel(ModelPart root) {
		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.head = this.body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -14.0F, -3.0F, 8.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 34).addBox(-1.0F, -5.0F, -4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-4.0F, -10.0F, -2.0F, 8.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 34).addBox(-4.0F, -16.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

		PartDefinition hand_l = body.addOrReplaceChild("hand_l", CubeListBuilder.create().texOffs(24, 20).addBox(0.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -10.0F, 0.0F));

		PartDefinition hand_r = body.addOrReplaceChild("hand_r", CubeListBuilder.create().texOffs(24, 20).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -10.0F, 0.0F));

		PartDefinition leg_r = all.addOrReplaceChild("leg_r", CubeListBuilder.create().texOffs(28, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

		PartDefinition leg_l = all.addOrReplaceChild("leg_l", CubeListBuilder.create().texOffs(28, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 48, 48);
	}

	@Override
	public void setupAnim(MonsterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		if(entity.isAggressive()){
			this.animateWalk(ModAnimationDefinitions.RUN, limbSwing, limbSwingAmount, 2f, 2.5f);
		}else{
			this.animateWalk(ModAnimationDefinitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		}
		this.animate(entity.idleAnimationState, ModAnimationDefinitions.IDLE, ageInTicks, 1f);
		this.animate(entity.attackAnimationState, ModAnimationDefinitions.ATTACK, ageInTicks, 1f);
	}
	private void applyHeadRotation(MonsterEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
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