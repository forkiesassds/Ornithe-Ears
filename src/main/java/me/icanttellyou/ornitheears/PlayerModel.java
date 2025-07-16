//? if <1.8 {
/*package me.icanttellyou.ornitheears;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.client.render.model.entity.HumanoidModel;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class PlayerModel extends HumanoidModel {
    public ModelPart leftSleeve;
    public ModelPart rightSleeve;
    public ModelPart leftPants;
    public ModelPart rightPants;
    public ModelPart jacket;
    private ModelPart cape;
    private ModelPart ears;
    public boolean thinArms;

    public PlayerModel(float reduction, boolean thinArms) {
        super(reduction, 0.0F, 64, 64);
        this.thinArms = thinArms;
        this.ears = new ModelPart(this, 24, 0);
        this.ears.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, reduction);
        this.cape = new ModelPart(this, 0, 0);
        this.cape.setTextureSize(64, 32);
        this.cape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, reduction);
        if (thinArms) {
            this.leftArm = new ModelPart(this, 32, 48);
            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, reduction);
            this.leftArm.setPivot(5.0F, 2.5F, 0.0F);
            this.rightArm = new ModelPart(this, 40, 16);
            this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, reduction);
            this.rightArm.setPivot(-5.0F, 2.5F, 0.0F);
            this.leftSleeve = new ModelPart(this, 48, 48);
            this.leftSleeve.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, reduction + 0.25F);
            this.leftSleeve.setPivot(5.0F, 2.5F, 0.0F);
            this.rightSleeve = new ModelPart(this, 40, 32);
            this.rightSleeve.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, reduction + 0.25F);
            this.rightSleeve.setPivot(-5.0F, 2.5F, 10.0F);
        } else {
            this.leftArm = new ModelPart(this, 32, 48);
            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, reduction);
            this.leftArm.setPivot(5.0F, 2.0F, 0.0F);
            this.leftSleeve = new ModelPart(this, 48, 48);
            this.leftSleeve.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, reduction + 0.25F);
            this.leftSleeve.setPivot(5.0F, 2.0F, 0.0F);
            this.rightSleeve = new ModelPart(this, 40, 32);
            this.rightSleeve.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, reduction + 0.25F);
            this.rightSleeve.setPivot(-5.0F, 2.0F, 10.0F);
        }

        this.leftLeg = new ModelPart(this, 16, 48);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, reduction);
        this.leftLeg.setPivot(1.9F, 12.0F, 0.0F);
        this.leftPants = new ModelPart(this, 0, 48);
        this.leftPants.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, reduction + 0.25F);
        this.leftPants.setPivot(1.9F, 12.0F, 0.0F);
        this.rightPants = new ModelPart(this, 0, 32);
        this.rightPants.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, reduction + 0.25F);
        this.rightPants.setPivot(-1.9F, 12.0F, 0.0F);
        this.jacket = new ModelPart(this, 16, 32);
        this.jacket.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, reduction + 0.25F);
        this.jacket.setPivot(0.0F, 0.0F, 0.0F);
    }

    public void render(Entity entity, float handSwing, float handSwingAmount, float age, float yaw, float pitch, float scale) {
        super.render(entity, handSwing, handSwingAmount, age, yaw, pitch, scale);
        GL11.glPushMatrix();
        if (this.isBaby) {
            float f = 2.0F;
            GlStateManager.scalef(1.0F / f, 1.0F / f, 1.0F / f);
            GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
        } else if (entity.isSneaking()) {
            GlStateManager.translatef(0.0F, 0.2F, 0.0F);
        }

        this.leftPants.render(scale);
        this.rightPants.render(scale);
        this.leftSleeve.render(scale);
        this.rightSleeve.render(scale);
        this.jacket.render(scale);

        GL11.glPopMatrix();
    }

    public void setAngles(float handSwing, float handSwingAmount, float age, float yaw, float pitch, float scale, Entity entity) {
        super.setAngles(handSwing, handSwingAmount, age, yaw, pitch, scale, entity);
        copyRotation(this.leftLeg, this.leftPants);
        copyRotation(this.rightLeg, this.rightPants);
        copyRotation(this.leftArm, this.leftSleeve);
        copyRotation(this.rightArm, this.rightSleeve);
        copyRotation(this.body, this.jacket);
        if (entity.isSneaking()) {
            this.cape.pivotY = 2.0F;
        } else {
            this.cape.pivotY = 0.0F;
        }
    }

    public static void copyRotation(ModelPart from, ModelPart to) {
        to.rotationX = from.rotationX;
        to.rotationY = from.rotationY;
        to.rotationZ = from.rotationZ;
        to.pivotX = from.pivotX;
        to.pivotY = from.pivotY;
        to.pivotZ = from.pivotZ;
    }
}
*///?}