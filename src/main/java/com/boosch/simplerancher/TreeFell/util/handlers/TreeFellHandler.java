package com.boosch.simplerancher.TreeFell.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraft.block.Block.getBlockFromName;


@Mod.EventBusSubscriber
public class TreeFellHandler {

//    @SubscribeEvent
/**
 * This exists wholly to test if I can actually successfully subscribe to a global event

    public static void itemPickup(EntityItemPickupEvent event){

        event.getEntity().sendMessage(new TextComponentString("Hey "+event.getEntity().getName()+" you picked up a(n) "+event.getItem().getName()));
    }
**/

    @SubscribeEvent
    /**
     * Needs to account for calling our Tree Fell method to kill the whole tree above this block.
     * Needs to allow for log, log2, leaves leaves2 by name... is there a better way?
     */
    public static void treeBroken(BlockEvent.BreakEvent event){
        if(event.getState().getBlock() == getBlockFromName("log") ||
                event.getState().getBlock() == getBlockFromName("log2")){

            event.getPlayer().sendMessage(new TextComponentString("Hey, stop that! We need those!  ["+ event.getResult().name() + "] [" +event.getState()+"] ["+event.getPos()+"] blocks!"));
        }
        else{
            event.getPlayer().sendMessage(new TextComponentString("It wasn't a log according to our check :("));

        }
    }

}
