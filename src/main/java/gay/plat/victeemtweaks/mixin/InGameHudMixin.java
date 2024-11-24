package gay.plat.victeemtweaks.mixin;

import gay.plat.victeemtweaks.util.ColorUtil;
import gay.plat.victeemtweaks.util.DuraUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

	@Unique
	ItemStack helmetStack;
	@Unique
	int helmetDura;
	@Unique
	ItemStack chestplateStack;
	@Unique
	int chestplateDura;
	@Unique
	ItemStack leggingsStack;
	@Unique
	int leggingsDura;
	@Unique
	ItemStack bootsStack;
	@Unique
	int bootsDura;

	@Shadow public abstract TextRenderer getTextRenderer();

	@Shadow @Final private MinecraftClient client;

	@Inject(at = @At("HEAD"), method = "render")
	private void init(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		PlayerEntity player = this.client.player;
		if (player == null || !(player.isPlayer())) {
			return;
		}

		helmetStack = player.getInventory().armor.get(3);
		helmetDura = helmetStack.getMaxDamage() - helmetStack.getDamage();
		chestplateStack = player.getInventory().armor.get(2);
		chestplateDura = chestplateStack.getMaxDamage() - chestplateStack.getDamage();
		leggingsStack = player.getInventory().armor.get(1);
		leggingsDura = leggingsStack.getMaxDamage() - leggingsStack.getDamage();
		bootsStack = player.getInventory().armor.get(0);
		bootsDura = bootsStack.getMaxDamage() - bootsStack.getDamage();

		List<ItemStack> armorStackList = new ArrayList<>();
		armorStackList.add(helmetStack);
		armorStackList.add(chestplateStack);
		armorStackList.add(leggingsStack);
		armorStackList.add(bootsStack);

		List<Integer> list0 = new ArrayList<>();
		list0.add(0);
		list0.add(0);
		List<Integer> list1 = new ArrayList<>();
		list1.add(context.getScaledWindowWidth()-64);
		list1.add(0);
		List<Integer> list2 = new ArrayList<>();
		list2.add(0);
		list2.add(context.getScaledWindowHeight()-64);
		List<Integer> list3 = new ArrayList<>();
		list3.add(context.getScaledWindowWidth()-64);
		list3.add(context.getScaledWindowHeight()-64);

		Map<Integer,List<Integer>> offsetMap = new HashMap<>();
		offsetMap.put(0,list0);
		offsetMap.put(1,list1);
		offsetMap.put(2,list2);
		offsetMap.put(3,list3);

		int presetSelection = 3;

		int xOffset = offsetMap.get(presetSelection).get(0);
		int yOffset = offsetMap.get(presetSelection).get(1);

		for (int i = 0; i < 4; i++) {
			ItemStack armorStack = armorStackList.get(i);
			int armorDura = armorStack.getMaxDamage() - armorStack.getDamage();
			context.drawItem(armorStack, xOffset, yOffset+i*16);
			if (!armorStack.getComponentChanges().toString().contains("minecraft:unbreakable")) {
				context.drawTextWithShadow(getTextRenderer(), DuraUtil.ifZeroEmptyString(armorDura)+DuraUtil.getFunctionalMaxDuraString(player,armorStack), xOffset+16, yOffset+4+i*16, ColorUtil.getDuraColor(armorStack,player));
			} else {
				context.drawTextWithShadow(getTextRenderer(), "∞", xOffset+16, yOffset+4+i*16, ColorUtil.getDuraColor(armorStack,player));
			}


		}
	}
}