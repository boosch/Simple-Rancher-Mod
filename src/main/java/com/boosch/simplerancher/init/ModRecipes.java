package com.boosch.simplerancher.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;


/**
 * Example of recipes which return items: https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/8c691d6081188f53744c4394078b656847ae6fd6/src/main/java/choonster/testmod3/crafting/recipe/ShapelessCuttingRecipe.java
 */

public class ModRecipes {

    public static void init(){

        //allow smelting of QuartzCrystalOre to make QuartzCrystal
        GameRegistry.addSmelting(ModBlocks.QUARTZ_CRYSTAL_ORE, new ItemStack(Items.QUARTZ), 0.7F); // ModItems.QUARTZ_CRYSTAL), 0.7f);

        //allow cooking of foods
        GameRegistry.addSmelting(ModItems.RAW_DOUGH, new ItemStack(Items.BREAD), 0.7F);

        //init various oreDict items & blocks

        //items
        //ModItems.QUARTZ_CRYSTAL.initOreDict();

        //blocks
        ModBlocks.QUARTZ_CRYSTAL_ORE.initOreDict();

    }
}
