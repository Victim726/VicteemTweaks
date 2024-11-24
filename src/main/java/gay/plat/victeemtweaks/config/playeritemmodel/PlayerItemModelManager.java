package gay.plat.victeemtweaks.config.playeritemmodel;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gay.plat.victeemtweaks.VicteemTweaks;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerItemModelManager {
    private static File fileMemberData;

    public static void prepareConfigFile() {
        fileMemberData = new File(FabricLoader.getInstance().getConfigDir().toString(), "playeritemmodels.json");
        if (!FabricLoader.getInstance().getConfigDir().resolve("playeritemmodels.json").toFile().exists()) {
            try {
                FileWriter fileWriter = new FileWriter(fileMemberData);
                fileWriter.write("{}");
                fileWriter.close();
            } catch (IOException error) {
                VicteemTweaks.LOGGER.error("couldn't create PlayerItemModel config file");
                error.printStackTrace();
            }
            VicteemTweaks.LOGGER.info("created PlayerItemModel config file");
        }
    }

    public static void loadConfigFile() {
        prepareConfigFile();
        try (FileReader fileReader = new FileReader(fileMemberData)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            JsonObject playerItemModelsJson = new Gson().fromJson(bufferedReader, JsonObject.class);
            bufferedReader.close();
            Type playerItemModelsMapType = new TypeToken<Map<String, PlayerItemModelClass>>() {}.getType();
            Map<String, PlayerItemModelClass> uuidPlayerItemModels = new Gson().fromJson(playerItemModelsJson.toString(), playerItemModelsMapType);
            PlayerItemModelData.playerNameMap = new ConcurrentHashMap<>();
            for (String i : uuidPlayerItemModels.keySet()) {
                PlayerItemModelData.playerNameMap.put(i,uuidPlayerItemModels.get(i));
            }
        } catch (IOException error) {
            System.err.println("Couldn't read PlayerItemModel configuration file");
            error.printStackTrace();
        }
    }

    public static void writeConfigFile() {
        try {
            FileWriter fileWriter = new FileWriter(fileMemberData);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fileWriter.write(gson.toJson(PlayerItemModelData.playerNameMap));
            fileWriter.close();
        } catch (IOException error) {
            System.err.println("Couldn't write to PlayerItemModel configuration file");
            error.printStackTrace();
        }
    }
}
