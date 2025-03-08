package net.itshamza.cavity.entity.client;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.cavity.entity.animations.ModAnimationDefinitions;
import net.itshamza.cavity.entity.custom.theoneandonly.BlazingMonsterEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BlazingMonsterModel<T extends BlazingMonsterEntity> extends HierarchicalModel<T> {
	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart head;

	public BlazingMonsterModel(ModelPart root) {
		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.head = this.body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -8.0F, -10.0F, 24.0F, 8.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 27).addBox(-10.0F, -16.0F, -9.0F, 20.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 2.0F));

		PartDefinition hand = body.addOrReplaceChild("hand", CubeListBuilder.create().texOffs(0, 59).addBox(-6.0F, -4.0F, -14.0F, 6.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, -4.0F, 2.0F));

		PartDefinition hand2 = body.addOrReplaceChild("hand2", CubeListBuilder.create().texOffs(0, 59).mirror().addBox(0.0F, -4.0F, -14.0F, 6.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, -4.0F, 2.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(44, 59).addBox(-6.0F, -10.0F, -4.0F, 12.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(72, 27).addBox(-2.0F, -16.0F, -6.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(72, 47).addBox(-2.0F, -6.0F, -8.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 96, 96);
	}

	@Override
	public void setupAnim(BlazingMonsterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);

		//this.animateWalk(ModAnimationDefinitions.BEAVER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		if(entity.isInflated()){
			this.animate(entity.idleAnimationState, ModAnimationDefinitions.BLAZING_INFLATE, ageInTicks, 1f);
		}else{
			this.animate(entity.idleAnimationState, ModAnimationDefinitions.BLAZING_IDLE, ageInTicks, 1f);
		}
		//this.animate(entity.swimmingAnimationState, ModAnimationDefinitions.BEAVER_SWIM, ageInTicks, 1f);
	}
	private void applyHeadRotation(BlazingMonsterEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
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