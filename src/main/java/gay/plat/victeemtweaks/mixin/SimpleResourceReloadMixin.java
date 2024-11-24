package gay.plat.victeemtweaks.mixin;

import gay.plat.victeemtweaks.VicteemTweaks;
import gay.plat.victeemtweaks.config.customsweepparticle.SweepParticleData;
import gay.plat.victeemtweaks.config.playeritemmodel.PlayerItemModelData;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.resource.*;
import net.minecraft.util.Unit;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(SimpleResourceReload.class)
public abstract class SimpleResourceReloadMixin {
    @Inject(at = @At("HEAD"), method = "start")
    private static void onInventoryTick(ResourceManager manager, List<ResourceReloader> reloaders, Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, boolean profiled, CallbackInfoReturnable<ResourceReload> cir) {
        SweepParticleData.loadJsonData();
    }
}
