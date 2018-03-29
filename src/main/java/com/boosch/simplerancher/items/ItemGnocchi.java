package com.boosch.simplerancher.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class ItemGnocchi extends SimpleRancherItemFood {

    public ItemGnocchi(){
        super(18, 0.18F, false, "gnocchi", "Wait... How did this even get cooked?");

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

            //if there's room in the player's inventory, add a bowl, else, spawn a bowl at the player's feet
            if( !((EntityPlayer) entityLiving).inventory.addItemStackToInventory(new ItemStack(Items.BOWL))){
                //hrm... we failed, let's spawn a bowl then... Remember to only spawn it on the server!
                if(!worldIn.isRemote) entityLiving.entityDropItem(new ItemStack(Items.BOWL),1f);
            }
        }

        return new ItemStack(stack.getItem(), stack.getCount()-1);
    }



}
