package com.boosch.simplerancher.proxy;

import com.boosch.simplerancher.TreeFell.util.handlers.TreeHandler;
import com.boosch.simplerancher.items.ItemQuartzEdgedAxe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.minecraft.block.Block.getBlockFromName;

//indicates code that should only ever be run on a server
public class ServerProxy implements CommonProxy {

    @Override
    public void init() {

    }

    @Override
    public void preInit() {

    }

    @Override
    public void postInit() {

    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {

    }


    /**
     * TreeFell stuff!
     *
     *
     * Everything below here should be literally 100% from Client Proxy
     *
     *
     *
     */

    class PlayerInteractionSession{
        public BlockPos pos;
        public float logCount;
        public int axeDurability;

        /**
         *
         * @param pos - the position of the block of the interaction
         * @param logCount - the number of logs to be broken by this interaction
         * @param axeDurability - the amount of durability.... in the axe? required by the axe?
         */
        public PlayerInteractionSession(BlockPos pos, float logCount, int axeDurability){
            this.pos = pos;
            this.logCount=logCount;
            this.axeDurability = axeDurability;
        }
    }

    public static Map<UUID, Boolean> tf_PlayerPrintNames = new HashMap<>();
    protected static Map<UUID, PlayerInteractionSession> tf_PlayerData = new HashMap<>();
    protected TreeHandler treeHandler;


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

                //event.getEntityPlayer().sendMessage(new TextComponentString("... Sweet axe!"));

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

    @Override
    @SubscribeEvent
    /**
     * If the player tries to break a block, check if this player is trying to break a treeblock that we're tracking a session for.
     * If so, then adjust the breakspeed to match the difficulty of the tree through a linear formula
     */
    public void breakingWoodBlock(PlayerEvent.BreakSpeed speed){
        UUID pid = speed.getEntityPlayer().getPersistentID();
        if(tf_PlayerData.containsKey(pid)){

            BlockPos pos = tf_PlayerData.get(pid).pos;

            if(pos.equals(speed.getPos()) ){
                speed.setNewSpeed(speed.getOriginalSpeed() / (tf_PlayerData.get(pid).logCount) / 2.0f);
            }
            else{ //revert the breakspeed in case we goofed somehow
                speed.setNewSpeed(speed.getOriginalSpeed());
            }
        }
    }

    @Override
    @SubscribeEvent
    public void destroyWoodBlock(BlockEvent.BreakEvent event){

        EntityPlayer p = event.getPlayer();

        if( tf_PlayerData.containsKey(p.getPersistentID())){
            BlockPos pos = tf_PlayerData.get(p.getPersistentID()).pos;

            if(pos.equals(event.getPos())){
                //TODO destroy the tree from the treehandler



                /**
                 * burst damage the axe based on the size of the tree
                 */
                if(!p.isCreative() && p.getHeldItemMainhand().isItemStackDamageable()){
                    int newAxeDurability = p.getHeldItemMainhand().getItemDamage() + (int)(tf_PlayerData.get(p.getPersistentID()).logCount * 1.5);
                    p.getHeldItemMainhand().setItemDamage(newAxeDurability);
                }
            }
        }
        if( isWoodenBlock(event.getWorld(), event.getPos())){

            event.getPlayer().sendMessage(new TextComponentString("Hey, stop that! We need those!  ["+ event.getResult().name() + "] [" +event.getState()+"] ["+event.getPos()+"] blocks!"));
        }
        else{
            event.getPlayer().sendMessage(new TextComponentString("It wasn't a log according to our check :("));

        }
    }

    /**
     *
     * @param world - the world that the block exists in
     * @param blockPos - the position of the block in question
     * @return
     */
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

}
