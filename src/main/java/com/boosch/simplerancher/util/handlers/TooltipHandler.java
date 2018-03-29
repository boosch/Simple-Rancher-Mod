package com.boosch.simplerancher.util.handlers;

import com.boosch.simplerancher.items.FlavorText;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



//@SideOnly(Side.CLIENT)
public class TooltipHandler {


    //TODO Fix the tooltip handler running on both server and client...

    public TooltipHandler(){}

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
