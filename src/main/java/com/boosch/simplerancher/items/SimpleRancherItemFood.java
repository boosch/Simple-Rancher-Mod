package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.item.ItemFood;

public class SimpleRancherItemFood extends ItemFood{

    private String name;

    public SimpleRancherItemFood(int amount, float saturation, boolean isWoolfFood, String name){

        super(amount, saturation, isWoolfFood);
        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(SimpleRancher.creativeTab);

    }

    public void registerItemModel(){

        SimpleRancher.proxy.registerItemRenderer(this, 0, name);
    }

}
