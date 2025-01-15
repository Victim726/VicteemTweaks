package gay.plat.victeemtweaks;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import eu.midnightdust.lib.config.MidnightConfig;
import gay.plat.victeemtweaks.config.VicteemTweaksConfig;
import gay.plat.victeemtweaks.itemrenamer.ItemNameManager;
import gay.plat.victeemtweaks.util.PlayerUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class VicteemTweaks implements ClientModInitializer {
	public static final String MODID = "victeemtweaks";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("ClientInit");

		ItemNameManager.prepareConfigFile();
		ItemNameManager.loadConfigFile();

		MinecraftClient client = MinecraftClient.getInstance();

		ClientCommandRegistrationCallback.EVENT.register((callback, access) -> callback.register(ClientCommandManager.literal("config")
				.then(ClientCommandManager.literal("load")
						.executes(context -> {
							ItemNameManager.loadConfigFile();
							MinecraftClient.getInstance().player.sendMessage(Text.literal("Successfully loaded config from files."), false);
							return 1;
						})
				)
				.then(ClientCommandManager.literal("write")
						.executes(context -> {
							ItemNameManager.writeConfigFile();
							MinecraftClient.getInstance().player.sendMessage(Text.literal("Successfully written config to files."), false);
							return 1;
						})
				)
		));

		ClientCommandRegistrationCallback.EVENT.register((callback, access) -> callback.register(ClientCommandManager.literal("itemrenamer")
				.then(ClientCommandManager.literal("create")
						.then(ClientCommandManager.argument("username", StringArgumentType.string())
								.then(ClientCommandManager.argument("itemType", ItemStackArgumentType.itemStack(access))
										.then(ClientCommandManager.argument("itemName", StringArgumentType.string())
												.executes(context -> {
													assert client.player != null;
													final String username = StringArgumentType.getString(context, "username");
													final String itemType = ItemStackArgumentType.getItemStackArgument(context, "itemType").getItem().toString();
													final String itemName = StringArgumentType.getString(context, "itemName").replace("_"," ");
													final String uuid = PlayerUtil.getUUID(username);
													if (uuid == null) {
														client.player.sendMessage(Text.literal("Invalid username inputted."), false);
														return 1;
													}

													HashMap<String,String> newItemNameMap = ItemNameManager.get(uuid);
													newItemNameMap.put(itemType,itemName);
													ItemNameManager.set(uuid,newItemNameMap);
													ItemNameManager.writeConfigFile();

													client.player.sendMessage(Text.literal(String.format("Successfully added item name %s for item %s for player %s to files",itemName,itemType,username)), false);
													return 1;
												})
										)
								)
						)
				)
				.then(ClientCommandManager.literal("remove")
						.then(ClientCommandManager.argument("username", StringArgumentType.string())
								.executes(context -> {
									assert client.player != null;
									final String username = StringArgumentType.getString(context, "username");
									final String uuid = PlayerUtil.getUUID(username);
									if (uuid == null) {
										client.player.sendMessage(Text.literal("Invalid username inputted."), false);
										return 1;
									}

									HashMap<String,String> newItemNameMap = ItemNameManager.get(uuid);
									newItemNameMap.clear();
									ItemNameManager.set(uuid,newItemNameMap);
									ItemNameManager.writeConfigFile();
									client.player.sendMessage(Text.literal(String.format("Successfully cleared %s from files",username)), false);
									return 1;
								})
								.then(ClientCommandManager.argument("itemType", StringArgumentType.string())
										.executes(context -> {
											assert client.player != null;
											final String username = StringArgumentType.getString(context, "username");
											final String itemType = StringArgumentType.getString(context, "itemType");
											final String uuid = PlayerUtil.getUUID(username);
											if (uuid == null) {
												client.player.sendMessage(Text.literal("Invalid username inputted."), false);
												return 1;
											}

											if (ItemNameManager.get(uuid).get(itemType) == null) {
												client.player.sendMessage(Text.literal("There is nothing to remove."), false);
												return 1;
											}

											HashMap<String,String> newItemNameMap = ItemNameManager.get(uuid);
											newItemNameMap.remove(itemType);
											ItemNameManager.set(uuid,newItemNameMap);
											ItemNameManager.writeConfigFile();
											client.player.sendMessage(Text.literal(String.format("Successfully removed item name for item %s for player %s from files",itemType,username)), false);
											return 1;
										})
								)
						)
				)
				.then(ClientCommandManager.literal("get")
						.then(ClientCommandManager.argument("username", StringArgumentType.string())
								.executes(context -> {
									assert client.player != null;
									final String username = StringArgumentType.getString(context, "username");
									final String uuid = PlayerUtil.getUUID(username);
									if (uuid == null) {
										client.player.sendMessage(Text.literal("Invalid username inputted."), false);
										return 1;
									}
									if (ItemNameManager.get(uuid).isEmpty()) {
										client.player.sendMessage(Text.literal("Player not in config."), false);
										return 1;
									}
									client.player.sendMessage(Text.literal(ItemNameManager.get(uuid).toString()), false);
									return 1;
								})
								.then(ClientCommandManager.argument("itemType", StringArgumentType.string())
										.executes(context -> {
											assert client.player != null;
											final String username = StringArgumentType.getString(context, "username");
											final String uuid = PlayerUtil.getUUID(username);
											final String itemType = StringArgumentType.getString(context, "itemType");
											if (uuid == null) {
												client.player.sendMessage(Text.literal("Invalid username inputted."), false);
												return 1;
											}
											if (ItemNameManager.get(uuid).isEmpty()) {
												client.player.sendMessage(Text.literal("Player not in config."), false);
												return 1;
											}
											if (ItemNameManager.get(uuid).get(itemType) != null) {
												client.player.sendMessage(Text.literal(ItemNameManager.get(uuid).get(itemType)), false);
											} else {
												client.player.sendMessage(Text.literal("Player itemList does not contain "+itemType), false);
											}
											return 1;
										})
								)
						)
				)
				.then(ClientCommandManager.literal("copy")
						.then(ClientCommandManager.argument("usernameCopyFrom", StringArgumentType.string())
								.then(ClientCommandManager.argument("usernameCopyTo", StringArgumentType.string())
										.executes(context -> {
											assert client.player != null;
											final String usernameCopyFrom = StringArgumentType.getString(context, "usernameCopyFrom");
											final String usernameCopyTo = StringArgumentType.getString(context, "usernameCopyTo");
											final String uuidCopyFrom = PlayerUtil.getUUID(usernameCopyFrom);
											final String uuidCopyTo = PlayerUtil.getUUID(usernameCopyTo);
											if (uuidCopyFrom == null) {
												client.player.sendMessage(Text.literal("Invalid usernameCopyFrom inputted."), false);
												return 1;
											}
											if (uuidCopyTo == null) {
												client.player.sendMessage(Text.literal("Invalid usernameCopyTo inputted."), false);
												return 1;
											}
											if (ItemNameManager.get(uuidCopyFrom).isEmpty()) {
												client.player.sendMessage(Text.literal(String.format("Player %s not in config.",usernameCopyFrom)), false);
												return 1;
											}

											ItemNameManager.set(uuidCopyTo,ItemNameManager.get(uuidCopyFrom));
											ItemNameManager.writeConfigFile();
											client.player.sendMessage(Text.literal(String.format("Successfully copied nameData from %s to %s in config.",usernameCopyFrom, usernameCopyTo)), false);
											return 1;
										})
								)
						)
				)
		));

		ClientCommandRegistrationCallback.EVENT.register((callback, access) -> callback.register(ClientCommandManager.literal("setarmor")
				.then(ClientCommandManager.argument("armorMaterialType", StringArgumentType.string())
						.then(ClientCommandManager.argument("templateType", StringArgumentType.string())
								.then(ClientCommandManager.argument("trimMaterialType", StringArgumentType.string())
										.then(ClientCommandManager.argument("enchantmentGlint", BoolArgumentType.bool())
												.executes(context -> {
													final String armorMaterialType = StringArgumentType.getString(context, "armorMaterialType");
													final String templateType = StringArgumentType.getString(context, "templateType");
													final String trimMaterialType = StringArgumentType.getString(context, "trimMaterialType");
													final boolean enchantmentGlint = BoolArgumentType.getBool(context, "enchantmentGlint");

													ClientPlayerEntity player = context.getSource().getPlayer();
													player.networkHandler.sendChatCommand(String.format("item replace entity @p armor.head with %s_helmet[trim={pattern:%s,material:%s},enchantment_glint_override=%s]",armorMaterialType,templateType,trimMaterialType,enchantmentGlint));
													player.networkHandler.sendChatCommand(String.format("item replace entity @p armor.chest with %s_chestplate[trim={pattern:%s,material:%s},enchantment_glint_override=%s]",armorMaterialType,templateType,trimMaterialType,enchantmentGlint));
													player.networkHandler.sendChatCommand(String.format("item replace entity @p armor.legs with %s_leggings[trim={pattern:%s,material:%s},enchantment_glint_override=%s]",armorMaterialType,templateType,trimMaterialType,enchantmentGlint));
													player.networkHandler.sendChatCommand(String.format("item replace entity @p armor.feet with %s_boots[trim={pattern:%s,material:%s},enchantment_glint_override=%s]",armorMaterialType,templateType,trimMaterialType,enchantmentGlint));

													return 1;
												})
										)
								)
						)
				)
		));


		MidnightConfig.init(MODID, VicteemTweaksConfig.class);
	}

	public static Identifier identiferOf(String id) {
		return Identifier.of(MODID,id);
	}
}