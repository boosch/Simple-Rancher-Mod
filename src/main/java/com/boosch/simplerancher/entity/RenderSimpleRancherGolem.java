package com.boosch.simplerancher.entity;

import com.boosch.simplerancher.entity.layers.LayerSimpleRancherGolemHoldItem;
import com.boosch.simplerancher.entity.model.ModelSimpleRancherGolem;
import com.boosch.simplerancher.util.Reference;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerIronGolemFlower;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderSimpleRancherGolem extends RenderLiving<EntitySimpleRancherGolem> {

    //private ResourceLocation mobTexture = new ResourceLocation(Reference.MOD_ID+":textures/entity/entity_straw_golem.png");
    private String strawMobTextureSuffix = ":textures/entity/entity_straw_golem.png";
    private String defaultMobTextureSuffix = ":textures/entity/entity_base_golem.png";


    public static final Factory FACTORY = new Factory();

    public RenderSimpleRancherGolem(RenderManager renderManagerIn){

        //we use the vanilla golem model here and we simply retecture it
        super(renderManagerIn, new ModelSimpleRancherGolem(), .3F);
        //this.addLayer(new LayerSimpleRancherGolemHoldItem(this));
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntitySimpleRancherGolem entity){

        String suffix = defaultMobTextureSuffix;
        switch(entity.type){
            case "straw":
                suffix = strawMobTextureSuffix;
                break;
            default:
                break;
        }
        return new ResourceLocation(Reference.MOD_ID+suffix);
    }

    @Override
    protected void preRenderCallback(EntitySimpleRancherGolem entitylivingbaseIn, float partialTickTime)
    {
        preRencderCallbackSimpleRancherGolem((EntitySimpleRancherGolem)entitylivingbaseIn, partialTickTime);
    }

    protected void preRencderCallbackSimpleRancherGolem(EntitySimpleRancherGolem golem, float f){

        GlStateManager.scale(golem.getScale(), golem.getScale(), golem.getScale() );
    }




    public static class Factory implements IRenderFactory<EntitySimpleRancherGolem>{
        @Override
        public Render<? super EntitySimpleRancherGolem> createRenderFor(RenderManager manager){
            return new RenderSimpleRancherGolem(manager);
        }
    }
}
