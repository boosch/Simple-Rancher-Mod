package com.boosch.simplerancher.init;


import com.boosch.simplerancher.items.SimpleRancherItemBase;
import com.boosch.simplerancher.items.SimpleRancherItemOre;
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
        @Item DONE leatherstripg - component to saddle, BUCKLED_STRAP,
        @Item DONE IRON_BUCKLE - component to saddle, BUCKLED_STRAP,
        @Item DONE BUCKLED_STRAP - component to saddle,

        @Item toolshaft - component to pitchfork
        @item pitchforkprongs - component to pitchfork

        @Armor sturdyworkboots - +armor, +movement speed, +blockwalk

        @Tool pitchfork - increases crop yield when used to break wheat; +dmg to zombies
        @Tool treefellingaxe - cuts whole trees down!

        @Block&Ores
            @Item DONE QUARTZ_CRYSTAL
            @Block quartzOre
            @BlockItem (?) QUARTZ_CRYSTAL

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

    //ores and other
    public static SimpleRancherItemOre QUARTZ_CRYSTAL = new SimpleRancherItemOre("quartzcrystal", "gemQuartz");

    public static void register(IForgeRegistry<Item> registry){
        registry.registerAll(
                IRON_BUCKLE,
                LEATHER_STRIP,
                BUCKLED_STRAP,

                QUARTZ_CRYSTAL
        );

    }

    public static void registerModels(){
        IRON_BUCKLE.registerItemModel();
        LEATHER_STRIP.registerItemModel();
        BUCKLED_STRAP.registerItemModel();
        QUARTZ_CRYSTAL.registerItemModel();
    }

}
