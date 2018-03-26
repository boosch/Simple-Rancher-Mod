package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemGnocchi extends ItemFood{

    public ItemGnocchi (){
        super(18, 0.18F, false);

        setUnlocalizedName("gnocchi");
        setRegistryName("gnocchi");
        setCreativeTab(SimpleRancher.creativeTab);

    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL);
    }

    public void registerItemModel(){
        SimpleRancher.proxy.registerItemRenderer(this, 0,"gnocchi");
    }

    public void initOreDict(){
        OreDictionary.registerOre("foodGnocchi", this);
    }
}
