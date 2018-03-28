package com.boosch.simplerancher.proxy;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.items.ItemQuartzEdgedAxe;
import com.boosch.simplerancher.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.minecraft.block.Block.getBlockFromName;

//indicates code that should only ever be run on the client
public class ClientProxy implements CommonProxy {


    @Override
    public void init() {

        MinecraftForge.EVENT_BUS.register(SimpleRancher.proxy);
    }

    @Override
    public void preInit() {

    }

    @Override
    public void postInit() {

    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MOD_ID+":"+id, "inventory"));
    }


    /**
     *
     *
     *
     *
     * TreeFell Stuff is here
     *
     *
     *
     */

    /**
     * A helper class to allow for a single interaction to be tracked instead of risking super-spam during a long interaction by a player.
     */
    class PlayerInteractionSession{
        public BlockPos pos;
        public float logCount;
        public int axeDurability;

        public PlayerInteractionSession(BlockPos pos, float logCount, int axeDurability){
            this.pos = pos;
            this.logCount=logCount;
            this.axeDurability = axeDurability;
        }
    }

    public static Map<UUID, Boolean> playerPrintNames = new HashMap<>();
    protected static Map<UUID, PlayerInteractionSession> playerData = new HashMap<>();


    @Override
    @SubscribeEvent
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
            Item tool = p.getHeldItemMainhand().getItem();

            if (tool instanceof ItemQuartzEdgedAxe) { //let's be sure they're using our axe!

                event.getEntityPlayer().sendMessage(new TextComponentString("... Sweet axe!"));
            }
            else{
                // well, whatever they did it with, it wasn't our axe...
            }
        }
        else{return;}
    }



    @Override
    @SubscribeEvent
    public void destroyWoodBlock(BlockEvent.BreakEvent event){
        if( isWoodenBlock(event.getWorld(), event.getPos())){

            event.getPlayer().sendMessage(new TextComponentString("Hey, stop that! We need those!  ["+ event.getResult().name() + "] [" +event.getState()+"] ["+event.getPos()+"] blocks!"));
        }
        else{
            event.getPlayer().sendMessage(new TextComponentString("It wasn't a log according to our check :("));

        }
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
}
