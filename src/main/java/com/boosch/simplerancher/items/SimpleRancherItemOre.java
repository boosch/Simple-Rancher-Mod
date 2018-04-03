package com.boosch.simplerancher.items;

import net.minecraftforge.oredict.OreDictionary;

public class SimpleRancherItemOre extends SimpleRancherItemBase {

    //for OreDict
    private String oreName;

    public SimpleRancherItemOre(String name, String oreName){
        super(name);
        this.oreName = oreName;
    }

    public void initOreDict(){
        OreDictionary.registerOre(oreName, this);
    }

}
