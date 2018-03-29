package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.item.Item;


public class SimpleRancherBase extends Item implements FlavorText {

    protected String name;
    protected String flavorText;

    public SimpleRancherBase(String name){

        this.name = name;
        this.flavorText="So this is new...";

        setUnlocalizedName(name);
        setRegistryName(name);

        setCreativeTab(SimpleRancher.CREATIVE_TAB);

    }

    public SimpleRancherBase(String name, String flavorText){

        this.name = name;
        this.flavorText = flavorText;
        setUnlocalizedName(name);
        setRegistryName(name);

        setCreativeTab(SimpleRancher.CREATIVE_TAB);

    }

    public String getFlavorText(){
        return flavorText;
    }

    public void registerItemModel(){
        SimpleRancher.proxy.registerItemRenderer(this, 0, name);
    }


    /*
    @Override
    public SimpleRancherBase setCreativeTab(CreativeTabs tab){
        super.setCreativeTab(tab);
        return this;
    }
    */
}
