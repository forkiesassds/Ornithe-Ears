package me.icanttellyou.ornitheears;

//? if >=1.0.0-beta.9 {
import com.mojang.blaze3d.platform.GLX;
//?} else {
/*import org.lwjgl.opengl.GL13;
*///?}
import com.mojang.blaze3d.vertex.BufferBuilder;
//? if >=1.8 {
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tessellator;
//?}
import com.unascribed.ears.api.features.EarsFeatures;
import com.unascribed.ears.common.EarsCommon;
import com.unascribed.ears.common.EarsFeaturesStorage;
import com.unascribed.ears.common.debug.EarsLog;
import com.unascribed.ears.common.render.DirectEarsRenderDelegate;
import com.unascribed.ears.common.render.EarsRenderDelegate;
import com.unascribed.ears.common.render.EarsRenderDelegate.BodyPart;
import com.unascribed.ears.common.util.Decider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
//? if >=1.3 {
import net.minecraft.client.Minecraft;
//?} else {
/*import me.icanttellyou.ornitheears.mixin.MinecraftAccessor;
*///?}
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.client.render.model.entity.HumanoidModel;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

//? if >=1.6 {
import net.minecraft.client.render.texture.DynamicTexture;
import net.minecraft.resource.Identifier;

import java.io.IOException;
//?} else {
/*import net.minecraft.client.render.texture.TextureManager;

import java.awt.image.BufferedImage;
*///?}

import java.util.List;

@Environment(EnvType.CLIENT)
public class EarsLayer /*? if >=1.8 {*/ implements net.minecraft.client.render.entity.layer.EntityRenderLayer<ClientPlayerEntity> /*?}*/ {
    private final LivingEntityRenderer/*? if >=1.6 {*/<ClientPlayerEntity>/*?}*/ render;
    private float tickDelta;

    public EarsLayer(LivingEntityRenderer/*? if >=1.6 {*/<ClientPlayerEntity>/*?}*/ render) {
        this.render = render;
        EarsLog.debug(EarsLog.Tag.PLATFORM_RENDERER, "Constructed");
    }

