package gay.plat.victeemtweaks.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gay.plat.victeemtweaks.VicteemTweaks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class AssetsUtil {
    public static JsonObject getAssetFile(Identifier identifier) {
        Optional<Resource> resource = MinecraftClient.getInstance().getResourceManager().getResource(identifier);
        if (resource.isEmpty()) {
            return null;
        }
        try {
            BufferedReader bufferedReader = resource.get().getReader();
            JsonObject jsonObject = new Gson().fromJson(bufferedReader, JsonObject.class);
            bufferedReader.close();
            return jsonObject;
        } catch (IOException error) {
            System.err.println("Unable to find Asset File in File Path "+identifier.getNamespace());
            error.printStackTrace();
        }
        return null;
    }
}
