package gay.plat.victeemtweaks.config.customsweepparticle;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gay.plat.victeemtweaks.VicteemTweaks;
import gay.plat.victeemtweaks.util.AssetsUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SweepParticleManager {
    public static void loadConfigFile() {
        Identifier sweep_config = Identifier.of(VicteemTweaks.MOD_ID,"particles/sweep_particle_config.json");
        JsonObject sweepParticleJson = AssetsUtil.getAssetFile(sweep_config);

        if (sweepParticleJson == null) {
            return;
        }

        Type sweepParticleMapType = new TypeToken<Map<String, SweepParticleClass>>() {}.getType();
        Map<String, SweepParticleClass> sweepParticleMap = new Gson().fromJson(sweepParticleJson.toString(), sweepParticleMapType);
        SweepParticleData.sweepParticleMap = new ConcurrentHashMap<>();
        for (String i : sweepParticleMap.keySet()) {
            SweepParticleData.sweepParticleMap.put(i, sweepParticleMap.get(i));
            //SweepParticleData.sweepParticleMap.get(i).itemID = i;
        }
    }
}
