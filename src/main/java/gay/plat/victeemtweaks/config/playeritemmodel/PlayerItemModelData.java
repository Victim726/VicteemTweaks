package gay.plat.victeemtweaks.config.playeritemmodel;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerItemModelData {
    public static ConcurrentHashMap<String, PlayerItemModelClass> playerNameMap = new ConcurrentHashMap<>();

    public static void loadJsonData() {
        PlayerItemModelManager.loadConfigFile();
    }
    public static void writeJsonData() {
        PlayerItemModelManager.writeConfigFile();
    }

    public static void addNamePlayerItem(String uuid, String itemType, String itemName) {
        playerNameMap.putIfAbsent(uuid,new PlayerItemModelClass(uuid));
        playerNameMap.get(uuid).itemNameMap.put(itemType,itemName);
    }

    public static void removeNamePlayerItem(String uuid, String itemType) {
        if (playerNameMap.get(uuid) == null) {
            return;
        }
        playerNameMap.get(uuid).itemNameMap.remove(itemType);
        if (playerNameMap.get(uuid).itemNameMap.isEmpty()) {
            clearNamePlayerItem(uuid);
        }
    }

    public static void clearNamePlayerItem(String uuid) {
        if (playerNameMap.get(uuid) != null) {
            playerNameMap.remove(uuid);
        }
    }

    public static void copyNamePlayerItem(String uuidCopyFrom, String uuidCopyTo) {
        playerNameMap.putIfAbsent(uuidCopyTo,new PlayerItemModelClass(uuidCopyTo));
        playerNameMap.get(uuidCopyTo).itemNameMap = playerNameMap.get(uuidCopyFrom).itemNameMap;
    }

    public static void renameItem(ItemStack stack, String uuid, String itemType) {
        //System.out.println(uuid+" - "+itemType);
        //System.out.println(get(uuid).itemNameMap.get(itemType));
        if (playerNameMap.get(uuid) != null && playerNameMap.get(uuid).itemNameMap.get(itemType) != null) {
            stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(playerNameMap.get(uuid).itemNameMap.get(itemType)));
        }
    }
}
