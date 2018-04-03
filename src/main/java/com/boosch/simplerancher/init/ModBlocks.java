package com.boosch.simplerancher.init;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.block.Pedestal.BlockPedestal;
import com.boosch.simplerancher.block.SimpleRancherBlockOre;
import com.boosch.simplerancher.block.TestCounter.BlockCounter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    //these are my custom blocks, much akin to custom items.
    //these are specifically very simple ores & blocks.

    //my own personal quartz replacement. Unfortunately it does not seem to ordict properly...
    public static SimpleRancherBlockOre QUARTZ_CRYSTAL_ORE = new SimpleRancherBlockOre("ore_quartzcrystal", "oreQuartz", "This looks like it should be smelted").setCreativeTab(SimpleRancher.CREATIVE_TAB);

    //example counter materials
    public static BlockCounter BLOCK_COUNTER = new BlockCounter();

    //example pedestal
    public static BlockPedestal BLOCK_PEDESTAL = new BlockPedestal();


    public static void register(IForgeRegistry<Block> registry){

        registry.registerAll(
                QUARTZ_CRYSTAL_ORE,
                BLOCK_COUNTER,
                BLOCK_PEDESTAL
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry){

        registry.registerAll(
                QUARTZ_CRYSTAL_ORE.createItemBlock(),
                BLOCK_COUNTER.createItemBlock(),
                BLOCK_PEDESTAL.createItemBlock()
        );
    }


    public static void registerModels(){
        QUARTZ_CRYSTAL_ORE.registerItemModel(Item.getItemFromBlock(QUARTZ_CRYSTAL_ORE));
        BLOCK_COUNTER.registerItemModel(Item.getItemFromBlock(BLOCK_COUNTER));
        BLOCK_PEDESTAL.registerItemModel(Item.getItemFromBlock(BLOCK_PEDESTAL));
    }
}
