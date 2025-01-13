package gay.plat.victeemtweaks.mixin;

import gay.plat.victeemtweaks.itemrenamer.ItemNameManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow public abstract PlayerInventory getInventory();

	@Inject(at = @At("HEAD"), method = "tick")
	private void onTick(CallbackInfo info) {
		final PlayerEntity player = getInventory().player;
		final String uuid = player.getUuidAsString();
		if (uuid == null) {
			return;
		}

		for (int i = 0; i < player.getInventory().size(); i++) {
			ItemStack stack = player.getInventory().getStack(i);
			ItemNameManager.renameItemFromConfig(stack, uuid);
		}
	}
}