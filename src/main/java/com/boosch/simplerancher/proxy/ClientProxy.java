package com.boosch.simplerancher.proxy;

import com.boosch.simplerancher.items.FlavorText;
import com.boosch.simplerancher.items.ItemGnocchi;
import com.boosch.simplerancher.items.ItemQuartzEdgedAxe;
import com.boosch.simplerancher.items.ItemReinforcedBoots;
import com.boosch.simplerancher.util.Reference;
import jline.TerminalFactory;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

        if (event.getItemStack().getItem() instanceof FlavorText) {
            insertToolTipFlavorText(event, ((FlavorText) event.getItemStack().getItem()).getFlavorText());
        }
    }

    /**
     * Insert a tooltip for an item
     * @param event
     * @param flavorText
     */
    private void insertToolTipFlavorText(ItemTooltipEvent event, String flavorText){

        if(event.getToolTip().contains(flavorText)){ return;}

        event.getToolTip().add(1, TextFormatting.BLUE + "" + TextFormatting.ITALIC +flavorText);
    }


}
