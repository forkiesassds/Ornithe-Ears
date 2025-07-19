package me.icanttellyou.ornitheears.mixin;

import com.unascribed.ears.common.debug.EarsLog;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.icanttellyou.ornitheears.EarsLayer;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.model.Model;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if <1.8 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.icanttellyou.ornitheears.PlayerModel;
import net.minecraft.client.render.model.entity.HumanoidModel;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Shadow;
*///?}


@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
//? if >=1.8 {
@Mixin(net.minecraft.client.render.entity.PlayerRenderer.class)
//?} else {
/*@Mixin(net.minecraft.client.render.entity.PlayerEntityRenderer.class)
*///?}
public abstract class PlayerRendererMixin extends LivingEntityRenderer<ClientPlayerEntity> {
    //? if >=1.8 {
    public PlayerRendererMixin(net.minecraft.client.render.entity.EntityRenderDispatcher dispatcher, Model model, float shadowSize) {
        super(dispatcher, model, shadowSize);
    }
    //?} else {
    /*public PlayerRendererMixin(Model model, float shadowSize) {
        super(model, shadowSize);
    }
    *///?}

    @Unique
    private EarsLayer ears$earsLayer;

    //? if <1.8 {
    /*@Unique
    private final PlayerModel ears$slimModel = new PlayerModel(0.0F, true);
    @Unique
    private final PlayerModel ears$slimModel1 = new PlayerModel(1.0F, true);
    @Unique
    private final PlayerModel ears$slimModel2 = new PlayerModel(0.5F, true);

    @Shadow private HumanoidModel handmodel;
    @Shadow private HumanoidModel model1;
    @Shadow private HumanoidModel model2;
    *///?}


    @Inject(method =
            //? if >=1.8 {
            "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V",
            //?} else {
            /*"<init>",
            *///?}
            at = @At("TAIL"))
    private void patchConstructor(
            //? if >=1.8
            net.minecraft.client.render.entity.EntityRenderDispatcher dispatcher, boolean thinArms,
                                  CallbackInfo ci) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_INJECT, "Construct player renderer");
        //? if >=1.8 {
        this.addLayer(ears$earsLayer = new EarsLayer(this));
        //?} else {
        /*ears$earsLayer = new EarsLayer(this);
        *///?}
    }


    //? if <1.8 {
    /*@WrapOperation(method = "<init>", at = @At(value = "NEW", target = "(F)Lnet/minecraft/client/render/model/entity/HumanoidModel;"))
    private static HumanoidModel replaceModels(float reduction, Operation<HumanoidModel> original) {
        return new me.icanttellyou.ornitheears.PlayerModel(reduction, false);
    }

    @Unique
    private void ears$beforeRenderModel(PlayerEntity entity) {
        //? if >=1.7 {
        com.unascribed.ears.legacy.LegacyHelper.ensureLookedUpAsynchronously(entity.getGameProfile().getId(), entity.getGameProfile().getName());
        boolean slim = com.unascribed.ears.legacy.LegacyHelper.isSlimArms(entity.getGameProfile().getId());
        //?} else {
        /^com.unascribed.ears.legacy.LegacyHelper.ensureLookedUpAsynchronously(entity.getName());
        boolean slim = com.unascribed.ears.legacy.LegacyHelper.isSlimArms(entity.getName());
        ^///?}

        if (slim) {
            this.model = ears$slimModel;
            this.model1 = ears$slimModel1;
            this.model2 = ears$slimModel2;
            this.handmodel = ears$slimModel;
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;DDDFF)V", at = @At("HEAD"))
    private void patchRender(ClientPlayerEntity clientPlayerEntity, double d2, double e, double f, float g, float h, CallbackInfo ci) {
        ears$beforeRenderModel(clientPlayerEntity);
    }

    @Inject(method = "renderDecoration(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;F)V", at = @At("TAIL"))
    private void patchRenderDecoration(ClientPlayerEntity clientPlayerEntity, float f2, CallbackInfo ci) {
        ears$earsLayer.render(clientPlayerEntity, clientPlayerEntity.prevHandSwingAmount + (clientPlayerEntity.handSwingAmount - clientPlayerEntity.prevHandSwingAmount) * f2, f2);
    }

    @Inject(method = "renderPlayerRightHandModel", at = @At("HEAD"))
    private void patchRenderPlayerRightHandModelBeforeRender(PlayerEntity player, CallbackInfo ci) {
        ears$beforeRenderModel(player);
    }
    *///?}

    //? if >=1.8 {
    @Inject(method = "renderPlayerLeftHandModel", at = @At("TAIL"))
    private void patchRenderPlayerLeftHandModel(ClientPlayerEntity player, CallbackInfo ci) {
        ears$earsLayer.renderLeftArm(player);
    }
    //?}

    @Inject(method = "renderPlayerRightHandModel", at = @At("TAIL"))
    private void patchRenderPlayerRightHandModel(/*? if >=1.8 {*/ ClientPlayerEntity /*?} else {*/ /*PlayerEntity *//*?}*/ player, CallbackInfo ci) {
        //? if <1.8
        /*((me.icanttellyou.ornitheears.PlayerModel) handmodel).rightSleeve.render(0.0625F);*/
        ears$earsLayer.renderRightArm((ClientPlayerEntity) player);
    }
}
