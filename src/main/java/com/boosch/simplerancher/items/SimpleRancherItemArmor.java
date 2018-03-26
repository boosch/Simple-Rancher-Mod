package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public class SimpleRancherItemArmor extends ItemArmor {

    private String name;

    public SimpleRancherItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name){
        super(material, 0, slot);

        setRegistryName(name);
        setUnlocalizedName(name);

        setCreativeTab(SimpleRancher.creativeTab);

        this.name = name;
    }

    public void registerItemModel(){

        SimpleRancher.proxy.registerItemRenderer(this, 0, name);
    }
}
