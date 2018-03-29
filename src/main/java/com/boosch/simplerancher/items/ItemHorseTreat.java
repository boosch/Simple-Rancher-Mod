package com.boosch.simplerancher.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.xml.soap.Text;
import java.util.Random;

@Mod.EventBusSubscriber
public class ItemHorseTreat extends SimpleRancherItemFood {


    private float horseHP;
    private int horseGrowth;

    public ItemHorseTreat(){
        super(2, 0.1F, false, "horsetreat", "Really gets horses in the mood. Too sweet for people.");

        horseHP = 4f;
        horseGrowth = 90;
    }

    @Override
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (worldIn.isRemote) {
            return;
        }

        player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 100, 1));
        player.sendMessage(new TextComponentString(TextFormatting.DARK_RED+ ""+TextFormatting.ITALIC+"Ugh... too sweet. You don't feel so good..."));
    }






    protected static Random rand;

    @SubscribeEvent
    public static void onFeedHorse(PlayerInteractEvent.EntityInteract event){
        EntityPlayer p = event.getEntityPlayer();
        Entity e = event.getTarget();


        //ref AbstractHorse 547
        //ref EntityHorse 292

        /**
         * If the item isn't our item, or the entity isn't a horse, do nothing.
         */
        if(e instanceof AbstractHorse && event.getItemStack().getItem() instanceof ItemHorseTreat){

            //System.out.println(p.getName()+" is interacting with a "+e.getName());

            rand = new Random(System.currentTimeMillis());

            if(!p.capabilities.isCreativeMode){

                AbstractHorse horse = (AbstractHorse)e;
                boolean ateIt= false;


                if(!horse.isTame() && !horse.isChild()) {
                    //horse.makeMad();
                    event.setCancellationResult(EnumActionResult.PASS);
                    horse.makeMad();
                    event.setCanceled(true);
                    return;
                }

                if (horse.getHealth() < horse.getMaxHealth())
                {
                    ateIt=true;
                    //arbitrary number
                    horse.heal(20);
                }

                if(horse.isChild()){
                    horse.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, horse.posX + (double)(rand.nextFloat() * horse.width * 2.0F) - (double)horse.width, horse.posY + 0.5D + (double)(rand.nextFloat() * horse.height), horse.posZ + (double)(rand.nextFloat() * horse.width * 2.0F) - (double)horse.width, 0.0D, 0.0D, 0.0D);

                    if (!horse.world.isRemote)
                    {
                        //arbitrary number...
                        horse.addGrowth(20);
                    }
                    ateIt=true;
                }

                if(horse.getGrowingAge()==0 && !horse.isInLove()){
                    ateIt=true;
                    horse.setInLove(p);
                }


                if(ateIt){
                    event.getItemStack().shrink(1);

                    horse.setEatingHaystack(ateIt);
                    horse.world.playSound((EntityPlayer)null, horse.posX, horse.posY, horse.posZ, SoundEvents.ENTITY_HORSE_EAT, horse.getSoundCategory(), 1.0F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                    event.setCanceled(true);
                    event.setCancellationResult(EnumActionResult.PASS);

                    //System.out.println("The "+e.getName()+" ate the "+ event.getItemStack().getDisplayName());
                }
                else{
                    //System.out.println("The "+e.getName()+" did nto eat the "+event.getItemStack().getDisplayName());
                    event.setCancellationResult(EnumActionResult.PASS);
                    horse.makeMad();
                    event.setCanceled(true);
                }
            }

        }
    }
}
