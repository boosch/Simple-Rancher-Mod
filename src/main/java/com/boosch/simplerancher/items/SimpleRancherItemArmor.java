package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class SimpleRancherItemArmor extends ItemArmor implements FlavorText{

    private String name;
    private String flavorText;

    public SimpleRancherItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name){
        super(material, 0, slot);

        setRegistryName(name);
        setUnlocalizedName(name);

        setCreativeTab(SimpleRancher.CREATIVE_TAB);

        this.name = name;
        this.flavorText = "So this is new...";
    }


    public SimpleRancherItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name, String flavorText){
        super(material, 0, slot);

        setRegistryName(name);
        setUnlocalizedName(name);

        setCreativeTab(SimpleRancher.CREATIVE_TAB);

        this.name = name;
        this.flavorText = flavorText;
    }

    public String getFlavorText(){
        return flavorText;
    }

    public void registerItemModel(){

        SimpleRancher.proxy.registerItemRenderer(this, 0, name);
    }
}
