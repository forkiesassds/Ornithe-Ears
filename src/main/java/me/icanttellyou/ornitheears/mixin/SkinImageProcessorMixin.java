package me.icanttellyou.ornitheears.mixin;

import com.unascribed.ears.common.EarsCommon;
import com.unascribed.ears.common.debug.EarsLog;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.render.texture.SkinImageProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

//? if >=1.8 {
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
//?}

//? if <1.13 {
import com.unascribed.ears.common.legacy.AWTEarsImage;
import com.unascribed.ears.common.util.EarsStorage;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.awt.image.BufferedImage;

//? if <1.8 {
/*import java.awt.*;
import java.awt.image.DataBufferInt;
*///?}
//?}

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(SkinImageProcessor.class)
public abstract class SkinImageProcessorMixin {
    //? if <1.8 {
    /*@Shadow private int[] data;
    @Shadow private int width;
    *///?}
    //? if <1.13
    @Shadow private int height;

    //? if <1.8
    /*@Shadow protected abstract void setTransparent(int uMin, int vMin, int uMax, int vMax);*/
    //? if <1.13
    @Shadow protected abstract void setOpaque(int uMin, int vMin, int uMax, int vMax);

    //? if >=1.8 {
    @Unique private static boolean ears$reentering;

    //? if <1.13 {
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
    //?} else {
    /*@Inject(method = "process", at = @At("HEAD"), cancellable = true)
    private void patchProcess(BufferedImage image, CallbackInfoReturnable<BufferedImage> cir) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "parseUserSkin({}, {})", this, image);
        if (image == null) {
            EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "parseUserSkin(...): Image is null");
            cir.setReturnValue(null);
        } else {
            width = 64;
            height = 64;
            BufferedImage newImg = new BufferedImage(64, 64, 2);
            Graphics g = newImg.getGraphics();
            g.drawImage(image, 0, 0, null);

            if (image.getHeight() == 32) {
                EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "parseUserSkin(...): Upgrading legacy skin");
                g.drawImage(newImg, 24, 48, 20, 52, 4, 16, 8, 20, null);
                g.drawImage(newImg, 28, 48, 24, 52, 8, 16, 12, 20, null);
                g.drawImage(newImg, 20, 52, 16, 64, 8, 20, 12, 32, null);
                g.drawImage(newImg, 24, 52, 20, 64, 4, 20, 8, 32, null);
                g.drawImage(newImg, 28, 52, 24, 64, 0, 20, 4, 32, null);
                g.drawImage(newImg, 32, 52, 28, 64, 12, 20, 16, 32, null);
                g.drawImage(newImg, 40, 48, 36, 52, 44, 16, 48, 20, null);
                g.drawImage(newImg, 44, 48, 40, 52, 48, 16, 52, 20, null);
                g.drawImage(newImg, 36, 52, 32, 64, 48, 20, 52, 32, null);
                g.drawImage(newImg, 40, 52, 36, 64, 44, 20, 48, 32, null);
                g.drawImage(newImg, 44, 52, 40, 64, 40, 20, 44, 32, null);
                g.drawImage(newImg, 48, 52, 44, 64, 52, 20, 56, 32, null);
            }

            g.dispose();

            EarsStorage.put(newImg, EarsStorage.Key.ALFALFA, EarsCommon.preprocessSkin(new AWTEarsImage(newImg)));

            data = ((DataBufferInt) newImg.getRaster().getDataBuffer()).getData();
            EarsCommon.carefullyStripAlpha(this::setOpaque, true);
            setTransparent(32, 0, 64, 32);
            setTransparent(0, 32, 16, 48);
            setTransparent(16, 32, 40, 48);
            setTransparent(40, 32, 56, 48);
            setTransparent(0, 48, 16, 64);
            setTransparent(48, 48, 64, 64);
            cir.setReturnValue(newImg);
        }
    }
    *///?}
}