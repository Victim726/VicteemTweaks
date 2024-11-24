package gay.plat.victeemtweaks.particles;

import gay.plat.victeemtweaks.VicteemTweaks;
import gay.plat.victeemtweaks.config.customsweepparticle.SweepParticleClass;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.concurrent.ConcurrentHashMap;

public class VicteemTweaksParticles {
    public static ConcurrentHashMap<String, SimpleParticleType> sweepParticleMap = new ConcurrentHashMap<>();

    public static final SimpleParticleType SWEEP_ATTACK_ALT = FabricParticleTypes.simple();

    public static final SimpleParticleType SWEEP_ATTACK_1 = FabricParticleTypes.simple();
    public static final SimpleParticleType SWEEP_ATTACK_1_ALT = FabricParticleTypes.simple();

    public static final SimpleParticleType SWEEP_ATTACK_2 = FabricParticleTypes.simple();
    public static final SimpleParticleType SWEEP_ATTACK_2_ALT = FabricParticleTypes.simple();

    public static final SimpleParticleType SWEEP_ATTACK_3 = FabricParticleTypes.simple();
    public static final SimpleParticleType SWEEP_ATTACK_3_ALT = FabricParticleTypes.simple();

    public static final SimpleParticleType SWEEP_ATTACK_4 = FabricParticleTypes.simple();
    public static final SimpleParticleType SWEEP_ATTACK_4_ALT = FabricParticleTypes.simple();

    public static final SimpleParticleType SWEEP_ATTACK_5 = FabricParticleTypes.simple();
    public static final SimpleParticleType SWEEP_ATTACK_5_ALT = FabricParticleTypes.simple();

    public static final SimpleParticleType SWEEP_ATTACK_6 = FabricParticleTypes.simple();
    public static final SimpleParticleType SWEEP_ATTACK_6_ALT = FabricParticleTypes.simple();

    public static final SimpleParticleType SWEEP_ATTACK_7 = FabricParticleTypes.simple();
    public static final SimpleParticleType SWEEP_ATTACK_7_ALT = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_alt"), SWEEP_ATTACK_ALT);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_1"), SWEEP_ATTACK_1);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_1_alt"), SWEEP_ATTACK_1_ALT);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_2"), SWEEP_ATTACK_2);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_2_alt"), SWEEP_ATTACK_2_ALT);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_3"), SWEEP_ATTACK_3);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_3_alt"), SWEEP_ATTACK_3_ALT);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_4"), SWEEP_ATTACK_4);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_4_alt"), SWEEP_ATTACK_4_ALT);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_5"), SWEEP_ATTACK_5);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_5_alt"), SWEEP_ATTACK_5_ALT);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_6"), SWEEP_ATTACK_6);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_6_alt"), SWEEP_ATTACK_6_ALT);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_7"), SWEEP_ATTACK_7);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(VicteemTweaks.MOD_ID,"sweep_attack_7_alt"), SWEEP_ATTACK_7_ALT);

        sweepParticleMap.put("sweep_1",SWEEP_ATTACK_1);
        sweepParticleMap.put("sweep_2",SWEEP_ATTACK_2);
        sweepParticleMap.put("sweep_3",SWEEP_ATTACK_3);
        sweepParticleMap.put("sweep_4",SWEEP_ATTACK_4);
        sweepParticleMap.put("sweep_5",SWEEP_ATTACK_5);
        sweepParticleMap.put("sweep_6",SWEEP_ATTACK_6);
        sweepParticleMap.put("sweep_7",SWEEP_ATTACK_7);

        sweepParticleMap.put("sweep_1_alt",SWEEP_ATTACK_1_ALT);
        sweepParticleMap.put("sweep_2_alt",SWEEP_ATTACK_2_ALT);
        sweepParticleMap.put("sweep_3_alt",SWEEP_ATTACK_3_ALT);
        sweepParticleMap.put("sweep_4_alt",SWEEP_ATTACK_4_ALT);
        sweepParticleMap.put("sweep_5_alt",SWEEP_ATTACK_5_ALT);
        sweepParticleMap.put("sweep_6_alt",SWEEP_ATTACK_6_ALT);
        sweepParticleMap.put("sweep_7_alt",SWEEP_ATTACK_7_ALT);
    }
}
