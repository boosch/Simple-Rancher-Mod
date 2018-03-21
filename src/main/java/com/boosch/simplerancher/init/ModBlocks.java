package com.boosch.simplerancher.init;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.block.SimpleRancherBlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    //these are my custom blocks, much akin to custom items.
    //these are specifically very simple ores & blocks.
    public static SimpleRancherBlockOre oreQuartzCrystal = new SimpleRancherBlockOre("ore_quartzcrystal", "oreQuartz").setCreativeTab(SimpleRancher.creativeTab);


    public static void register(IForgeRegistry<Block> registry){

        registry.registerAll(
                oreQuartzCrystal
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry){

        registry.registerAll(
                oreQuartzCrystal.createItemBlock()
        );
    }


    public static void registerModels(){
        oreQuartzCrystal.registerItemModel(Item.getItemFromBlock(oreQuartzCrystal));
    }
}
