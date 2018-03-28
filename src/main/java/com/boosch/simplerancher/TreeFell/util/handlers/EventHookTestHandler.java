package com.boosch.simplerancher.TreeFell.util.handlers;

import com.boosch.simplerancher.TreeFell.Tree;
import com.boosch.simplerancher.items.ItemQuartzEdgedAxe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraft.block.Block.getBlockFromName;


@Mod.EventBusSubscriber
public class EventHookTestHandler {

//    @SubscribeEvent
/**
 * This exists wholly to test if I can actually successfully subscribe to a global event
 *
 *
 *
 *
 *
**/


    /**
        public static void itemPickup(EntityItemPickupEvent event){

            event.getEntity().sendMessage(new TextComponentString("Hey "+event.getEntity().getName()+" you picked up a(n) "+event.getItem().getName()));
        }
    **/

    //@SubscribeEvent
    /**
     * Needs to account for calling our Tree Fell method to kill the whole tree above this block.
     * Needs to allow for log, log2, leaves leaves2 by name... is there a better way?
     */

    /** @TODO verify that all of this code works fine in CommonProxy
    public static void destroyWoodBlock(BlockEvent.BreakEvent event){
        if(event.getState().getBlock() == getBlockFromName("log") ||
                event.getState().getBlock() == getBlockFromName("log2")){

            event.getPlayer().sendMessage(new TextComponentString("Hey, stop that! We need those!  ["+ event.getResult().name() + "] [" +event.getState()+"] ["+event.getPos()+"] blocks!"));
        }
        else{
            event.getPlayer().sendMessage(new TextComponentString("It wasn't a log according to our check :("));

        }
    }

    @SubscribeEvent
    public static void interactWithTree(PlayerInteractEvent event){

        boolean isSneaking = event.getEntityPlayer().isSneaking();

        if(isSneaking) {return;} // they're sneaking, let's do this normally
        //if they're interacting with wood, continue, else, abort!
        if(isWoodenBlock(event.getWorld(), event.getPos())) {

            EntityPlayer p = event.getEntityPlayer();
            Item tool = p.getHeldItemMainhand().getItem();

            if (tool != null && tool instanceof ItemQuartzEdgedAxe) { //let's be sure they're using an axe!

                event.getEntityPlayer().sendMessage(new TextComponentString("... Sweet axe!"));
            } else if (tool != null) {
                event.getEntityPlayer().sendMessage(new TextComponentString("Eww... Why'd you use a " + tool.getUnlocalizedName() + " for that?"));

            }
        }
        else{return;}
    }

    protected static boolean isWoodenBlock(World world, BlockPos blockPos){

        //add any other woodlogblocks to this array to let the quartz-edged-axe chop the whole tree
        Block[] woodblocks = {
                getBlockFromName("log"),
                getBlockFromName("log2")
        };

        Block ub = world.getBlockState(blockPos).getBlock();
        for(Block b : woodblocks){
            if( b == ub )
                return true;
        }

        return false;
    }
**/
}
