package me.icanttellyou.ornitheears.mixin;

import com.unascribed.ears.common.EarsCommon;
import com.unascribed.ears.common.debug.EarsLog;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.render.texture.SkinImageProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if <1.13 {
import com.llamalad7.mixinextras.sugar.Local;
import com.unascribed.ears.common.legacy.AWTEarsImage;
import com.unascribed.ears.common.util.EarsStorage;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.awt.image.BufferedImage;
//?}

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(SkinImageProcessor.class)
public abstract class SkinImageProcessorMixin {
    @Unique private static boolean ears$reentering;

    //? if <1.13 {
    @Shadow private int height;

    @Shadow protected abstract void setOpaque(int uMin, int vMin, int uMax, int vMax);

    @Inject(method = "process", at = @At("RETURN"))
    private void patchProcess(BufferedImage image, CallbackInfoReturnable<BufferedImage> cir, @Local BufferedImage image1) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "preprocessSkin({}, {}, {})", this, image, image1);
        EarsStorage.put(image1, EarsStorage.Key.ALFALFA, EarsCommon.preprocessSkin(new AWTEarsImage(image)));
    }
    //?}

    //? if >=1.13 {
    /*@Shadow
    private static void setOpaque(com.mojang.blaze3d.platform.NativeImage par1, int par2, int par3, int par4, int par5) {
        throw new IllegalStateException("Mixin failed");
    }
    *///?}

    @Inject(method = "setOpaque", at = @At("HEAD"), cancellable = true)
    private /*? if >=1.13 {*/ /*static *//*?}*/ void patchSetOpaque(
            //? if >=1.13
            /*com.mojang.blaze3d.platform.NativeImage image,*/
            int uMin, int vMin, int uMax, int vMax, CallbackInfo ci
    ) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "stripAlpha({}, {}, {}, {}, {}) reentering={}", /*? >= 1.13 {*/ /*image *//*?} else {*/ this /*?}*/, uMin, vMin, uMax, vMax, ears$reentering);
        if (ears$reentering) return;
        if (uMin == 0 && vMin == 0 && uMax == 32 && vMax == 16) {
            try {
                ears$reentering = true;
                //? if >= 1.13 {
                /*EarsCommon.carefullyStripAlpha((x1, y1, x2, y2) ->
                        setOpaque(image, x1, y1, x2, y2), image.getHeight() != 32);
                *///?} else {
                EarsCommon.carefullyStripAlpha(this::setOpaque, height != 32);
                //?}
            } finally {
                ears$reentering = false;
            }
        }
        ci.cancel();
    }
}