    //? if >=1.8 {
    @Override
    public void render(ClientPlayerEntity entity, float limbAngle, float limbDistance,
                              float tickDelta, float age, float headYaw, float headPitch, float scale) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_RENDERER, "render({}, {}, {}, {}, {}, {}, {}, {}, {})", entity, limbAngle, limbDistance, tickDelta, age, headYaw, headPitch, scale);
    //?} else {
    /*public void render(ClientPlayerEntity entity, float limbDistance, float tickDelta) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_RENDERER, "render({}, {}, {})", entity, limbDistance, tickDelta);
    *///?}
        this.tickDelta = tickDelta;
        delegate.render(entity, null);
    }

    //? if >=1.8 {
    public void renderLeftArm(ClientPlayerEntity entity) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_RENDERER, "renderLeftArm({})", entity);
        this.tickDelta = 0;
        delegate.render(entity, BodyPart.LEFT_ARM);
    }
    //?}

    public void renderRightArm(ClientPlayerEntity entity) {
        EarsLog.debug(EarsLog.Tag.PLATFORM_RENDERER, "renderRightArm({})", entity);
        this.tickDelta = 0;
        delegate.render(entity, BodyPart.RIGHT_ARM);
    }

    //? if >=1.8 {
    @Override
    public boolean colorsWhenDamaged() {
        return true;
    }
    //?}

    private final DirectEarsRenderDelegate<ClientPlayerEntity, ModelPart> delegate =
    //? if >=1.8 {
    new DirectEarsRenderDelegate<>() {
    //?} else if >=1.6 {
    /*new com.unascribed.ears.common.legacy.PartiallyUnmanagedEarsRenderDelegate<>() {
    *///?} else {
    /*new com.unascribed.ears.common.legacy.UnmanagedEarsRenderDelegate<>() {
    *///?}
        //? if >=1.8 {
        @Override
        protected void setUpRenderState() {
            GlStateManager.enableCull();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        @Override
        protected void tearDownRenderState() {
            GlStateManager.disableBlend();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableCull();
        }
        //?}

        @Override
        protected Decider<EarsRenderDelegate.BodyPart, ModelPart> decideModelPart(Decider<EarsRenderDelegate.BodyPart, ModelPart> d) {
            HumanoidModel model =
                    //? if >=1.8 {
                    (HumanoidModel) render.getModel();
                     //?} else {
                    /*(HumanoidModel) ((me.icanttellyou.ornitheears.mixin.LivingEntityRendererAccessor) render).getModel();
                    *///?}
            return d.map(BodyPart.HEAD, model.head)
                    .map(BodyPart.LEFT_ARM, model.leftArm)
                    .map(BodyPart.LEFT_LEG, model.leftArm)
                    .map(BodyPart.RIGHT_ARM, model.rightArm)
                    .map(BodyPart.RIGHT_LEG, model.rightLeg)
                    .map(BodyPart.TORSO, model.body);
        }

        @SuppressWarnings("rawtypes")
        @Override
        protected void doAnchorTo(BodyPart part, ModelPart modelPart) {
            if (peer.isSneaking() && permittedBodyPart == null) {
                GlStateManager.translatef(0, 0.2f, 0);
            }
            modelPart.translate(1/16f);
            GlStateManager.scalef(1/16f, 1/16f, 1/16f);

            //? if >=1.0.0-beta.9 {
            //? if >1.0.0 {
            List boxes = modelPart.boxes;
            //?} else {
            /*List boxes = ((me.icanttellyou.ornitheears.mixin.ModelPartAccessor) modelPart).getBoxes();
            *///?}

            net.minecraft.client.render.model.Box cuboid = (net.minecraft.client.render.model.Box) boxes.get(0);
            GlStateManager.translatef(cuboid.minX, cuboid.maxY, cuboid.minZ);
            //?} else {
            /*net.minecraft.client.render.model.Vertex vert = ((me.icanttellyou.ornitheears.mixin.ModelPartAccessor) modelPart).getVertices()[3];
            GlStateManager.translated(vert.pos.x, vert.pos.y, vert.pos.z);
            *///?}
        }

        @Override
        protected boolean isVisible(ModelPart modelPart) {
            return modelPart.visible;
        }

        @Override
        protected EarsFeatures getEarsFeatures() {
            //? if >=1.6 {
            Identifier skin = peer.getSkinTextureLocation();
            net.minecraft.client.render.texture.Texture tex = Minecraft.getInstance().getTextureManager().get(skin);
            //?} else {
            /*String tex = peer.skin;
            *///?}
            if (OrnitheEars.earsSkinFeatures.containsKey(tex)) {
                EarsFeatures feat = OrnitheEars.earsSkinFeatures.get(tex);
                //? if >=1.7.6 {
                String name = peer.getGameProfile().getName();
                //?} else if >= 1.5 {
                /*String name = peer.getName();
                *///?} else {
                /*String name = peer.name;
                *///?}
                EarsFeaturesStorage.INSTANCE.put(name, /*? if >=1.7.6 {*/ peer.getGameProfile().getId() /*?} else {*/ /*null *//*?}*/, feat);
                //? if >=1.4 {
                if (!peer.isInvisible()) {
                    return feat;
                }
                //?} else {
                /*return feat;
                *///?}
            }
            return EarsFeatures.DISABLED;
        }

        @Override
        public boolean isSlim() {
            //? if >=1.8 {
            return ((me.icanttellyou.ornitheears.mixin.PlayerModelAccessor) render.getModel()).getThinArms();
             //?} else {
            /*return ((PlayerModel) ((me.icanttellyou.ornitheears.mixin.LivingEntityRendererAccessor) render).getModel()).thinArms;
            *///?}
        }

        //? if >=1.8 {
        @Override
        protected void pushMatrix() {
            GlStateManager.pushMatrix();
        }

        @Override
        protected void popMatrix() {
            GlStateManager.popMatrix();
        }
        //?}

        @Override
        protected void doBindSkin() {
            //? if >=1.6 {
            Minecraft.getInstance().getTextureManager().bind(peer.getSkinTextureLocation());
            //?} else {
            /*TextureManager manager = Minecraft.getInstance().textureManager;
            int id = manager.bindHttpTexture(peer.skin, peer.getTexture());
            if (id < 0) return;
            GlStateManager.bindTexture(id);
            //? if >=1.5
            manager.clear();
            *///?}
        }

        //? if >=1.6 {
        @Override
        protected void doBindAux(EarsRenderDelegate.TexSource src, byte[] pngData) {
            if (pngData == null) {
                GlStateManager.bindTexture(0);
            } else {
                Identifier skin = peer.getSkinTextureLocation();
                Identifier id = new Identifier(skin.getNamespace(), src.addSuffix(skin.getPath()));
                if (Minecraft.getInstance().getTextureManager().get(id) == null) {
                    try {
                        Minecraft.getInstance().getTextureManager().register(id,
                                //? if >=1.13 {
                                /*new DynamicTexture(com.mojang.blaze3d.platform.NativeImage.read(toNativeBuffer(pngData)))
                                 *///?} else {
                                new DynamicTexture(javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(pngData)))
                                //?}
                        );
                    } catch (IOException e) {
                        Minecraft.getInstance().getTextureManager().register(id,
                                //? if >=1.13 {
                                /*net.minecraft.unmapped.C_9812125.m_8862353()
                                 *///?} else {
                                com.mojang.blaze3d.platform.TextureUtil.MISSING_TEXTURE
                                //?}
                        );
                    }
                }
                Minecraft.getInstance().getTextureManager().bind(id);
            }
        }
        //?} else {
        /*@Override
        protected String getSkinUrl() {
            return peer.skin;
        }

        @Override
        protected int uploadImage(BufferedImage bufferedImage) {
            return Minecraft.getInstance().textureManager.bind(bufferedImage);
        }
        *///?}

        //? if >=1.8 {
        @Override
        protected void doTranslate(float x, float y, float z) {
            GlStateManager.translatef(x, y, z);
        }

        @Override
        protected void doRotate(float ang, float x, float y, float z) {
            GlStateManager.rotatef(ang, x, y, z);
        }

        @Override
        protected void doScale(float x, float y, float z) {
            GlStateManager.scalef(x, y, z);
        }
        //?}

        @Override
        protected void beginQuad() {
            //? if >=1.8 {
            Tessellator.getInstance().getBuilder().begin(GL11.GL_QUADS, DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);
             //?} else {
            /*BufferBuilder.INSTANCE.start(GL11.GL_QUADS);
            *///?}
        }

        @Override
        protected void addVertex(float x, float y, int z, float r, float g, float b, float a, float u, float v, float nX, float nY, float nZ) {
            //? if >=1.8 {
            Tessellator.getInstance().getBuilder().vertex(x, y, z).texture(u, v).color(r, g, b, a).normal(nX, nY, nZ).nextVertex();
             //?} else {
            /*BufferBuilder bb = BufferBuilder.INSTANCE;
            bb.color(r, g, b, a);
            bb.normal(nX, nY, nZ);
            bb.vertex(x, y, z, u, v);
            *///?}
        }

        //FIXME: supposed to be >=1.0.0-beta.8, but stonecutter not happy with that
        //? if >1.0.0-beta.7.3 {
        @Override
        public void setEmissive(boolean emissive) {
            super.setEmissive(emissive);
            //? if >=1.8 {
            GlStateManager.activeTexture(GLX.GL_TEXTURE1);
            if (emissive) {
                GlStateManager.disableLighting();
                GlStateManager.disableTexture();
            } else {
                GlStateManager.enableLighting();
                GlStateManager.enableTexture();
            }
            GlStateManager.activeTexture(GLX.GL_TEXTURE0);
            //?} else {
            /*//? if >=1.0.0-beta.9 {
            GLX.activeTexture(GLX.GL_TEXTURE1);
            //?} else {
            /^GL13.glActiveTexture(GL13.GL_TEXTURE1);
            ^///?}
            if (emissive) {
               GL11.glDisable(GL11.GL_TEXTURE_2D);
            } else {
               GL11.glEnable(GL11.GL_TEXTURE_2D);
            }
            //? if >=1.0.0-beta.9 {
            GLX.activeTexture(GLX.GL_TEXTURE0);
            //?} else {
            /^GL13.glActiveTexture(GL13.GL_TEXTURE0);
            ^///?}
            *///?}
        }
        //?}


        @Override
        protected void drawQuad() {
            Tessellator.getInstance().end();
        }

        //? if >=1.8 {
        @Override
        protected void doRenderDebugDot(float r, float g, float b, float a) {
            GL11.glPointSize(8);
            GlStateManager.disableTexture();
            BufferBuilder bb = Tessellator.getInstance().getBuilder();
            bb.begin(GL11.GL_POINTS, DefaultVertexFormat.POSITION_COLOR);
            bb.vertex(0, 0, 0).color(r, g, b, a).nextVertex();
            Tessellator.getInstance().end();
            GlStateManager.enableTexture();
        }
        //?}

        @Override
        public float getTime() {
            return peer.time + tickDelta;
        }

        @Override
        public boolean isFlying() {
            //FIXME: supposed to be >=1.0.0-beta.8, but stonecutter not happy with that
            //? if >1.0.0-beta.7.3 {
            return peer.abilities.flying;
            //?} else {
            /*return false;
            *///?}
        }

        @Override
        public boolean isGliding() {
            return false;
        }

        @Override
        public boolean isJacketEnabled() {
            //? if >=1.8 {
            return peer.isModelPartVisible(net.minecraft.client.render.model.PlayerModelPart.JACKET);
             //?} else {
            /*return true;
            *///?}
        }

        @Override
        public boolean isWearingBoots() {
            ItemStack feet = peer.inventory.getArmor(0);
            return feet != null && feet.getItem() instanceof ArmorItem;
        }

        @Override
        public boolean isWearingChestplate() {
            ItemStack chest = peer.inventory.getArmor(2);
            return chest != null && chest.getItem() instanceof ArmorItem;
        }

        @Override
        public boolean isWearingElytra() {
            //? if >=1.9 {
            /*ItemStack chest = peer.inventory.getArmor(2);
            return chest != null && chest.getItem() instanceof net.minecraft.item.ElytraItem;
            *///?} else {
            return false;
            //?}
        }

        @Override
        public float getHorizontalSpeed() {
            return EarsCommon.lerpDelta(peer.prevHorizontalSpeed, peer.horizontalVelocity, tickDelta);
        }

        @Override
        public float getLimbSwing() {
            return EarsCommon.lerpDelta(peer.prevHandSwingAmount, peer.handSwingAmount, tickDelta);
        }

        @Override
        public float getStride() {
            return EarsCommon.lerpDelta(peer.prevStrideDistance, peer.strideDistance, tickDelta);
        }

        @Override
        public float getBodyYaw() {
            return EarsCommon.lerpDelta(peer.prevBodyYaw, peer.bodyYaw, tickDelta);
        }

        @Override
        public double getCapeX() {
            return EarsCommon.lerpDelta(peer.lastCapeX, peer.capeX, tickDelta);
        }

        @Override
        public double getCapeY() {
            return EarsCommon.lerpDelta(peer.lastCapeY, peer.capeY, tickDelta);
        }

        @Override
        public double getCapeZ() {
            return EarsCommon.lerpDelta(peer.lastCapeZ, peer.capeZ, tickDelta);
        }

        @Override
        public double getX() {
            return EarsCommon.lerpDelta(peer.prevX, peer.x, tickDelta);
        }

        @Override
        public double getY() {
            return EarsCommon.lerpDelta(peer.prevY, peer.y, tickDelta);
        }

        @Override
        public double getZ() {
            return EarsCommon.lerpDelta(peer.prevZ, peer.z, tickDelta);
        }
    };
}
