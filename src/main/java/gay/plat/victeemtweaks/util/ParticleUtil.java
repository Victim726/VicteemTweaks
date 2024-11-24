package gay.plat.victeemtweaks.util;

import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ParticleUtil {
    static int particleMultiplier = 10;
    public static void createParticleLine(int color, Vec3d originPos, Vec3d endPos, World world){
        for (int i = 1; i <= Math.floor(originPos.distanceTo(endPos)*particleMultiplier); i++) {
            double lineDecimal = i/Math.floor(originPos.distanceTo(endPos)*particleMultiplier);
            createParticle(color,originPos.add(endPos.subtract(originPos).multiply(lineDecimal)),world);
        }
    }

    public static void createParticle(int color, Vec3d particlePos, World world){
        //world.addParticle(new DustParticleEffect(Vec3d.unpackRgb(color).toVector3f(),2),particlePos.getX(),particlePos.getY(),particlePos.getZ(),0,0,0);
        world.addImportantParticle(new DustParticleEffect(Vec3d.unpackRgb(color).toVector3f(),1),particlePos.getX(),particlePos.getY(),particlePos.getZ(),0,0,0);
    }

    public static void createParticleCircle(int color, Vec3d centerPos, int radius, World world){
        for (int i = 0; i < Math.floor(particleMultiplier*radius*(2*Math.PI)*(2*Math.PI)); i++) {
            double theta = i/(particleMultiplier*radius*(2*Math.PI));
            createParticle(color,centerPos.add(radius*Math.cos(theta),0,radius*Math.sin(theta)),world);
        }
    }
}
