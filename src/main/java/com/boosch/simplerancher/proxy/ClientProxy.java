package com.boosch.simplerancher.proxy;



import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.util.Reference;


import com.boosch.simplerancher.util.handlers.TooltipHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;


//indicates code that should only ever be run on the client
public class ClientProxy extends CommonProxy {




    @Override
    public void init() {

        MinecraftForge.EVENT_BUS.register(SimpleRancher.proxy);

        TooltipHandler tth = new TooltipHandler();
        MinecraftForge.EVENT_BUS.register(tth);
    }


    @Override
    public void preInit() {

    }

    @Override
    public void postInit() {

    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MOD_ID+":"+id, "inventory"));
    }



}
