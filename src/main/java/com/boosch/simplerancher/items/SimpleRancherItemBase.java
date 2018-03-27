package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.item.Item;


public class SimpleRancherItemBase extends Item{

    protected String name;

    public SimpleRancherItemBase(String name){

        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);

        setCreativeTab(SimpleRancher.CREATIVE_TAB);

    }

    public void registerItemModel(){
        SimpleRancher.proxy.registerItemRenderer(this, 0, name);
    }


    /*
    @Override
    public SimpleRancherItemBase setCreativeTab(CreativeTabs tab){
        super.setCreativeTab(tab);
        return this;
    }
    */
}
