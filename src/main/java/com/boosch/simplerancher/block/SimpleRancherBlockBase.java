package com.boosch.simplerancher.block;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.items.FlavorText;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class SimpleRancherBlockBase extends Block implements FlavorText{

    protected String name;
    private String flavorText;

    public SimpleRancherBlockBase(Material material, String name){
        super(material);

        this.name=name;
        this.flavorText= "So this is new...";

        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public SimpleRancherBlockBase(Material material, String name, String flavortext){
        super(material);

        this.name=name;
        this.flavorText= flavortext;

        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public String getFlavorText(){
        return flavorText;
    }

    public void registerItemModel(Item itemBlock){
        SimpleRancher.proxy.registerItemRenderer(itemBlock,0,name);
    }

    public Item createItemBlock(){
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    @Override
    public SimpleRancherBlockBase setCreativeTab(CreativeTabs tab){
        super.setCreativeTab(tab);
        return this;
    }

}
