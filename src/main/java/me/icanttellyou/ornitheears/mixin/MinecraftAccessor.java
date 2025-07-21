//? if <1.3 {
/*package me.icanttellyou.ornitheears.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(Minecraft.class)
public interface MinecraftAccessor {
    @Accessor("INSTANCE")
    static Minecraft getInstance() {
        throw new IllegalStateException("Failed to mixin.");
    }
}
*///?}