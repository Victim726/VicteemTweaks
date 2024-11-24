package gay.plat.victeemtweaks.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gay.plat.victeemtweaks.VicteemTweaks;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.server.command.CommandManager;

public class ItemUtil {
    /*public static ItemStack createItemStack(String itemString) {
        try {
            return new ItemStackArgumentType(CommandManager.createRegistryAccess(BuiltinRegistries.createWrapperLookup())).parse(new StringReader(itemString)).createStack(1, false);
        } catch (CommandSyntaxException error) {
            throw new RuntimeException(error);
        }
    }*/
    public static void getStringEnchantments(String itemString) {
        JsonObject componentListGson = getStringComponents(itemString);
        componentListGson.get("enchantments");
        System.out.println(componentListGson.get("enchantments"));
    }

    public static JsonObject getStringComponents(String itemString) {
        if (!(itemString.contains("[") && itemString.endsWith("]"))) {
            return null;
        }
        String componentString = itemString.substring(itemString.indexOf("["));
        System.out.println(componentString);
        JsonObject componentListGson = new Gson().fromJson(componentString, JsonObject.class);
        System.out.println(componentListGson);
        return new Gson().fromJson(componentString, JsonObject.class);
    }
}
