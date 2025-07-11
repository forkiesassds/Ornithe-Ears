//? if <1.9 {
package me.icanttellyou.ornitheears.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.unascribed.ears.common.util.EarsStorage;
import me.icanttellyou.ornitheears.OrnitheEars;
import net.minecraft.client.render.model.ModelPart;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void patchRender$1(float scale, CallbackInfo ci) {
        if (EarsStorage.get(this, OrnitheEars.FORCE_TRANSLUCENT)) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void patchRender$2(float scale, CallbackInfo ci) {
        if (EarsStorage.get(this, OrnitheEars.FORCE_TRANSLUCENT)) {
            GlStateManager.disableBlend();
        }
    }
}
//?}