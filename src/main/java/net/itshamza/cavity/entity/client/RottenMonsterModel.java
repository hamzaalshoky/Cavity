package net.itshamza.cavity.entity.client;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.cavity.entity.animations.ModAnimationDefinitions;
import net.itshamza.cavity.entity.custom.theoneandonly.RottenMonsterEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class RottenMonsterModel<T extends RottenMonsterEntity> extends HierarchicalModel<T> {
	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart headtwo;
	private final ModelPart headthree;

	public RottenMonsterModel(ModelPart root) {
		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.head = this.body.getChild("head1");
		this.headtwo = this.body.getChild("head_r");
		this.headthree = this.body.getChild("head_l");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition leg_l = all.addOrReplaceChild("leg_l", CubeListBuilder.create().texOffs(0, 38).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -12.0F, 1.0F));

		PartDefinition leg_r = all.addOrReplaceChild("leg_r", CubeListBuilder.create().texOffs(44, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, -12.0F, 1.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -16.0F, 1.0F, 8.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(2.0F, -24.0F, 1.0F, 8.0F, 14.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 54).addBox(-4.0F, -22.0F, 3.0F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -12.0F, -2.0F));

		PartDefinition hand_l2 = body.addOrReplaceChild("hand_l2", CubeListBuilder.create().texOffs(16, 46).addBox(0.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.0F, 3.0F));

		PartDefinition hand_r = body.addOrReplaceChild("hand_r", CubeListBuilder.create().texOffs(48, 0).addBox(-4.0F, -9.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -5.0F, 3.0F));

		PartDefinition hand_l1 = body.addOrReplaceChild("hand_l1", CubeListBuilder.create().texOffs(32, 48).addBox(0.0F, -2.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -12.0F, 2.0F));

		PartDefinition head1 = body.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(24, 0).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 38).addBox(-6.0F, -9.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 48).addBox(-4.0F, -18.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -22.0F, 2.0F));

		PartDefinition head_l = body.addOrReplaceChild("head_l", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -8.0F, -3.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -22.0F, 3.0F));

		PartDefinition head_r = body.addOrReplaceChild("head_r", CubeListBuilder.create().texOffs(24, 32).addBox(-2.0F, -8.0F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -16.0F, 2.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(RottenMonsterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimationDefinitions.ROTTEN_RUN, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, ModAnimationDefinitions.ROTTEN_IDLE, ageInTicks, 1f);
		//this.animate(entity.swimmingAnimationState, ModAnimationDefinitions.BEAVER_SWIM, ageInTicks, 1f);
	}
	private void applyHeadRotation(RottenMonsterEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
		this.headtwo.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.headtwo.xRot = pHeadPitch * ((float)Math.PI / 180F);
		this.headthree.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.headthree.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}


	public ModelPart root() {
		return all;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}