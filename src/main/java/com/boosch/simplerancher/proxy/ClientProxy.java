package com.boosch.simplerancher.proxy;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.TreeFell.util.handlers.TreeHandler;
import com.boosch.simplerancher.items.ItemQuartzEdgedAxe;
import com.boosch.simplerancher.items.ItemReinforcedBoots;
import com.boosch.simplerancher.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

import static net.minecraft.block.Block.getBlockFromName;

//indicates code that should only ever be run on the client
public class ClientProxy extends CommonProxy {


    /**
     * Not necessary since mod does not branch from server during init
    @Override
    public void init() {

        MinecraftForge.EVENT_BUS.register(SimpleRancher.proxy);
    }
    **/

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
     * used to override a tooltip on the fly
     * @param event
     */
    @SubscribeEvent
    public void modItemsTooltipInjector(ItemTooltipEvent event) {

        if (event.getItemStack().getItem() instanceof ItemQuartzEdgedAxe) {
            insertToolTipFlavorText(event, "So sharp it can cut down whole trees!");
        }
        if(event.getItemStack().getItem() instanceof ItemReinforcedBoots){
            insertToolTipFlavorText(event, "Sturdy boots for workers who need to hustle.");
        }
    }

    /**
     * Insert a tooltip for an item
     * @param event
     * @param flavorText
     */
    private void insertToolTipFlavorText(ItemTooltipEvent event, String flavorText){

        if(event.getToolTip().contains(flavorText)){ return;}

        List<String> newTooltip = new LinkedList<>();
        newTooltip.add(event.getToolTip().get(0));
        event.getToolTip().remove(0);
        newTooltip.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.ITALIC +flavorText);

        for(String str : event.getToolTip()){
            newTooltip.add(str);
        }

        event.getToolTip().clear();
        event.getToolTip().addAll(newTooltip);
    }


}
