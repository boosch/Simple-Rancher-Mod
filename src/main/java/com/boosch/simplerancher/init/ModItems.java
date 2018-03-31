package com.boosch.simplerancher.init;


import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.items.*;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {


    /**
     * This mod adds the following capabilities
     *  - unique component items for new craftables
     *  - ability to craft
     *      - Saddle (vanilla)
     */

    //Change!

    /** I want to make these things
        @Item DONE leatherstrip - component to saddle, BUCKLED_STRAP,
        @Item DONE IRON_BUCKLE - component to saddle, BUCKLED_STRAP,
        @Item DONE BUCKLED_STRAP - component to saddle,

        @Recipes
            DONE Saddle
            DONE Iron Horse Armor
            DONE Gold Horse Armor
            DONE Diamond Horse Armor

        @ArmorMaterial DONE reinforced_leather - a tougher version of leather armor made by adding straps to leather armor. Slightly stronger than leather, with 1 toughness
        @Armor DONE Reinforced Leather Boots - +armor, +movement speed
        TODO @Armor Reinforced Leather Cap
        TODO @Armor Reinforced Leather Leggings
        TODO @Armor Reinforced Leather Chestpiece



        @ToolMaterial DONE Quartz_edged - repair with quartz
        @Tool quartz_edged_axe - cuts whole trees down!

        @Tool quartz_edged_pickaxe - +50% ore gains




        @Laquer
            TODO add a concept of laquers - dye equivelents for iron armors; parity function with leather dye
            TODO @Item laquer - dye for metallic items
            TODO @??? Make Iron Armor dye-able

        @Block&Ores
            @Item DONE QUARTZ_CRYSTAL
            @Block DONE quartzOre

        @Food
             DONE Flour - Wheat processes into flour
             DONE Dough - Flour + Egg = 2x Dough
             DONE Bread - Cook Dough
             DONE Gnocci - Potato, Flour, Egg, Bowl (better than steak!)
             DONE Sugary Horse Treat - breeds horses

        @

        @Stretch
        Add magic to the world. Based on Thaumcraft. Stretch goals include...
        Every item in the game consists of materia
        @MateriaCommon
        @MateriaRare
        @MateriaSpecial

        @AlchemyFurnace - consume fuel & Items to create materia
        @AlchemyPipe - move materia from source to target

        @AlchemyJarCommon - store materia of a specifc type; pipe interaction
        @AlchemyJarRare - store rare materia; pipe interaction
        @AlchemyJarSpecial - store special materia; pipe interaction

        @AlchemyInfuser - restore item durability via materia consumption; pipe fed
        @AlchemyDuplicator - generates clone of item at cost of materia; pipe fed
        @AlchemyRefiner - generates one tier higher of materia at cost of materia; pipe interaction

        @HungryChest - consumes any item that touches it, large inventory
        @MateriaFurnace - consumes items through input, spits out cooked result

        @MommetEnhancer - consume mommet, material, materia to upgrade a mommet

        @MommetInanimate - ITEM ONLY - a craftable companion that can be given a specific task
                    - Wheat, string, eyes, bones
        @MommetBase - infuse a MommetInanimate with Common Materia
        @MommetClay - infuse a MommetBase with Clay and Common Materia
        @MommetFarm - MommetBase - give hoe, seeds
        @MommetGatherer - MommetBase - assign to drop location
        @MommetChopper - MommetBase - give axe
        @MommetRitualist - MommetClay - give ??? - generates materia
        @MommetRitualistRare - Mommet??? - give ??? - generates materiaRare
        @MommetRitualistSpecial - Mommet??? - give ??? - generates materiaSpecial

     **/


    //tools and materials
    public static SimpleRancherItemBase IRON_BUCKLE = new SimpleRancherItemBase("ironbuckle");//.setCreativeTab(SimpleRancher.CREATIVE_TAB);
    public static SimpleRancherItemBase LEATHER_STRIP = new SimpleRancherItemBase("leatherstrip");//.setCreativeTab(SimpleRancher.CREATIVE_TAB);
    public static SimpleRancherItemBase BUCKLED_STRAP = new SimpleRancherItemBase("buckledstrap");//.setCreativeTab(SimpleRancher.CREATIVE_TAB);
    public static ItemQuartzEdgedAxe QUARTZ_EDGED_AXE = new ItemQuartzEdgedAxe();

    //armor pieces
    public static ItemReinforcedBoots REINFORCED_BOOTS = new ItemReinforcedBoots(SimpleRancher.ARMORMAT_REINFORCED_LEATHER, "reinforced_leather_boots");

    //ores
    //public static SimpleRancherOre QUARTZ_CRYSTAL = new SimpleRancherOre("quartzcrystal", "gemQuartz");


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
