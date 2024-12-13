// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class golden_chain extends EntityModel<golden_chain> {
	private final ModelPart chains;
	private final ModelPart chain1;
	private final ModelPart chainpart1;
	private final ModelPart chain2;
	private final ModelPart chainpart2;
	public golden_chain(ModelPart root) {
		this.chains = root.getChild("chains");
		this.chain1 = this.chains.getChild("chain1");
		this.chainpart1 = this.chain1.getChild("chainpart1");
		this.chain2 = this.chains.getChild("chain2");
		this.chainpart2 = this.chain2.getChild("chainpart2");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData chains = modelPartData.addChild("chains", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData chain1 = chains.addChild("chain1", ModelPartBuilder.create(), ModelTransform.of(0.0F, -9.5F, 0.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData chainpart1 = chain1.addChild("chainpart1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -1.5F, -8.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData chain2 = chains.addChild("chain2", ModelPartBuilder.create(), ModelTransform.of(0.0F, -9.5F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData chainpart2 = chain2.addChild("chainpart2", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -1.5F, -8.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(golden_chain entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		chains.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}