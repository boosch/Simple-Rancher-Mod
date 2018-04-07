package com.boosch.simplerancher.proxy;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.TreeFell.util.handlers.TreeHandler;
import com.boosch.simplerancher.init.ModEntities;
import com.boosch.simplerancher.items.ItemQuartzEdgedAxe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.UUID;


//indicates code that should only ever be run on a server
public class ServerProxy extends CommonProxy {

    /**
     * Not necessary since mod doesn't branch from server at init
     */

    /*
    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(SimpleRancher.proxy);
        System.out.println("HEY WE LOADED THE EVENT BUSSES IN THE SERVER THINGY");
    }
*/
    @Override
    public void registerRenderers(){}

    @Override
    public void preInit() {
        ModEntities.init();
    }

    @Override
    public void postInit() {

    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {

    }


    @Override
    /**
     * Check if the player is interacting with a tree. If so, let's store this interaction set.
     * We need to clear the interaction set if they stop or if they destroy the block!
     */
    public void interactWithTree(PlayerInteractEvent event){

        boolean isSneaking = event.getEntityPlayer().isSneaking();
        int logCount;

        if(isSneaking) {return;} // they're sneaking, let's do this normally

        if(isWoodenBlock(event.getWorld(), event.getPos())) { //if they're interacting with wood, continue, else, abort!

            EntityPlayer p = event.getEntityPlayer();
            ItemStack tool = p.getHeldItemMainhand();

            if (tool.getItem() instanceof ItemQuartzEdgedAxe) { //let's be sure they're using our axe!

                int axeCurrentDurability = tool.getMaxDamage() - tool.getItemDamage();
                UUID pid = p.getPersistentID();

                /**
                 * Check if this interaction already exists by vetting the PlayerInteractionSession against this current interaction
                 */
                if(tf_PlayerData.containsKey(pid) && //pid has an interaction
                        tf_PlayerData.get(pid).pos.equals(event.getPos()) && //the interaction pid has is at this log
                        tf_PlayerData.get(pid).axeDurability == axeCurrentDurability){ //the interaction pid has is still valid (axe durability hasn't changed)

                    return; // we already have an interaction AND it's this interaction!
                }

                //this is a new interaction!
                treeHandler = new TreeHandler();
                logCount = treeHandler.AnalyzeTree(event.getWorld(), event.getPos(), p);

                /**
                 * if we don't have enough durability to complete the current interaction, we stop.
                 */
                if(tool.isItemStackDamageable() && axeCurrentDurability < logCount){
                    tf_PlayerData.remove(p.getPersistentID());
                    return;
                }

                /**
                 * The player has successfully started a new interaction - add it to the hashSet
                 */
                if(logCount>1){
                    tf_PlayerData.put(p.getPersistentID(), new PlayerInteractionSession(event.getPos(), logCount, axeCurrentDurability));
                }else{ //there's only one log, so there's no need to keep the Interaction session alive for this player
                    tf_PlayerData.remove(p.getPersistentID());
                }

            }
            else{
                // well, whatever they did it with, it wasn't our axe...
                tf_PlayerData.remove(p.getPersistentID()); //I don't think this should ever be necessary...
            }
        }
        else{return;}
    }


}
