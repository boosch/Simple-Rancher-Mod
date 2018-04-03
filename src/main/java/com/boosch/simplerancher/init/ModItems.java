package com.boosch.simplerancher.init;


import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.items.*;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {


    //tools and materials
    public static SimpleRancherItemBase IRON_BUCKLE = new SimpleRancherItemBase("ironbuckle");//.setCreativeTab(SimpleRancher.CREATIVE_TAB);
    public static SimpleRancherItemBase LEATHER_STRIP = new SimpleRancherItemBase("leatherstrip");//.setCreativeTab(SimpleRancher.CREATIVE_TAB);
    public static SimpleRancherItemBase BUCKLED_STRAP = new SimpleRancherItemBase("buckledstrap");//.setCreativeTab(SimpleRancher.CREATIVE_TAB);
    public static ItemQuartzEdgedAxe QUARTZ_EDGED_AXE = new ItemQuartzEdgedAxe();

    //armor pieces
    public static ItemReinforcedBoots REINFORCED_BOOTS = new ItemReinforcedBoots(SimpleRancher.ARMORMAT_REINFORCED_LEATHER, "reinforced_leather_boots");

    //ores
    //public static SimpleRancherItemOre QUARTZ_CRYSTAL = new SimpleRancherItemOre("quartzcrystal", "gemQuartz");


    //food-ingredients
    public static SimpleRancherItemBase RAW_DOUGH = new SimpleRancherItemBase("rawdough");
    public static SimpleRancherItemBase FLOUR = new SimpleRancherItemBase("flour");

    //foods
    public static ItemGnocchi GNOCCHI = new ItemGnocchi();
    public static ItemHorseTreat HORSETREAT = new ItemHorseTreat();

    //golems
    public static ItemGolem GOLEM_STRAW = new ItemGolem("golem_straw","straw", "You should probably put him down");

    public static void register(IForgeRegistry<Item> registry){
        registry.registerAll(
                IRON_BUCKLE,
                LEATHER_STRIP,
                BUCKLED_STRAP,

    //            QUARTZ_CRYSTAL,

                REINFORCED_BOOTS,

                RAW_DOUGH,
                FLOUR,
                GNOCCHI,
                HORSETREAT,

                QUARTZ_EDGED_AXE,

                GOLEM_STRAW
        );

    }

    public static void registerModels(){
  //    QUARTZ_CRYSTAL.registerItemModel();

        IRON_BUCKLE.registerItemModel();
        LEATHER_STRIP.registerItemModel();
        BUCKLED_STRAP.registerItemModel();

        REINFORCED_BOOTS.registerItemModel();

        RAW_DOUGH.registerItemModel();
        FLOUR.registerItemModel();
        GNOCCHI.registerItemModel();
        HORSETREAT.registerItemModel();

        QUARTZ_EDGED_AXE.registerItemModel();

        GOLEM_STRAW.registerItemModel();
    }

}
