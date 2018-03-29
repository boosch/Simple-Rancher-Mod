package com.boosch.simplerancher.block;

import com.boosch.simplerancher.items.FlavorText;
import jline.TerminalFactory;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;

public class SimpleRancherBlockOre extends SimpleRancherBlockBase implements FlavorText{

    //for oreDict
    private String oreName;
    private String flavorText;

    public SimpleRancherBlockOre(String name, String oreName){
        super(Material.ROCK, name);

        this.oreName = oreName;
        this.flavorText="So this is new...";

        setHardness(3f);
        setResistance(5f);
    }

    public SimpleRancherBlockOre(String name, String oreName, String flavorText){
        super(Material.ROCK, name);

        this.oreName = oreName;
        this.flavorText=flavorText;

        setHardness(3f);
        setResistance(5f);
    }

    public String getFlavorText(){
        return flavorText;
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
