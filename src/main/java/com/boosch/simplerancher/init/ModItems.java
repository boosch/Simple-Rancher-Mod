package com.boosch.simplerancher.init;


import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.items.SimpleRancherItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {


    /**
     * This mod adds the following capabilities
     *  - unique component items for new craftables
     *  - ability to craft
     *      - Saddle (vanilla)
     */

    /** I want to make these things
        @Item DONE leatherstripg - component to saddle, buckledstrap,
        @Item DONE ironbuckle - component to saddle, buckledstrap,
        @Item DONE buckledstrap - component to saddle,

        @Item toolshaft - component to pitchfork
        @item pitchforkprongs - component to pitchfork

        @Armor sturdyworkboots - +armor, +movement speed, +blockwalk

        @Tool pitchfork - increases crop yield when used to break wheat; +dmg to zombies
        @Tool treefellingaxe - cuts whole trees down!

     **/


    public static SimpleRancherItemBase ironbuckle = new SimpleRancherItemBase("ironbuckle");//.setCreativeTab(SimpleRancher.creativeTab);
    public static SimpleRancherItemBase leatherstrip = new SimpleRancherItemBase("leatherstrip");//.setCreativeTab(SimpleRancher.creativeTab);
    public static SimpleRancherItemBase buckledstrap = new SimpleRancherItemBase("buckledstrap");//.setCreativeTab(SimpleRancher.creativeTab);

    public static void register(IForgeRegistry<Item> registry){
        registry.registerAll(
                ironbuckle,
                leatherstrip,
                buckledstrap
        );

    }

    public static void registerModels(){
        ironbuckle.registerItemModel();
        leatherstrip.registerItemModel();
        buckledstrap.registerItemModel();
    }

}
