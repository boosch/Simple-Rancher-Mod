package com.boosch.simplerancher.items;

import com.boosch.simplerancher.SimpleRancher;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemGnocchi extends SimpleRancherItemFood{

    public ItemGnocchi (){
        super(18, 0.18F, false, "gnocchi");

    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);

        if(entityLiving instanceof EntityPlayer){
            ((EntityPlayer) entityLiving).inventory.addItemStackToInventory(new ItemStack(Items.BOWL));
        }
        return new ItemStack(stack.getItem(), stack.getCount());
    }

}
