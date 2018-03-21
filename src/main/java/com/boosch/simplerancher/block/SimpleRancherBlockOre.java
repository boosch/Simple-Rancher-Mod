package com.boosch.simplerancher.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;

public class SimpleRancherBlockOre extends SimpleRancherBlockBase{

    //for oreDict
    private String oreName;

    public SimpleRancherBlockOre(String name, String oreName){
        super(Material.ROCK, name);

        this.oreName = oreName;

        setHardness(3f);
        setResistance(5f);
    }


    public void initOreDict(){

        OreDictionary.registerOre(oreName, this);
    }

    @Override
    public SimpleRancherBlockOre setCreativeTab(CreativeTabs tab){
        super.setCreativeTab(tab);
        return this;
    }
}
