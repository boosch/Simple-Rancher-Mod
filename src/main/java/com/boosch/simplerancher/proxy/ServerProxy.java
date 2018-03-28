package com.boosch.simplerancher.proxy;

import com.boosch.simplerancher.items.ItemQuartzEdgedAxe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import static com.boosch.simplerancher.proxy.ClientProxy.isWoodenBlock;
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

    @Override
    public void destroyWoodBlock(BlockEvent.BreakEvent event){

    }

    @Override
    public void interactWithTree(PlayerInteractEvent event){
/**
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
        else{return;}   **/
    }

}
