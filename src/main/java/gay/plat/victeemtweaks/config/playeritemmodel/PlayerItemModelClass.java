package gay.plat.victeemtweaks.config.playeritemmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gay.plat.victeemtweaks.VicteemTweaks;
import gay.plat.victeemtweaks.util.AssetsUtil;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerItemModelClass {
    public String uuid;
    public ConcurrentHashMap<String, String> itemNameMap = new ConcurrentHashMap<>();

    public PlayerItemModelClass(String uuid) {
        this.uuid = uuid;
        itemNameMap.clear();
    }

    public String getItemNameMapString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(itemNameMap);
    }

    //public String getDefaultName(String itemID) {
    //    return itemNameMap.put(itemID, AssetsUtil.getAssetFile(Identifier.of(VicteemTweaks.MOD_ID,"lang/en_us.json")).get(String.format("item.%s.%s",itemID, StringUtils.substringBefore(itemID,":"),StringUtils.substringAfter(itemID,":"))).getAsString());
    //}
}
