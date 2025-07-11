//? if >=1.9 {
/*package me.icanttellyou.ornitheears.mixin;

import com.unascribed.ears.common.EarsCommon;
import me.icanttellyou.ornitheears.OrnitheEars;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.render.entity.layer.ElytraLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin {
    @Inject(method =
            //? if >= 1.11 {
            /^"render",
            ^///?} else {
            "render(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;FFFFFFF)V",
            //?}
            at = @At("HEAD"), cancellable = true)
    private void patchRender(
            //? if >= 1.11 {
            /^net.minecraft.entity.living.LivingEntity entity,
            ^///?} else {
            ClientPlayerEntity entity,
            //?}
            float f, float g, float h, float i, float j, float k, float l, CallbackInfo ci) {
        if (entity instanceof ClientPlayerEntity && EarsCommon.shouldSuppressElytra(OrnitheEars.getEarsFeatures((ClientPlayerEntity) entity)))
            ci.cancel();
    }
}
*///?}
