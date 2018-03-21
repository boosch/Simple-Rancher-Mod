package com.boosch.simplerancher;

import com.boosch.simplerancher.init.ModItems;
import com.boosch.simplerancher.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;


public class SimpleRancherCreativeTab extends CreativeTabs{

    public SimpleRancherCreativeTab(){
        super(Reference.MOD_ID);
        setBackgroundImageName("item_search.png");

    }

    @Override
    public ItemStack getTabIconItem(){

        return new ItemStack(ModItems.buckledstrap);
    }

    @Override
    public boolean hasSearchBar(){
        return true;
    }
}
