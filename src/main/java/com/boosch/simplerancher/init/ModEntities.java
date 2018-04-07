package com.boosch.simplerancher.init;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.entity.golem.EntitySimpleRancherGolem;
import com.boosch.simplerancher.entity.golem.renderers.RenderSimpleRancherGolem;
import com.boosch.simplerancher.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {


    public static void init(){

        //every entity in our mod has an id local to this mod
        int id = 1;

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "straw_golem"), EntitySimpleRancherGolem.class, "basegolem", id++, SimpleRancher.instance, 64, 3, true, 0x99600, 0x00ff00);

        //if it could spawn, we'd add something like this
        //EntityRegistry.addSpawn(EntitySimpleRancherGolem.class, 100, 3, 3, EnumCreatureType.AMBIENT, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.PLAINS);

        //and here we'd add loot
        LootTableList.register(EntitySimpleRancherGolem.LOOT);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels(){
        RenderingRegistry.registerEntityRenderingHandler(EntitySimpleRancherGolem.class, RenderSimpleRancherGolem.FACTORY);
    }
}
