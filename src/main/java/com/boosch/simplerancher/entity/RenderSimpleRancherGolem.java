package com.boosch.simplerancher.entity;

import com.boosch.simplerancher.util.Reference;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderSimpleRancherGolem extends RenderLiving<EntitySimpleRancherGolem> {

    private ResourceLocation mobTexture = new ResourceLocation(Reference.MOD_ID+":textures/entity/entity_straw_golem.png");

    public static final Factory FACTORY = new Factory();

    public RenderSimpleRancherGolem(RenderManager renderManagerIn){

        //we use the vanilla golem model here and we simply retecture it
        super(renderManagerIn, new ModelIronGolem(), 0.1F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntitySimpleRancherGolem entity){
        return mobTexture;
    }


    public static class Factory implements IRenderFactory<EntitySimpleRancherGolem>{
        @Override
        public Render<? super EntitySimpleRancherGolem> createRenderFor(RenderManager manager){
            return new RenderSimpleRancherGolem(manager);
        }
    }
}
