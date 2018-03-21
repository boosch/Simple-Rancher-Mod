package com.boosch.simplerancher.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init(){

        //allow smelting of QuartzCrystalOre to make QuartzCrystal
        GameRegistry.addSmelting(ModBlocks.oreQuartzCrystal, new ItemStack(ModItems.quartzCrystal), 0.7f);

        //init various oreDict items & blocks

        //items
        ModItems.quartzCrystal.initOreDict();

        //blocks
        ModBlocks.oreQuartzCrystal.initOreDict();

    }
}
