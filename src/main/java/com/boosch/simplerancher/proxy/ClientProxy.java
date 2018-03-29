package com.boosch.simplerancher.proxy;


import com.boosch.simplerancher.util.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.client.model.ModelLoader;

//indicates code that should only ever be run on the client
public class ClientProxy extends CommonProxy {


    /**
     * Not necessary since mod does not branch from server during init
    @Override
    public void init() {

        MinecraftForge.EVENT_BUS.register(SimpleRancher.proxy);
    }
    **/

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
