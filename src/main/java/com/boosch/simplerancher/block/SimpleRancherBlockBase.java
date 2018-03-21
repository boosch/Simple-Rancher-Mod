package com.boosch.simplerancher.block;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class SimpleRancherBlockBase extends Block {

    protected String name;

    public SimpleRancherBlockBase(Material material, String name){
        super(material);

        this.name=name;

        setUnlocalizedName(name);
        setRegistryName(name);
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
