package com.boosch.simplerancher.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class SimpleRancherBlockOre extends SimpleRancherBlockBase{

    public SimpleRancherBlockOre(String name){
        super(Material.ROCK, name);

        setHardness(3f);
        setResistance(5f);
    }

    @Override
    public SimpleRancherBlockOre setCreativeTab(CreativeTabs tab){
        super.setCreativeTab(tab);
        return this;
    }
}
