package com.boosch.simplerancher.items;

import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import scala.Array;


public class ItemReinforcedBoots extends SimpleRancherItemArmor {

    public ItemReinforcedBoots(ArmorMaterial material,  String name){

        super(material, EntityEquipmentSlot.FEET, name);

    }


    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(world.isRemote){return;}

        if(entity instanceof EntityPlayer){
            ItemStack boots = ((EntityPlayer)entity).inventory.armorItemInSlot(0);
            if( boots !=null && boots.getItem() instanceof ItemReinforcedBoots){
                //let's try to apply a movespeed potion
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.getPotionById(1), 1, 1));
            }
        }
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemReinforcedBoots to increase movementSpeed.
     * Cant quite figure out how to get this to work
     * */
    /**
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.FEET)
        {
            multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier( ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
     **/
}
