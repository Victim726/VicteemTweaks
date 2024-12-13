package gay.plat.victeemtweaks;

import com.mojang.brigadier.arguments.StringArgumentType;
import gay.plat.victeemtweaks.config.customsweepparticle.SweepParticleData;
import gay.plat.victeemtweaks.config.playeritemmodel.PlayerItemModelData;
import gay.plat.victeemtweaks.particles.VicteemTweaksParticles;
import gay.plat.victeemtweaks.util.ColorUtil;
import gay.plat.victeemtweaks.util.PlayerUtil;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VicteemTweaks implements ClientModInitializer {
	public static final String MOD_ID = "victeemtweaks";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("ModInit");

		VicteemTweaksParticles.registerParticles();
		PlayerItemModelData.loadJsonData();

		ClientLifecycleEvents.CLIENT_STARTED.register((client) -> SweepParticleData.loadJsonData());

		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_ALT, SweepAttackParticle.Factory::new);

		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_1, SweepAttackParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_1_ALT, SweepAttackParticle.Factory::new);

		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_2, SweepAttackParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_2_ALT, SweepAttackParticle.Factory::new);

		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_3, SweepAttackParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_3_ALT, SweepAttackParticle.Factory::new);

		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_4, SweepAttackParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_4_ALT, SweepAttackParticle.Factory::new);

		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_5, SweepAttackParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_5_ALT, SweepAttackParticle.Factory::new);

		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_6, SweepAttackParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_6_ALT, SweepAttackParticle.Factory::new);

		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_7, SweepAttackParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(VicteemTweaksParticles.SWEEP_ATTACK_7_ALT, SweepAttackParticle.Factory::new);

		ClientCommandRegistrationCallback.EVENT.register((callback, a) -> callback.register(ClientCommandManager.literal("vtping")
			.executes(context -> {
				MinecraftClient client = MinecraftClient.getInstance();
				assert client.player != null;
				final String uuid = client.player.getUuidAsString();
				client.player.sendMessage(Text.literal("Your ping: ").append(Text.literal(String.valueOf(PlayerUtil.getPing(uuid))).withColor(ColorUtil.getPingColor(PlayerUtil.getPing(uuid))).append(Text.literal("ms").withColor(0x00FFFFFF))));
				return 1;
			})
			.then(ClientCommandManager.argument("username", StringArgumentType.string())
				.executes(context -> {
					MinecraftClient client = MinecraftClient.getInstance();
					assert client.player != null;
					final String username = StringArgumentType.getString(context, "username");
					List<String> serverNameList = new ArrayList<>();
					for (String i : Arrays.stream(Objects.requireNonNull(client.getServer()).getPlayerNames()).toList()) {
						serverNameList.add(i.toLowerCase());
					}
					if (!(serverNameList.contains(username.toLowerCase()))) {
						client.player.sendMessage(Text.literal("Player not on server."));
						return 1;
					}
					final String uuid = client.getServer().getPlayerManager().getPlayer(username).getUuidAsString();
					client.player.sendMessage(Text.literal("Ping of ").append(client.getServer().getPlayerManager().getPlayer(username).getName()).append(": ").append(Text.literal(String.valueOf(PlayerUtil.getPing(uuid))).withColor(ColorUtil.getPingColor(PlayerUtil.getPing(uuid))).append(Text.literal("ms").withColor(0x00FFFFFF))));
					return 1;
				})
			)
		));

		ClientCommandRegistrationCallback.EVENT.register((callback, a) -> callback.register(ClientCommandManager.literal("config")
			.then(ClientCommandManager.literal("load")
				.executes(context -> {
					PlayerItemModelData.loadJsonData();
					SweepParticleData.loadJsonData();
					MinecraftClient.getInstance().player.sendMessage(Text.literal("Successfully loaded config from files."), false);
					return 1;
				})
			)
			.then(ClientCommandManager.literal("write")
				.executes(context -> {
					PlayerItemModelData.writeJsonData();
					MinecraftClient.getInstance().player.sendMessage(Text.literal("Successfully written config to files."), false);
					return 1;
				})
			)
		));

		ClientCommandRegistrationCallback.EVENT.register((callback, a) -> callback.register(ClientCommandManager.literal("playeritemmodel")
			.then(ClientCommandManager.literal("create")
				.then(ClientCommandManager.argument("username", StringArgumentType.string())
					.then(ClientCommandManager.argument("itemType", StringArgumentType.string())
						.then(ClientCommandManager.argument("itemName", StringArgumentType.string())
							.executes(context -> {
								MinecraftClient client = MinecraftClient.getInstance();
								assert client.player != null;
								final String username = StringArgumentType.getString(context, "username");
								final String itemType = StringArgumentType.getString(context, "itemType");
								final String itemName = StringArgumentType.getString(context, "itemName").replace("_"," ");
								final String uuid = PlayerUtil.getUUID(username.toLowerCase());
								if (uuid == null) {
									client.player.sendMessage(Text.literal("Invalid username inputted."), false);
									return 1;
								}

								PlayerItemModelData.addNamePlayerItem(uuid,itemType,itemName);

								PlayerItemModelData.writeJsonData();
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
						MinecraftClient client = MinecraftClient.getInstance();
						assert client.player != null;
						final String username = StringArgumentType.getString(context, "username");
						final String uuid = PlayerUtil.getUUID(username.toLowerCase());
						if (uuid == null) {
							client.player.sendMessage(Text.literal("Invalid username inputted."), false);
							return 1;
						}

						PlayerItemModelData.clearNamePlayerItem(uuid);

						PlayerItemModelData.writeJsonData();
						client.player.sendMessage(Text.literal(String.format("Successfully cleared %s from files",username)), false);
						return 1;
					})
					.then(ClientCommandManager.argument("itemType", StringArgumentType.string())
						.executes(context -> {
							MinecraftClient client = MinecraftClient.getInstance();
							assert client.player != null;
							final String username = StringArgumentType.getString(context, "username");
							final String itemType = StringArgumentType.getString(context, "itemType");
							final String uuid = PlayerUtil.getUUID(username.toLowerCase());
							if (uuid == null) {
								client.player.sendMessage(Text.literal("Invalid username inputted."), false);
								return 1;
							}

							if (PlayerItemModelData.playerNameMap.get(uuid) == null || PlayerItemModelData.playerNameMap.get(uuid).itemNameMap.get(itemType) == null) {
								client.player.sendMessage(Text.literal("There is nothing to remove or Invalid itemType inputted."), false);
								return 1;
							}

							PlayerItemModelData.removeNamePlayerItem(uuid,itemType);

							PlayerItemModelData.writeJsonData();
							client.player.sendMessage(Text.literal(String.format("Successfully removed item name for item %s for player %s from files",itemType,username)), false);
							return 1;
						})
					)
				)
			)
			.then(ClientCommandManager.literal("get")
				.then(ClientCommandManager.argument("username", StringArgumentType.string())
					.executes(context -> {
						MinecraftClient client = MinecraftClient.getInstance();
						assert client.player != null;
						final String username = StringArgumentType.getString(context, "username");
						final String uuid = PlayerUtil.getUUID(username.toLowerCase());
						if (uuid == null) {
							client.player.sendMessage(Text.literal("Invalid username inputted."), false);
							return 1;
						}
						if (PlayerItemModelData.playerNameMap.get(uuid) == null) {
							client.player.sendMessage(Text.literal("Player not in config."), false);
							return 1;
						}
						client.player.sendMessage(Text.literal(PlayerItemModelData.playerNameMap.get(uuid).getItemNameMapString()), false);
						return 1;
					})
					.then(ClientCommandManager.argument("itemType", StringArgumentType.string())
						.executes(context -> {
							MinecraftClient client = MinecraftClient.getInstance();
							assert client.player != null;
							final String username = StringArgumentType.getString(context, "username");
							final String uuid = PlayerUtil.getUUID(username.toLowerCase());
							final String itemType = StringArgumentType.getString(context, "itemType");
							if (uuid == null) {
								client.player.sendMessage(Text.literal("Invalid username inputted."), false);
								return 1;
							}
							if (PlayerItemModelData.playerNameMap.get(uuid) == null) {
								client.player.sendMessage(Text.literal("Player not in config."), false);
								return 1;
							}
							if (PlayerItemModelData.playerNameMap.get(uuid).itemNameMap.get(itemType) != null) {
								client.player.sendMessage(Text.literal(PlayerItemModelData.playerNameMap.get(uuid).itemNameMap.get(itemType)), false);
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
							MinecraftClient client = MinecraftClient.getInstance();
							assert client.player != null;
							final String usernameCopyFrom = StringArgumentType.getString(context, "usernameCopyFrom");
							final String usernameCopyTo = StringArgumentType.getString(context, "usernameCopyTo");
							final String uuidCopyFrom = PlayerUtil.getUUID(usernameCopyFrom.toLowerCase());
							final String uuidCopyTo = PlayerUtil.getUUID(usernameCopyTo.toLowerCase());
							if (uuidCopyFrom == null) {
								client.player.sendMessage(Text.literal("Invalid usernameCopyFrom inputted."), false);
								return 1;
							}
							if (uuidCopyTo == null) {
								client.player.sendMessage(Text.literal("Invalid usernameCopyTo inputted."), false);
								return 1;
							}
							if (PlayerItemModelData.playerNameMap.get(uuidCopyFrom) == null) {
								client.player.sendMessage(Text.literal(String.format("Player %s not in config.",usernameCopyFrom)), false);
								return 1;
							}
							PlayerItemModelData.copyNamePlayerItem(uuidCopyFrom,uuidCopyTo);
							client.player.sendMessage(Text.literal(String.format("Successfully copied nameData from %s to %s in config.",usernameCopyFrom, usernameCopyTo)), false);
							return 1;
						})
					)
				)
			)
		));
		ClientCommandRegistrationCallback.EVENT.register((callback, a) -> callback.register(ClientCommandManager.literal("testthing")
			.executes(context -> {
				MinecraftClient.getInstance().player.sendMessage(Text.literal("Successfully testthinged."), false);
				return 1;
			})
		));
	}
}