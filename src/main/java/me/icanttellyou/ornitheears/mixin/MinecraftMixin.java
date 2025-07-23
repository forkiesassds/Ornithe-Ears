//? if <1.3 {
/*package me.icanttellyou.ornitheears.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.icanttellyou.ornitheears.OrnitheEars;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "run", at = @At("HEAD"))
    private void patchConstructor(CallbackInfo ci) {
        Minecraft.getInstance() = (Minecraft) (Object) this;
    }
}
*///?}