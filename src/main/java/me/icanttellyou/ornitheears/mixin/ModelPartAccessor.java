//? <1.3 {
/*package me.icanttellyou.ornitheears.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.render.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@SuppressWarnings("rawtypes")
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(ModelPart.class)
public interface ModelPartAccessor {
    //Although in 1.0.0+ this field is public, in early Beta 1.9
    //prereleases this field is private, at least until pre4.
    @Accessor("boxes")
    List getBoxes();
}
*///?}