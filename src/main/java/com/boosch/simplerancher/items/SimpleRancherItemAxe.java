package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class SimpleRancherItemAxe extends ItemAxe {

    private String name;

    public SimpleRancherItemAxe(ToolMaterial material, float damage, float speed, String name){
        super(material, damage, speed);

        setRegistryName(name);
        setUnlocalizedName(name);
        this.name= name;
    }

    public void registerItemModel(){
        SimpleRancher.proxy.registerItemRenderer(this, 0, name);
    }

}
