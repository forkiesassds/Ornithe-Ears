//? if <1.8 {
/*package me.icanttellyou.ornitheears.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.render.HeldItemRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @WrapOperation(
        method = "renderHand",
        at = @At(
            value = "INVOKE",
            target =
                    //? if >=1.4 {
                    "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;renderPlayerRightHandModel(Lnet/minecraft/entity/living/player/PlayerEntity;)V"
                    //?} else {
                    /^"Lnet/minecraft/client/render/entity/PlayerEntityRenderer;renderPlayerRightHandModel()V"
                    ^///?}
        )
    )
    private void patchRenderHand(PlayerEntityRenderer instance, /^? if >=1.4 {^/ net.minecraft.entity.living.player.PlayerEntity player,/^?}^/ Operation<Void> original) {
        GL11.glDisable(GL11.GL_CULL_FACE);
        original.call(instance /^? if >=1.4 {^/ , player/^?}^/);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }
}
*///?}