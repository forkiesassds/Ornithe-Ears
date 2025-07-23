//? if <1.8 {
/*package me.icanttellyou.ornitheears;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.client.render.model.entity.HumanoidModel;
//? if >1.0.0-beta.7.3
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class PlayerModel extends HumanoidModel {
    public ModelPart leftSleeve;
    public ModelPart rightSleeve;
    public ModelPart leftPants;
    public ModelPart rightPants;
    public ModelPart jacket;
    public ModelPart ears;
    public boolean thinArms;

    //? if <1.0.0-beta.9 {
    /^public int textureWidth;
    public int textureHeight;
    ^///?}

    public PlayerModel(float reduction, boolean thinArms) {
        //? if >=1.4 {
        super(reduction, 0.0F, 64, 64);
        this.cape = newModelPart(this, 0, 0);
        this.cape.setTextureSize(64, 32);
        this.cape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, reduction);
        //?} else {
        /^super(reduction, 0.0F);
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.head = newModelPart(this, 0, 0);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, reduction);
        this.head.setPivot(0.0F, 0.0F, 0.0F);
        this.hat = newModelPart(this, 32, 0);
        this.hat.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, reduction + 0.5F);
        this.hat.setPivot(0.0F, 0.0F, 0.0F);
        this.body = newModelPart(this, 16, 16);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, reduction);
        this.body.setPivot(0.0F, 0.0F, 0.0F);
        this.rightLeg = newModelPart(this, 0, 16);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, reduction);
        this.rightLeg.setPivot(-1.9F, 12.0F, 0.0F);
        this.ears = newModelPart(this, 24, 0);
        this.ears.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, reduction);
        ^///?}
        this.thinArms = thinArms;
        if (thinArms) {
            this.leftArm = newModelPart(this, 32, 48);
            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, reduction);
            this.leftArm.setPivot(5.0F, 2.0F, 0.0F);
            this.rightArm = newModelPart(this, 40, 16);
            this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, reduction);
            this.rightArm.setPivot(-5.0F, 2.0F, 0.0F);
            this.leftSleeve = newModelPart(this, 48, 48);
            this.leftSleeve.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, reduction + 0.25F);
            this.leftSleeve.setPivot(5.0F, 2.0F, 0.0F);
            this.rightSleeve = newModelPart(this, 40, 32);
            this.rightSleeve.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, reduction + 0.25F);
            this.rightSleeve.setPivot(-5.0F, 2.0F, 10.0F);
        } else {
            this.leftArm = newModelPart(this, 32, 48);
            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, reduction);
            this.leftArm.setPivot(5.0F, 2.0F, 0.0F);
            this.leftSleeve = newModelPart(this, 48, 48);
            this.leftSleeve.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, reduction + 0.25F);
            this.leftSleeve.setPivot(5.0F, 2.0F, 0.0F);
            this.rightSleeve = newModelPart(this, 40, 32);
            this.rightSleeve.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, reduction + 0.25F);
            this.rightSleeve.setPivot(-5.0F, 2.0F, 10.0F);
        }

        this.leftLeg = newModelPart(this, 16, 48);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, reduction);
        this.leftLeg.setPivot(1.9F, 12.0F, 0.0F);
        this.leftPants = newModelPart(this, 0, 48);
        this.leftPants.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, reduction + 0.25F);
        this.leftPants.setPivot(1.9F, 12.0F, 0.0F);
        this.rightPants = newModelPart(this, 0, 32);
        this.rightPants.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, reduction + 0.25F);
        this.rightPants.setPivot(-1.9F, 12.0F, 0.0F);
        this.jacket = newModelPart(this, 16, 32);
        this.jacket.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, reduction + 0.25F);
        this.jacket.setPivot(0.0F, 0.0F, 0.0F);
    }

    //FIXME: change this to >=1.0.0-beta.8 after stonecutter is fixed
    @Override
    public void render(/^? if >1.0.0-beta.7.3 {^/ Entity entity,/^?}^/ float handSwing, float handSwingAmount, float age, float yaw, float pitch, float scale) {
        super.render(/^? if >1.0.0-beta.7.3 {^/ entity, /^?}^/ handSwing, handSwingAmount, age, yaw, pitch, scale);
        GL11.glPushMatrix();
        //? if >=1.0.0-beta.9 {
        if (this.isBaby) {
            float f = 2.0F;
            GlStateManager.scalef(1.0F / f, 1.0F / f, 1.0F / f);
            GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
        } else
        //?}
            if (/^? if >1.0.0-beta.7.3 {^/ entity.isSneaking()/^?} else {^/ /^this.sneaking ^//^?}^/) {
                GlStateManager.translatef(0.0F, 0.2F, 0.0F);
            }

        this.leftPants.render(scale);
        this.rightPants.render(scale);
        this.leftSleeve.render(scale);
        this.rightSleeve.render(scale);
        this.jacket.render(scale);

        GL11.glPopMatrix();
    }

    @Override
    public void setAngles(float handSwing, float handSwingAmount, float age, float yaw, float pitch, float scale /^? if >=1.4 {^/, Entity entity /^?}^/) {
        super.setAngles(handSwing, handSwingAmount, age, yaw, pitch, scale /^? if >=1.4 {^/, entity /^?}^/);
        copyRotation(this.leftLeg, this.leftPants);
        copyRotation(this.rightLeg, this.rightPants);
        copyRotation(this.leftArm, this.leftSleeve);
        copyRotation(this.rightArm, this.rightSleeve);
        copyRotation(this.body, this.jacket);

    }

    public static void copyRotation(ModelPart from, ModelPart to) {
        to.rotationX = from.rotationX;
        to.rotationY = from.rotationY;
        to.rotationZ = from.rotationZ;
        to.pivotX = from.pivotX;
        to.pivotY = from.pivotY;
        to.pivotZ = from.pivotZ;
    }

    @Override
    public void renderDeadmau5Ears(float scale) {
        this.ears.rotationY = this.head.rotationY;
        this.ears.rotationX = this.head.rotationX;
        this.ears.pivotX = 0.0F;
        this.ears.pivotY = 0.0F;
        this.ears.render(scale);
    }

    public static ModelPart newModelPart(PlayerModel model, int textureU, int textureV) {
        //? if >=1.0.0-beta.9 {
        return new ModelPart(model, textureU, textureV);
        //?} else {
        /^//FIXME: change this to >=1.0.0-beta.8 after stonecutter is fixed
        SizeableTexModelPart modelPart = new SizeableTexModelPart(/^¹? if >1.0.0-beta.7.3 {¹^/ model, /^¹?}¹^/ textureU, textureV);
        modelPart.setTextureSize(model.textureWidth, model.textureHeight);
        return modelPart;
        ^///?}
    }
}
*///?}