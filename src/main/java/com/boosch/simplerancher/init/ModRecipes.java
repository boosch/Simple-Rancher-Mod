package com.boosch.simplerancher.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init(){

        //allow smelting of QuartzCrystalOre to make QuartzCrystal
        GameRegistry.addSmelting(ModBlocks.QUARTZ_CRYSTAL_ORE, new ItemStack(Items.QUARTZ), 0.7F); // ModItems.QUARTZ_CRYSTAL), 0.7f);

        //init various oreDict items & blocks

        //items
        ModItems.QUARTZ_CRYSTAL.initOreDict();

        //blocks
        ModBlocks.QUARTZ_CRYSTAL_ORE.initOreDict();

    }
}
