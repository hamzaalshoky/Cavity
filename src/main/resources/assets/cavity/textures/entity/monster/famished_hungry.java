// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class famished_hungry<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "famished_hungry"), "main");
	private final ModelPart famished;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public famished_hungry(ModelPart root) {
		this.famished = root.getChild("famished");
		this.head = this.famished.getChild("head");
		this.body = this.famished.getChild("body");
		this.left_arm = this.famished.getChild("left_arm");
		this.right_arm = this.famished.getChild("right_arm");
		this.left_leg = this.famished.getChild("left_leg");
		this.right_leg = this.famished.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition famished = partdefinition.addOrReplaceChild("famished", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = famished.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 22).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(38, 38).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		PartDefinition body = famished.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 16).addBox(-3.0F, 8.0F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		PartDefinition left_arm = famished.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(14, 46).mirror().addBox(0.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -23.0F, 0.0F, -0.9163F, 0.0F, 0.0F));

		PartDefinition right_arm = famished.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 46).addBox(-3.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -23.0F, 0.0F, -0.9163F, 0.0F, 0.0F));

		PartDefinition left_leg = famished.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(14, 28).addBox(-0.9F, 2.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -16.0F, 0.0F));

		PartDefinition right_leg = famished.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-2.1F, 2.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.9F, -16.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		famished.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}