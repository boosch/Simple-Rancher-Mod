package com.boosch.simplerancher.entity.model;

import com.boosch.simplerancher.entity.EntitySimpleRancherGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSimpleRancherGolem extends ModelBase
{
    /** The head model for the iron golem. */
    public ModelRenderer simpleRancherGolemHead;
    /** The body model for the iron golem. */
    public ModelRenderer simpleRancherGolemBody;
    /** The right arm model for the iron golem. */
    public ModelRenderer simpleRancherGolemRightArm;
    /** The left arm model for the iron golem. */
    public ModelRenderer simpleRancherGolemLeftArm;
    /** The left leg model for the Iron Golem. */
    public ModelRenderer simpleRancherGolemLeftLeg;
    /** The right leg model for the Iron Golem. */
    public ModelRenderer simpleRancherGolemRightLeg;

    public ModelSimpleRancherGolem()
    {
        this(0.0F);
    }

    public ModelSimpleRancherGolem(float p_i1161_1_)
    {
        this(p_i1161_1_, -7.0F);
    }

    public ModelSimpleRancherGolem(float p_i46362_1_, float p_i46362_2_)
    {
        int i = 128;
        int j = 128;
        this.simpleRancherGolemHead = (new ModelRenderer(this)).setTextureSize(128, 128);
        this.simpleRancherGolemHead.setRotationPoint(0.0F, 0.0F + p_i46362_2_, -2.0F);
        this.simpleRancherGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, p_i46362_1_);
        this.simpleRancherGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, p_i46362_1_);
        this.simpleRancherGolemBody = (new ModelRenderer(this)).setTextureSize(128, 128);
        this.simpleRancherGolemBody.setRotationPoint(0.0F, 0.0F + p_i46362_2_, 0.0F);
        this.simpleRancherGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, p_i46362_1_);
        this.simpleRancherGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, p_i46362_1_ + 0.5F);
        this.simpleRancherGolemRightArm = (new ModelRenderer(this)).setTextureSize(128, 128);
        this.simpleRancherGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.simpleRancherGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
        this.simpleRancherGolemLeftArm = (new ModelRenderer(this)).setTextureSize(128, 128);
        this.simpleRancherGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.simpleRancherGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
        this.simpleRancherGolemLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(128, 128);
        this.simpleRancherGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + p_i46362_2_, 0.0F);
        this.simpleRancherGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
        this.simpleRancherGolemRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(128, 128);
        this.simpleRancherGolemRightLeg.mirror = true;
        this.simpleRancherGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + p_i46362_2_, 0.0F);
        this.simpleRancherGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.simpleRancherGolemHead.render(scale);
        this.simpleRancherGolemBody.render(scale);
        this.simpleRancherGolemLeftLeg.render(scale);
        this.simpleRancherGolemRightLeg.render(scale);
        this.simpleRancherGolemRightArm.render(scale);
        this.simpleRancherGolemLeftArm.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.simpleRancherGolemHead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.simpleRancherGolemHead.rotateAngleX = headPitch * 0.017453292F;
        this.simpleRancherGolemLeftLeg.rotateAngleX = -1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.simpleRancherGolemRightLeg.rotateAngleX = 1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.simpleRancherGolemLeftLeg.rotateAngleY = 0.0F;
        this.simpleRancherGolemRightLeg.rotateAngleY = 0.0F;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        EntitySimpleRancherGolem entitygolem = (EntitySimpleRancherGolem)entitylivingbaseIn;
        int i = entitygolem.getAttackTimer();

        if (i > 0)
        {
            this.simpleRancherGolemRightArm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float)i - partialTickTime, 10.0F);
            this.simpleRancherGolemLeftArm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float)i - partialTickTime, 10.0F);
        }
        else
        {


            /*
            int j = entitygolem.getHoldRoseTick();

            if (j > 0)
            {
                this.simpleRancherGolemRightArm.rotateAngleX = -0.8F + 0.025F * this.triangleWave((float)j, 70.0F);
                this.simpleRancherGolemLeftArm.rotateAngleX = 0.0F;
            }
            else
            {
                this.simpleRancherGolemRightArm.rotateAngleX = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
                this.simpleRancherGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
            }
            */
        }
    }

    private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}