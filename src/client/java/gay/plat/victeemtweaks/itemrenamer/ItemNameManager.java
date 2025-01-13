package gay.plat.victeemtweaks.itemrenamer;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gay.plat.victeemtweaks.VicteemTweaks;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ItemNameManager {
    private static ConcurrentHashMap<String, HashMap<String,String>> playerList = new ConcurrentHashMap<>();
    private static File itemRenamerFile;

    public static HashMap<String,String> get(String uuid) {
        return Objects.requireNonNullElse(playerList.get(uuid),new HashMap<>());
    }

    public static void set(String uuid, HashMap<String,String> itemNameMap) {
        playerList.put(uuid,itemNameMap);
        if (itemNameMap.isEmpty()) {
            playerList.remove(uuid);
        }
    }

    public static void renameItemFromConfig(ItemStack stack, String uuid) {
        String itemType = stack.getItem().toString();

        if (get(uuid).get(itemType) != null && stack.get(DataComponentTypes.CUSTOM_NAME) == null) {
            stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(get(uuid).get(itemType)));
        }
    }

    public static void prepareConfigFile() {
        itemRenamerFile = new File(FabricLoader.getInstance().getConfigDir().toString(), "itemrenamer.json");
        if (!itemRenamerFile.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(itemRenamerFile);
                fileWriter.write("{}");
                fileWriter.close();
            } catch (IOException error) {
                VicteemTweaks.LOGGER.error("Couldn't create ItemRenamer config file");
                error.printStackTrace();
            }
        }
    }

    public static void loadConfigFile() {
        prepareConfigFile();
        try (FileReader fileReader = new FileReader(itemRenamerFile)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            JsonObject playerItemModelsJson = new Gson().fromJson(bufferedReader, JsonObject.class);
            bufferedReader.close();
            Type playerItemModelsMapType = new TypeToken<ConcurrentHashMap<String, HashMap<String,String>>>() {}.getType();
            playerList = new Gson().fromJson(playerItemModelsJson.toString(), playerItemModelsMapType);
        } catch (IOException error) {
            System.err.println("Couldn't read PlayerItemModel configuration file");
            error.printStackTrace();
        }
    }

    public static void writeConfigFile() {
        try {
            FileWriter fileWriter = new FileWriter(itemRenamerFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fileWriter.write(gson.toJson(playerList));
            fileWriter.close();
        } catch (IOException error) {
            System.err.println("Couldn't write to PlayerItemModel configuration file");
            error.printStackTrace();
        }
    }
}
