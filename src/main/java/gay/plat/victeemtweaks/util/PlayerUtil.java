package gay.plat.victeemtweaks.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

import java.math.BigInteger;
import java.util.UUID;

public class PlayerUtil {
    public static String addDashesUUID(String noDashUuidString) {
        BigInteger bi1 = new BigInteger(noDashUuidString.substring(0, 16), 16);
        BigInteger bi2 = new BigInteger(noDashUuidString.substring(16, 32), 16);
        UUID uuid = new UUID(bi1.longValue(), bi2.longValue());
        return uuid.toString();
    }

    public static int getPing(String uuid) {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            return 0;
        } else {
            PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(UUID.fromString(uuid));
            return playerListEntry == null ? 0 : playerListEntry.getLatency();
        }
    }

    public static String getUUID(String username) {
        JsonObject playerElement = ApiUtil.getApiData(username);
        if (playerElement != null) {
            JsonElement playerUUID = playerElement.get("uuid");
            if (playerUUID != null && !playerUUID.isJsonNull()) {
                return addDashesUUID(playerUUID.getAsString());
            }
        }
        return "";
    }

    public static String getDashlessUUID(String username) {
        JsonObject playerElement = ApiUtil.getApiData(username);
        if (playerElement != null) {
            JsonElement playerUUID = playerElement.get("uuid");
            if (playerUUID != null && !playerUUID.isJsonNull()) {
                return playerUUID.getAsString();
            }
        }
        return "";
    }
}
