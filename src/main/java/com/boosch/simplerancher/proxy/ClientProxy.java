package com.boosch.simplerancher.proxy;



import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.items.FlavorText;
import com.boosch.simplerancher.util.Reference;


import com.boosch.simplerancher.util.handlers.TooltipHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;


//indicates code that should only ever be run on the client
@Mod.EventBusSubscriber(value= Side.CLIENT, modid=Reference.MOD_ID)
public class ClientProxy extends CommonProxy {



/*
    @Override
    public void init() {
        System.out.println("HEY WE LOADED THE PRE-INIT EVENT BUSSES IN THE CLIENT THINGY");
        MinecraftForge.EVENT_BUS.register(SimpleRancher.proxy);
    }
*/
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

    //TODO Fix the tooltip handler running on both server and client...

    @SubscribeEvent
    public static void modItemsTooltipInjector(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof FlavorText) {
            insertToolTipFlavorText(event, ((FlavorText) event.getItemStack().getItem()).getFlavorText());
        }
    }

    /**
     * Insert a tooltip for an item
     * @param event
     * @param flavorText
     */

    private static void insertToolTipFlavorText(ItemTooltipEvent event, String flavorText){

        if(event.getToolTip().contains(flavorText)){ return;}

        event.getToolTip().add(1, TextFormatting.BLUE + "" + TextFormatting.ITALIC +flavorText);
    }



}
