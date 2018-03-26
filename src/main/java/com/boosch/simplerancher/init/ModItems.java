package com.boosch.simplerancher.init;


import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.items.*;
import net.minecraft.inventory.EntityEquipmentSlot;
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

        @Item toolshaft - component to pitchfork
        @item pitchforkprongs - component to pitchfork

        @ArmorMaterial DONE reinforced_leather - a tougher version of leather armor made by adding straps to leather armor. Slightly stronger than leather, with 1 toughness
        @Armor IN_PROGRESS Reinforced Leather Boots - +armor, +movement speed
        @TODO fix movement speed issue
        @Armor Reinforced Leather Cap
        @Armor Reinforced Leather Leggings
        @Armor Reinforced Leather Chestpiece
        @TODO check if repairing works for these items

        @Tool pitchfork - increases crop yield when used to break wheat; +dmg to zombies
        @Tool treefellingaxe - cuts whole trees down!

        @Block&Ores
            @Item DONE QUARTZ_CRYSTAL
            @Block DONE quartzOre
            @BlockItem (?) QUARTZ_CRYSTAL

        @Food DONE Flour - Wheat processes into flour
        @Food DONE Dough - Flour + Egg = 2x Dough
        @Food DONE Bread - Cook Dough
        @Food Gnocci - Potato, Flour, Egg, Bowl (better than steak!)


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
    public static SimpleRancherItemBase IRON_BUCKLE = new SimpleRancherItemBase("ironbuckle");//.setCreativeTab(SimpleRancher.creativeTab);
    public static SimpleRancherItemBase LEATHER_STRIP = new SimpleRancherItemBase("leatherstrip");//.setCreativeTab(SimpleRancher.creativeTab);
    public static SimpleRancherItemBase BUCKLED_STRAP = new SimpleRancherItemBase("buckledstrap");//.setCreativeTab(SimpleRancher.creativeTab);

    //armor pieces
    public static ItemReinforcedBoots REINFORCED_BOOTS = new ItemReinforcedBoots(SimpleRancher.reinforcedLeatherArmorMaterial, "reinforced_leather_boots");

    //ores
    public static SimpleRancherItemOre QUARTZ_CRYSTAL = new SimpleRancherItemOre("quartzcrystal", "gemQuartz");


    //food-ingredients
    public static SimpleRancherItemBase RAW_DOUGH = new SimpleRancherItemBase("rawdough");
    public static SimpleRancherItemBase FLOUR = new SimpleRancherItemBase("flour");

    //foods
    public static ItemGnocchi GNOCCHI = new ItemGnocchi();

    public static void register(IForgeRegistry<Item> registry){
        registry.registerAll(
                IRON_BUCKLE,
                LEATHER_STRIP,
                BUCKLED_STRAP,

                QUARTZ_CRYSTAL,

                REINFORCED_BOOTS,

                RAW_DOUGH,
                FLOUR,
                GNOCCHI
        );

    }

    public static void registerModels(){
        IRON_BUCKLE.registerItemModel();
        LEATHER_STRIP.registerItemModel();
        BUCKLED_STRAP.registerItemModel();
        QUARTZ_CRYSTAL.registerItemModel();
        REINFORCED_BOOTS.registerItemModel();
        RAW_DOUGH.registerItemModel();
        FLOUR.registerItemModel();
        GNOCCHI.registerItemModel();
    }

}
