//? if <1.6 {
/*package me.icanttellyou.ornitheears.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.unascribed.ears.common.EarsFeaturesParser;
import com.unascribed.ears.common.debug.EarsLog;
import com.unascribed.ears.common.legacy.AWTEarsImage;
import com.unascribed.ears.common.util.EarsStorage;
import com.unascribed.ears.legacy.LegacyHelper;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.icanttellyou.ornitheears.OrnitheEars;
import net.minecraft.client.render.texture.HttpTexture;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(targets = "net/minecraft/client/render/texture/HttpTexture$1")
public abstract class HttpTextureThreadMixin {
    @Shadow @Final String f_4140703;

    @ModifyExpressionValue(
        method = "run",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/texture/HttpTexture$1;f_4140703:Ljava/lang/String;",
            opcode = Opcodes.GETFIELD
        )
    )
    public String patchGetUrl(String url) {
        int trim = ears$getLengthToTrim(url);
        if (trim != -1 && url.endsWith(".png")) {
            String username = url.substring(trim, url.length() - 4);
            return LegacyHelper.getSkinUrl(username);
        }

        return url;
    }

    @WrapOperation(
        method = "run",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/texture/HttpTexture;image:Ljava/awt/image/BufferedImage;",
            ordinal = 1,
            opcode = Opcodes.PUTFIELD
        )
    )
    public void patchSetImage(HttpTexture instance, BufferedImage value, Operation<Void> original) {
        original.call(instance, value);

        EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "Process player skin");
        OrnitheEars.earsSkinFeatures.put(f_4140703, EarsFeaturesParser.detect(
                new AWTEarsImage(value),
                EarsStorage.get(value, EarsStorage.Key.ALFALFA),
                data -> new AWTEarsImage(ImageIO.read(new ByteArrayInputStream(data)))
        ));
    }

    @Unique
    private static int ears$getLengthToTrim(String url) {
        if (url == null) return 1;

        if (url.startsWith("http://skins.minecraft.net/MinecraftSkins")) return 42;
        if (url.startsWith("http://s3.amazonaws.com/MinecraftSkins")) return 39;
        if (url.startsWith("http://www.minecraft.net/skin")) return 30;

        return -1;
    }
}
*///?}