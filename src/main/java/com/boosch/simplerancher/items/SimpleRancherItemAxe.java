package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import jline.TerminalFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class SimpleRancherItemAxe extends ItemAxe implements FlavorText{

    private String name;
    private String flavorText;

    public SimpleRancherItemAxe(ToolMaterial material, float damage, float speed, String name){
        super(material, damage, speed);

        setRegistryName(name);
        setUnlocalizedName(name);
        this.name= name;
        this.flavorText="So this is new...";
    }

    public SimpleRancherItemAxe(ToolMaterial material, float damage, float speed, String name, String flavorText){
        super(material, damage, speed);

        setRegistryName(name);
        setUnlocalizedName(name);
        this.name= name;
        this.flavorText=flavorText;
    }

    public String getFlavorText(){
        return flavorText;
    }



    public void registerItemModel(){
        SimpleRancher.proxy.registerItemRenderer(this, 0, name);
    }

}
