package gay.plat.victeemtweaks.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ColorUtil {
    public static int getDuraColor(ItemStack stack, PlayerEntity player) {
        if (DuraUtil.getFunctionalMaxDura(player,stack) == 0) {
            return 0xFFFFFF;
        }
        int stackDura = stack.getMaxDamage() - stack.getDamage();
        if (stackDura >= DuraUtil.getFunctionalMaxDura(player,stack)) {
            return 0x00FF00;
        }
        return 0xFFFFFF;
    }

    public static int getPingColor(int ping) {
        if (ping > 100) {
            return 0xAA0000; //
        } else if (ping > 70) {
            return 0xFF0000; //
        } else if (ping > 40) {
            return 0xCCFF00; //
        } else if (ping > 0) {
            return 0x00FF00; //
        } else {
            return 0x000000; //
        }
    }
}
