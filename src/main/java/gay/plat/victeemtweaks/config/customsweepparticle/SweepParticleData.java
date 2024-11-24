package gay.plat.victeemtweaks.config.customsweepparticle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SweepParticleData {
    public static ConcurrentHashMap<String, SweepParticleClass> sweepParticleMap = new ConcurrentHashMap<>();
    public static List<List<SweepParticleClass>> weightedList = new ArrayList<>();

    public static void loadJsonData() {
        SweepParticleManager.loadConfigFile();
        setWeightedList();
    }

    public static void setWeightedList() {
        weightedList.clear();
        List<Integer> weightList = new ArrayList<>();
        for (String id:sweepParticleMap.keySet()) {
            if (!(weightList.contains(sweepParticleMap.get(id).weight))) {
                weightList.add(sweepParticleMap.get(id).weight);
            }
        }
        while (!weightList.isEmpty()) {
            weightedList.add(getListOfCommonWeights(Collections.max(weightList)));
            weightList.remove(Collections.max(weightList));
        }
    }

    public static List<SweepParticleClass> getListOfCommonWeights(int weight) {
        List<SweepParticleClass> weightList = new ArrayList<>();
        for (String id:sweepParticleMap.keySet()) {
            if (sweepParticleMap.get(id).weight == weight) {
                weightList.add(sweepParticleMap.get(id));
            }
        }
        return weightList;
    }
}
