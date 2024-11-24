package gay.plat.victeemtweaks;

import gay.plat.victeemtweaks.particles.ChainParticleOne;
import gay.plat.victeemtweaks.particles.ChainParticleTwo;
import gay.plat.victeemtweaks.particles.VicteemTweaksParticles;
import gay.plat.victeemtweaks.render.chain.ChainModelOne;
import gay.plat.victeemtweaks.render.chain.ChainModelTwo;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class VicteemTweaksClient implements ClientModInitializer {
    public static SimpleParticleType GOLDEN_CHAIN_ONE;
    public static SimpleParticleType GOLDEN_CHAIN_TWO;
    @Override
    public void onInitializeClient() {
        VicteemTweaks.LOGGER.info("ClientInit");

        EntityModelLayerRegistry.registerModelLayer(ChainModelOne.MODEL_LAYER, ChainModelOne::getTexturedModelData);
        GOLDEN_CHAIN_ONE = Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID, "golden_chains_one"), FabricParticleTypes.simple(true));
        ParticleFactoryRegistry.getInstance().register(GOLDEN_CHAIN_ONE, fabricSpriteProvider -> new ChainParticleOne.DefaultFactory(fabricSpriteProvider, Identifier.of(VicteemTweaks.MOD_ID, "textures/entity/killeffect/golden_chains.png"), 1.0f, 1.0f, 1.0f));

        EntityModelLayerRegistry.registerModelLayer(ChainModelTwo.MODEL_LAYER, ChainModelTwo::getTexturedModelData);
        GOLDEN_CHAIN_TWO = Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID, "golden_chains_two"), FabricParticleTypes.simple(true));
        ParticleFactoryRegistry.getInstance().register(GOLDEN_CHAIN_TWO, fabricSpriteProvider -> new ChainParticleTwo.DefaultFactory(fabricSpriteProvider, Identifier.of(VicteemTweaks.MOD_ID, "textures/entity/killeffect/golden_chains.png"), 1.0f, 1.0f, 1.0f));

        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_ALT, SweepAttackParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_1, SweepAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_1_ALT, SweepAttackParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_2, SweepAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_2_ALT, SweepAttackParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_3, SweepAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_3_ALT, SweepAttackParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_4, SweepAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_4_ALT, SweepAttackParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_5, SweepAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_5_ALT, SweepAttackParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_6, SweepAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_6_ALT, SweepAttackParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_7, SweepAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_7_ALT, SweepAttackParticle.Factory::new);


    }
}