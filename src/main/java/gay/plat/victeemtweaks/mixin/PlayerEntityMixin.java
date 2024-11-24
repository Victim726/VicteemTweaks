package gay.plat.victeemtweaks.mixin;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import gay.plat.victeemtweaks.VicteemTweaksClient;
import gay.plat.victeemtweaks.config.customsweepparticle.SweepParticleClass;
import gay.plat.victeemtweaks.config.customsweepparticle.SweepParticleData;
import gay.plat.victeemtweaks.config.playeritemmodel.PlayerItemModelData;
import gay.plat.victeemtweaks.particles.VicteemTweaksParticles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Type;
import java.util.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow public abstract Arm getMainArm();

    @Shadow public abstract PlayerInventory getInventory();

    @Inject(at = @At("HEAD"), method = "spawnSweepAttackParticles", cancellable = true)
    private void onSweepAttackParticleSpawn(CallbackInfo ci) {
        if (getMainArm() == Arm.LEFT) {
            PlayerEntity player = this.getInventory().player;
            double d = -MathHelper.sin(player.getYaw() * 0.017453292F);
            double e = MathHelper.cos(player.getYaw() * 0.017453292F);
            boolean spawnedParticle = false;
            for (int i = 0; i < SweepParticleData.weightedList.size(); i++) {
                Collections.shuffle(SweepParticleData.weightedList.get(i));
                for (int j = 0; j < SweepParticleData.weightedList.get(i).size(); j++) {
                    ItemStack stack = player.getMainHandStack();
                    SweepParticleClass sweepParticleClass = SweepParticleData.weightedList.get(i).get(j);
                    boolean componentSatisfaction = true;
                    boolean enchantmentComponentSatisfaction = true;
                    boolean customnameComponentSatisfaction = true;
                    boolean damagemaxComponentSatisfaction = true;
                    boolean damageminComponentSatisfaction = true;

                    if (sweepParticleClass.conditions != null && !sweepParticleClass.conditions.isEmpty()) {
                        componentSatisfaction = false;
                        if (sweepParticleClass.conditions.get("enchantments") != null) {
                            enchantmentComponentSatisfaction = false;
                            List<Map<String,Integer>> conditionalEnchantmentList = new ArrayList<>();
                            String modifiedEnchantmentString = stack.getEnchantments().getEnchantmentEntries().toString().replace("Reference{ResourceKey[minecraft:enchantment / minecraft:","").replace("=Enchantment","").replace("}=>","genricstring=").replace(" ","").replace("=",":").replace("]","genericstring").toLowerCase();
                            Type enchantmentMapType = new TypeToken<Map<String, Integer>>() {}.getType();
                            Map<String,Integer> modifiedEnchantmentJson = new Gson().fromJson(modifiedEnchantmentString,enchantmentMapType);
                            Map<String,Integer> updatedEnchantmentJson = new HashMap<>();

                            for (String k:modifiedEnchantmentJson.keySet()) {
                                updatedEnchantmentJson.put(k.replaceAll("(genericstring)[^&]*(genricstring)", "").toLowerCase(),modifiedEnchantmentJson.get(k));
                            }
                            for (String string:sweepParticleClass.conditions.get("enchantments").getAsJsonObject().keySet()) {
                                Map<String,Integer> enchantment = new HashMap<>();
                                enchantment.put(string,sweepParticleClass.conditions.get("enchantments").getAsJsonObject().get(string).getAsInt());
                                conditionalEnchantmentList.add(enchantment);
                            }
                            int fullfilledConditions = 0;
                            for (Map<String,Integer> conditionalEnchantment : conditionalEnchantmentList) {
                                if (updatedEnchantmentJson.get(conditionalEnchantment.keySet().stream().toList().getFirst()) != null && Objects.equals(updatedEnchantmentJson.get(conditionalEnchantment.keySet().stream().toList().getFirst()), conditionalEnchantment.get(conditionalEnchantment.keySet().stream().toList().getFirst()))) {
                                    fullfilledConditions++;
                                }
                            }
                            if (fullfilledConditions == conditionalEnchantmentList.size()) {
                                enchantmentComponentSatisfaction = true;
                            }
                        }
                        if (sweepParticleClass.conditions.get("custom_name") != null) {
                            customnameComponentSatisfaction = false;
                            if (stack.getName().getString().equalsIgnoreCase(sweepParticleClass.conditions.get("custom_name").getAsString())) {
                                customnameComponentSatisfaction = true;
                            }
                        }
                        if (sweepParticleClass.conditions.get("damagemax") != null) {
                            damagemaxComponentSatisfaction = false;
                            if (stack.getDamage() >= sweepParticleClass.conditions.get("damagemax").getAsInt()) {
                                damagemaxComponentSatisfaction = true;
                            }
                        }
                        if (sweepParticleClass.conditions.get("damagemin") != null) {
                            damageminComponentSatisfaction = false;
                            if (stack.getDamage() <= sweepParticleClass.conditions.get("damagemin").getAsInt()) {
                                damageminComponentSatisfaction = true;
                            }
                        }
                        if (enchantmentComponentSatisfaction && customnameComponentSatisfaction && damagemaxComponentSatisfaction && damageminComponentSatisfaction) {
                            componentSatisfaction = true;
                        }
                    }
                    if (componentSatisfaction && (sweepParticleClass.itemID == null || (sweepParticleClass.itemID.contains(player.getMainHandStack().getItem().toString().replace("minecraft:","")) || sweepParticleClass.itemID.contains(player.getMainHandStack().getItem().toString())))) {
                        if (player.getWorld() instanceof ServerWorld && !spawnedParticle) {
                            //VicteemTweaks.LOGGER.info(sweepParticleClass.sweepParticle+"_alt");
                            //System.out.println(VicteemTweaksParticles.sweepParticleMap.get(sweepParticleClass.sweepParticle+"_alt").toString());
                            ((ServerWorld) player.getWorld()).spawnParticles(VicteemTweaksParticles.sweepParticleMap.get(sweepParticleClass.sweepParticle+"_alt"), player.getX() + d, player.getBodyY(0.5), player.getZ() + e, 0, d, 0.0, e, 0.0);
                            spawnedParticle = true;
                            break;
                        }
                    }
                }
            }
            if (player.getWorld() instanceof ServerWorld && !spawnedParticle) {
                ((ServerWorld) player.getWorld()).spawnParticles(VicteemTweaksParticles.SWEEP_ATTACK_ALT, player.getX() + d, player.getBodyY(0.5), player.getZ() + e, 0, d, 0.0, e, 0.0);
                spawnedParticle = true;
            }
            if (spawnedParticle) {
                ci.cancel();
            }
        } else if (getMainArm() == Arm.RIGHT) {
            PlayerEntity player = this.getInventory().player;
            double d = -MathHelper.sin(player.getYaw() * 0.017453292F);
            double e = MathHelper.cos(player.getYaw() * 0.017453292F);
            boolean spawnedParticle = false;
            for (int i = 0; i < SweepParticleData.weightedList.size(); i++) {
                Collections.shuffle(SweepParticleData.weightedList.get(i));
                for (int j = 0; j < SweepParticleData.weightedList.get(i).size(); j++) {
                    ItemStack stack = player.getMainHandStack();
                    SweepParticleClass sweepParticleClass = SweepParticleData.weightedList.get(i).get(j);
                    boolean componentSatisfaction = true;
                    boolean enchantmentComponentSatisfaction = true;
                    boolean customnameComponentSatisfaction = true;
                    boolean damagemaxComponentSatisfaction = true;
                    boolean damageminComponentSatisfaction = true;

                    if (sweepParticleClass.conditions != null && !sweepParticleClass.conditions.isEmpty()) {
                        componentSatisfaction = false;
                        if (sweepParticleClass.conditions.get("enchantments") != null) {
                            enchantmentComponentSatisfaction = false;
                            List<Map<String, Integer>> conditionalEnchantmentList = new ArrayList<>();
                            String modifiedEnchantmentString = stack.getEnchantments().getEnchantmentEntries().toString().replace("Reference{ResourceKey[minecraft:enchantment / minecraft:", "").replace("=Enchantment", "").replace("}=>", "genricstring=").replace(" ", "").replace("=", ":").replace("]", "genericstring").toLowerCase();
                            Type enchantmentMapType = new TypeToken<Map<String, Integer>>() {
                            }.getType();
                            Map<String, Integer> modifiedEnchantmentJson = new Gson().fromJson(modifiedEnchantmentString, enchantmentMapType);
                            Map<String, Integer> updatedEnchantmentJson = new HashMap<>();

                            for (String k : modifiedEnchantmentJson.keySet()) {
                                updatedEnchantmentJson.put(k.replaceAll("(genericstring)[^&]*(genricstring)", "").toLowerCase(), modifiedEnchantmentJson.get(k));
                            }
                            for (String string : sweepParticleClass.conditions.get("enchantments").getAsJsonObject().keySet()) {
                                Map<String, Integer> enchantment = new HashMap<>();
                                enchantment.put(string, sweepParticleClass.conditions.get("enchantments").getAsJsonObject().get(string).getAsInt());
                                conditionalEnchantmentList.add(enchantment);
                            }
                            int fullfilledConditions = 0;
                            for (Map<String, Integer> conditionalEnchantment : conditionalEnchantmentList) {
                                if (updatedEnchantmentJson.get(conditionalEnchantment.keySet().stream().toList().getFirst()) != null && Objects.equals(updatedEnchantmentJson.get(conditionalEnchantment.keySet().stream().toList().getFirst()), conditionalEnchantment.get(conditionalEnchantment.keySet().stream().toList().getFirst()))) {
                                    fullfilledConditions++;
                                }
                            }
                            if (fullfilledConditions == conditionalEnchantmentList.size()) {
                                enchantmentComponentSatisfaction = true;
                            }
                        }
                        if (sweepParticleClass.conditions.get("custom_name") != null) {
                            customnameComponentSatisfaction = false;
                            if (stack.getName().getString().equalsIgnoreCase(sweepParticleClass.conditions.get("custom_name").getAsString())) {
                                customnameComponentSatisfaction = true;
                            }
                        }
                        if (sweepParticleClass.conditions.get("damagemax") != null) {
                            damagemaxComponentSatisfaction = false;
                            if (stack.getDamage() >= sweepParticleClass.conditions.get("damagemax").getAsInt()) {
                                damagemaxComponentSatisfaction = true;
                            }
                        }
                        if (sweepParticleClass.conditions.get("damagemin") != null) {
                            damageminComponentSatisfaction = false;
                            if (stack.getDamage() <= sweepParticleClass.conditions.get("damagemin").getAsInt()) {
                                damageminComponentSatisfaction = true;
                            }
                        }
                        if (enchantmentComponentSatisfaction && customnameComponentSatisfaction && damagemaxComponentSatisfaction && damageminComponentSatisfaction) {
                            componentSatisfaction = true;
                        }
                    }
                    if (componentSatisfaction && (sweepParticleClass.itemID == null || (sweepParticleClass.itemID.contains(player.getMainHandStack().getItem().toString().replace("minecraft:","")) || sweepParticleClass.itemID.contains(player.getMainHandStack().getItem().toString())))) {
                        if (player.getWorld() instanceof ServerWorld && !spawnedParticle) {
                            //VicteemTweaks.LOGGER.info(sweepParticleClass.sweepParticle);
                            //System.out.println(VicteemTweaksParticles.sweepParticleMap.get(sweepParticleClass.sweepParticle).toString());
                            ((ServerWorld) player.getWorld()).spawnParticles(VicteemTweaksParticles.sweepParticleMap.get(sweepParticleClass.sweepParticle), player.getX() + d, player.getBodyY(0.5), player.getZ() + e, 0, d, 0.0, e, 0.0);
                            spawnedParticle = true;
                            break;
                        }
                    }
                }
            }
            if (spawnedParticle) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo ci) {
        final PlayerEntity player = getInventory().player;
        final String uuid = player.getUuidAsString();
        if (uuid == null) {
            return;
        }

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            String stackID = stack.getItem().toString().replace("minecraft:","");

            PlayerItemModelData.renameItem(stack,uuid,stackID);


            /*if (PlayerItemModelData.get(uuid).useItem("generic_sword")
                    && stack.getItem() instanceof SwordItem) {
                PlayerItemModelData.renameItem(stack,uuid,"generic_sword");
            } else if (PlayerItemModelData.get(uuid).useItem("netherite_sword")
                    && stack.getItem() == Items.NETHERITE_SWORD) {
                PlayerItemModelData.renameItem(stack,uuid,"netherite_sword");
            } else if (PlayerItemModelData.get(uuid).useItem("diamond_sword")
                    && stack.getItem() == Items.DIAMOND_SWORD) {
                PlayerItemModelData.renameItem(stack,uuid,"diamond_sword");
            } else if (PlayerItemModelData.get(uuid).useItem("generic_axe")
                    && stack.getItem() instanceof AxeItem) {
                PlayerItemModelData.renameItem(stack,uuid,"generic_axe");
            } else if (PlayerItemModelData.get(uuid).useItem("netherite_axe")
                    && stack.getItem() == Items.NETHERITE_AXE) {
                PlayerItemModelData.renameItem(stack,uuid,"netherite_axe");
            } else if (PlayerItemModelData.get(uuid).useItem("diamond_axe")
                    && stack.getItem() == Items.DIAMOND_AXE) {
                PlayerItemModelData.renameItem(stack,uuid,"diamond_axe");
            }*/
        }
        //player.getWorld().addImportantParticle(VicteemTweaksClient.GOLDEN_CHAIN,player.getX(),player.getY(),player.getZ(),0,0,0);
    }
}