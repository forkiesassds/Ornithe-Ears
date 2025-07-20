//? if >=1.8 {
package me.icanttellyou.ornitheears.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.render.model.entity.PlayerModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin {
    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 2.5F))
    public float fixupSlimArmPivotPoint(float constant) {
        return 2.0F;
    }
}
//?}