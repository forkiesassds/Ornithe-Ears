package me.icanttellyou.ornitheears.mixin;

import com.unascribed.ears.common.EarsFeaturesParser;
import com.unascribed.ears.common.debug.EarsLog;
import com.unascribed.ears.common.util.EarsStorage;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.icanttellyou.ornitheears.OrnitheEars;
import net.minecraft.client.render.texture.HttpTexture;
import net.minecraft.client.render.texture.Texture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if <1.13 {
import com.unascribed.ears.common.legacy.AWTEarsImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
//?} else {
/*import com.mojang.blaze3d.platform.NativeImage;
import me.icanttellyou.ornitheears.NativeImageAdapter;
import com.unascribed.ears.common.render.AbstractEarsRenderDelegate;
*///?}


@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(HttpTexture.class)
public abstract class HttpTextureMixin {
    @Inject(method = "setImage", at = @At("HEAD"))
    public void patchSetImage(
            /*? if >=1.13 {*/ /*NativeImage image *//*?} else {*/ BufferedImage image /*?}*/,
            CallbackInfo ci
    ) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "Injecting into setImage");

        if (image == null) return;
        EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "Process player skin");
        OrnitheEars.earsSkinFeatures.put((Texture) this, EarsFeaturesParser.detect(
                /*? if >=1.13 {*/ /*new NativeImageAdapter(image) *//*?} else {*/ new AWTEarsImage(image) /*?}*/,
                EarsStorage.get(image, EarsStorage.Key.ALFALFA),
                //? if >=1.13 {
                /*data -> new NativeImageAdapter(NativeImage.read(AbstractEarsRenderDelegate.toNativeBuffer(data)))
                *///?} else {
                data -> new AWTEarsImage(ImageIO.read(new ByteArrayInputStream(data)))
                //?}
        ));
    }
}
