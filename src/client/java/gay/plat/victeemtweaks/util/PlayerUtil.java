package gay.plat.victeemtweaks.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PlayerUtil {
    public static String getUUID(String username) {
        JsonObject playerElement = ApiUtil.getApiData("https://minecraftapi.net/api/v2/profile/"+username);
        if (playerElement != null) {
            JsonElement playerUUID = playerElement.get("full_uuid");
            if (playerUUID != null && !playerUUID.isJsonNull()) {
                return playerUUID.getAsString();
            }
        }
        return "";
    }
}
