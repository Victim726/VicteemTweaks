package gay.plat.victeemtweaks.render.chain;

import gay.plat.victeemtweaks.VicteemTweaks;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ChainModelOne extends Model {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(VicteemTweaks.MOD_ID, "chain_one"), "main");

    private final ModelPart chain1;
    private final ModelPart chainpart1;

    public ChainModelOne(ModelPart root) {
        super(GlowyRenderLayer::get);
        this.chain1 = root.getChild("chain1");
        this.chainpart1 = this.chain1.getChild("chainpart1");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData chain1 = modelPartData.addChild("chain1", ModelPartBuilder.create(), ModelTransform.of(0.0F, 14.5F, 0.0F, 0.5236F, 0.0F, 0.0F));

        ModelPartData chainpart1 = chain1.addChild("chainpart1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -1.5F, -8.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        chain1.render(matrices, vertices, light, overlay, -1);
    }
}