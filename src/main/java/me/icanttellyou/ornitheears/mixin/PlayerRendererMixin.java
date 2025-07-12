package me.icanttellyou.ornitheears.mixin;

import com.unascribed.ears.common.debug.EarsLog;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.icanttellyou.ornitheears.EarsLayer;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.Model;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<ClientPlayerEntity> {
    public PlayerRendererMixin(EntityRenderDispatcher dispatcher, Model model, float shadowSize) {
        super(dispatcher, model, shadowSize);
    }

    @Unique
    private EarsLayer ears$earsLayer;

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V", at = @At("TAIL"))
    private void patchConstructor(EntityRenderDispatcher dispatcher, boolean thinArms, CallbackInfo ci) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "Construct player renderer");
        this.addLayer(ears$earsLayer = new EarsLayer(this));
    }

    @Inject(method = "renderPlayerLeftHandModel", at = @At("TAIL"))
    private void patchRenderPlayerLeftHandModel(ClientPlayerEntity player, CallbackInfo ci) {
        ears$earsLayer.renderLeftArm(player);
    }

    @Inject(method = "renderPlayerRightHandModel", at = @At("TAIL"))
    private void patchRenderPlayerRightHandModel(ClientPlayerEntity player, CallbackInfo ci) {
        ears$earsLayer.renderRightArm(player);
    }
}
