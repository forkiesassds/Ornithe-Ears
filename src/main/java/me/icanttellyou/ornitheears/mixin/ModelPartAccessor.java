//? if <1.3 {
/*package me.icanttellyou.ornitheears.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.render.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//? if >=1.0.0-beta.9
@SuppressWarnings("rawtypes")
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(ModelPart.class)
public interface ModelPartAccessor {
    //? if >=1.0.0-beta.9 {
    //Although in 1.0.0+ this field is public, in early Beta 1.9
    //prereleases this field is private, at least until pre4.
    @Accessor("boxes")
    java.util.List getBoxes();
    //?} else {
    /^@Accessor("f_3048190")
    void setVertices(net.minecraft.client.render.model.Vertex[] v);
    @Accessor("f_9309120")
    void setQuads(net.minecraft.client.render.model.Quad[] q);

    @Accessor("textureU")
    int getTextureU();
    @Accessor("textureV")
    int getTextureV();

    @Accessor("f_3048190")
    net.minecraft.client.render.model.Vertex[] getVertices();
    ^///?}
}
*///?}