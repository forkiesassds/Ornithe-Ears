//? if <1.6 {
/*package me.icanttellyou.ornitheears.mixin;

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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(targets = "net/minecraft/client/render/texture/HttpTexture$1")
public abstract class HttpTextureThreadMixin {
    @Shadow @Final String f_4140703;

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @WrapOperation(
        method = "run",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/texture/HttpTexture$1;f_4140703:Ljava/lang/String;",
            opcode = Opcodes.GETFIELD
        )
    )
    public String patchGetUrl(@Coerce Object instance, Operation<String> original) {
        String url = original.call(instance);

        if (url.startsWith("http://skins.minecraft.net/MinecraftSkins") && url.endsWith(".png")) {
            String username = url.substring(42, url.length() - 4);
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
}
*///?}