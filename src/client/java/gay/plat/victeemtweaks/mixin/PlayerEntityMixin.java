package gay.plat.victeemtweaks.mixin;

import gay.plat.victeemtweaks.itemrenamer.ItemNameManager;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin{
	@Shadow public abstract PlayerInventory getInventory();

	@Unique
	AttributeModifiersComponent prevAttributeModifiersComponent = new AttributeModifiersComponent(new ArrayList<>(),false);

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

		prevAttributeModifiersComponent = player.getMainHandStack().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
	}

	@Inject(at = @At("HEAD"), method = "attack")
	private void onAttack(Entity target, CallbackInfo info) {
		final PlayerEntity player = (PlayerEntity)(Object)this;
		if (player.getWorld().isClient()) {
			AttributeModifiersComponent attributeModifiersComponent = player.getMainHandStack().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
			if (attributeModifiersComponent != prevAttributeModifiersComponent)
				player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(),1,1);
		}
	}
}