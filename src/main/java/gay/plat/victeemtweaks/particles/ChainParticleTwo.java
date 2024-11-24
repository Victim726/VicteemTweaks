package gay.plat.victeemtweaks.particles;

import gay.plat.victeemtweaks.render.chain.ChainModelTwo;
import gay.plat.victeemtweaks.render.chain.GlowyRenderLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class ChainParticleTwo extends Particle {
    public final Identifier texture;
    final RenderLayer layer;
    public float yaw;
    public float pitch;
    public float prevYaw;
    public float prevPitch;
    protected PlayerEntity owner;
    Model model;

    protected ChainParticleTwo(ClientWorld world, double x, double y, double z, Identifier texture, float red, float green, float blue) {
        super(world, x, y, z);
        this.texture = texture;
        this.model = new ChainModelTwo(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ChainModelTwo.MODEL_LAYER));
        this.layer = RenderLayer.getEntityTranslucent(texture);
        this.gravityStrength = 0.0F;

        this.maxAge = 1048576;
        this.owner = world.getClosestPlayer((TargetPredicate.createNonAttackable()).setBaseMaxDistance(1D), this.x, this.y, this.z);

        if (this.owner == null) {
            this.markDead();
        }

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 1f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float f = (float) (MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float) (MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float) (MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());

        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(f, g, h);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90-this.owner.getBodyYaw()));
        //matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(g, this.prevYaw, this.yaw) - 180));
        //matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(g, this.prevPitch, this.pitch)));
        //matrixStack.scale(0.5F, -0.5F, 0.5F);
        //matrixStack.translate(0, -1, 0);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vertexConsumer2 = immediate.getBuffer(GlowyRenderLayer.get(texture));
        if (this.alpha > 0) {
            this.model.render(matrixStack, vertexConsumer2, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, -1);
        }
        immediate.draw();
    }

    @Override
    public void tick() {
        /*if (this.age > 10) {
            this.alpha = 1f;
        } else {
            this.alpha = 1f;
        }*/

        if (owner != null) {
            this.prevPosX = this.x;
            this.prevPosY = this.y;
            this.prevPosZ = this.z;

            // die if old enough
            if (this.age++ >= this.maxAge) {
                this.markDead();
            }

            this.setPos(owner.getX(), owner.getY(), owner.getZ());

            this.prevYaw = this.yaw;
            this.yaw = owner.age * 2;
        } else {
            this.markDead();
        }
    }


    public static class DefaultFactory implements ParticleFactory<SimpleParticleType> {
        private final Identifier texture;
        private final float red;
        private final float green;
        private final float blue;

        public DefaultFactory(SpriteProvider spriteProvider, Identifier texture, float red, float green, float blue) {
            this.texture = texture;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ChainParticleTwo(world, x, y, z, this.texture, this.red, this.green, this.blue);
        }
    }
}
