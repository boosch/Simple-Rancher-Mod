package com.boosch.simplerancher.proxy;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraft.block.Block.getBlockFromName;

public interface CommonProxy {

    public void init();
    public void preInit();
    public void postInit();
    public void registerItemRenderer(Item item, int meta, String id);

    /**
     * TreeFell stuff
     */

    public void destroyWoodBlock(BlockEvent.BreakEvent event);
    public void interactWithTree(PlayerInteractEvent event);
}
