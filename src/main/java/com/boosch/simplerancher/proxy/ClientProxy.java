package com.boosch.simplerancher.proxy;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.TreeFell.util.handlers.TreeHandler;
import com.boosch.simplerancher.items.ItemQuartzEdgedAxe;
import com.boosch.simplerancher.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.minecraft.block.Block.getBlockFromName;

//indicates code that should only ever be run on the client
public class ClientProxy extends CommonProxy {


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


}
