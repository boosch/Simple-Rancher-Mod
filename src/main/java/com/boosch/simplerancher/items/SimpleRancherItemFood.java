package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.item.ItemFood;

public class SimpleRancherItemFood extends ItemFood implements FlavorText {

    private String name;
    private String flavorText;

    public SimpleRancherItemFood(int amount, float saturation, boolean isWoolfFood, String name){

        super(amount, saturation, isWoolfFood);
        this.name = name;
        this.flavorText = "So this is new...";

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(SimpleRancher.CREATIVE_TAB);

    }

    public SimpleRancherItemFood(int amount, float saturation, boolean isWoolfFood, String name, String flavorText){

        super(amount, saturation, isWoolfFood);
        this.name = name;
        this.flavorText = flavorText;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(SimpleRancher.CREATIVE_TAB);

    }


    public String getFlavorText(){
        return flavorText;
    }

    public void registerItemModel(){

        SimpleRancher.proxy.registerItemRenderer(this, 0, name);
    }

}
