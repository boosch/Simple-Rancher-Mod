package com.boosch.simplerancher.items;

import net.minecraftforge.oredict.OreDictionary;

public class SimpleRancherOre extends SimpleRancherItemBase {

    //for OreDict
    private String oreName;

    public SimpleRancherOre(String name, String oreName){
        super(name);
        this.oreName = oreName;
    }

    public void initOreDict(){
        OreDictionary.registerOre(oreName, this);
    }

}
