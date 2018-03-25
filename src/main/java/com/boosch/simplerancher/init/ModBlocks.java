package com.boosch.simplerancher.init;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.block.SimpleRancherBlockOre;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    //these are my custom blocks, much akin to custom items.
    //these are specifically very simple ores & blocks.

    //my own personal quartz replacement. Unfortunately it does not seem to ordict properly...
    public static SimpleRancherBlockOre QUARTZ_CRYSTAL_ORE = new SimpleRancherBlockOre("ore_quartzcrystal", "oreQuartz").setCreativeTab(SimpleRancher.creativeTab);


    public static void register(IForgeRegistry<Block> registry){

        registry.registerAll(
                QUARTZ_CRYSTAL_ORE
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry){

        registry.registerAll(
                QUARTZ_CRYSTAL_ORE.createItemBlock()
        );
    }


    public static void registerModels(){
        QUARTZ_CRYSTAL_ORE.registerItemModel(Item.getItemFromBlock(QUARTZ_CRYSTAL_ORE));
    }
}
