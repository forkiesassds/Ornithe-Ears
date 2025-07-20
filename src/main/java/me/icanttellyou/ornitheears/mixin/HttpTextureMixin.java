//? if >=1.6 {
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

//? if <1.7 {
/*import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import net.minecraft.client.render.texture.HttpImageProcessor;
import net.minecraft.resource.Identifier;
*///?}
//?} else {
/*import com.mojang.blaze3d.platform.NativeImage;
import me.icanttellyou.ornitheears.NativeImageAdapter;
import com.unascribed.ears.common.render.AbstractEarsRenderDelegate;
*///?}


@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(HttpTexture.class)
public abstract class HttpTextureMixin {
    //? if <1.7 {
    /*@Mutable
    @Shadow @Final private String url;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void patchConstructor(String url, Identifier location, HttpImageProcessor processor, CallbackInfo ci) {
        if (url.startsWith("http://skins.minecraft.net/MinecraftSkins") && url.endsWith(".png")) {
            String username = url.substring(42, url.length() - 4);
            this.url = com.unascribed.ears.legacy.LegacyHelper.getSkinUrl(username);
        }
    }
    *///?}

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
//?}