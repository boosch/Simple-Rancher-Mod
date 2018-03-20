package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class SimpleRancherItemBase extends Item{

    protected String name;

    public SimpleRancherItemBase(String name){

        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);

        setCreativeTab(SimpleRancher.creativeTab);

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
