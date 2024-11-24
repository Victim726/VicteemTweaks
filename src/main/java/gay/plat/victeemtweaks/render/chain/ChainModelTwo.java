package gay.plat.victeemtweaks.render.chain;

import gay.plat.victeemtweaks.VicteemTweaks;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ChainModelTwo extends Model {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(VicteemTweaks.MOD_ID, "chain_two"), "main");

    private final ModelPart chain2;
    private final ModelPart chainpart2;

    public ChainModelTwo(ModelPart root) {
        super(GlowyRenderLayer::get);
        this.chain2 = root.getChild("chain2");
        this.chainpart2 = this.chain2.getChild("chainpart2");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData chain2 = modelPartData.addChild("chain2", ModelPartBuilder.create(), ModelTransform.of(0.0F, 14.5F, 0.0F, -0.5236F, 0.0F, 0.0F));

        ModelPartData chainpart2 = chain2.addChild("chainpart2", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -1.5F, -8.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        chain2.render(matrices, vertices, light, overlay, -1);
    }
}