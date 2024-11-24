package gay.plat.victeemtweaks.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DuraUtil {
    public static int getFunctionalMaxDura(PlayerEntity player, ItemStack inputArmorStack) {
        PlayerInventory inventory = player.getInventory();

        List<Integer> armorMaxDuraList = new ArrayList<>();
        int armorDuraAverage = 0;
        int armorCount = 0;

        for (ItemStack i:inventory.armor) {
            if(i.getEnchantments().toString().contains("mending") && !(i.toString().contains("air")) && !(i.getComponentChanges().toString().contains("minecraft:unbreakable"))) {
                armorMaxDuraList.add(i.getMaxDamage());
                armorDuraAverage += i.getMaxDamage() - i.getDamage();
                armorCount++;
            }
        }

        if (armorCount == 0 || !(inputArmorStack.getEnchantments().toString().contains("mending")) || inputArmorStack.toString().contains("air")) {
            return 0;
        }

        int maxHealGoal = (int)(Math.floor(Collections.min(armorMaxDuraList)/10.0)*10);

        int healDura = inventory.count(Items.EXPERIENCE_BOTTLE)*14;
        int goalValue = Math.min(armorDuraAverage+healDura/armorCount,maxHealGoal);

        return goalValue;
    }

    public static String getFunctionalMaxDuraString(PlayerEntity player, ItemStack inputArmorStack) {
        PlayerInventory inventory = player.getInventory();

        List<Integer> armorMaxDuraList = new ArrayList<>();
        int armorDuraAverage = 0;
        int armorCount = 0;

        for (ItemStack i:inventory.armor) {
            if(i.getEnchantments().toString().contains("mending") && !(i.toString().contains("air"))) {
                armorMaxDuraList.add(i.getMaxDamage());
                armorDuraAverage += i.getMaxDamage() - i.getDamage();
                armorCount++;
            }
        }

        if (armorCount == 0 || !(inputArmorStack.getEnchantments().toString().contains("mending")) || inputArmorStack.toString().contains("air")) {
            return "";
        }

        int maxHealGoal = (int)(Math.floor(Collections.min(armorMaxDuraList)/10.0)*10);
        armorDuraAverage /= armorCount;
        int healDura = inventory.count(Items.EXPERIENCE_BOTTLE)*14;
        int goalValue = Math.min(armorDuraAverage+healDura/armorCount,maxHealGoal);

        return "/"+goalValue;
    }

    public static String ifZeroEmptyString(int inputInt) {
        if (inputInt == 0) {
            return "";
        } else {
            return String.valueOf(inputInt);
        }
    }
}
