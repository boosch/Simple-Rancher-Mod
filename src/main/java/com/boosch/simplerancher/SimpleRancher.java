package com.boosch.simplerancher;

import com.boosch.simplerancher.init.ModItems;
import com.boosch.simplerancher.init.ModRecipes;
import com.boosch.simplerancher.proxy.CommonProxy;

import com.boosch.simplerancher.util.Reference;
import com.boosch.simplerancher.util.handlers.ModGuiHandler;
import com.boosch.simplerancher.world.ModWorldGen;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * http://shadowfacts.net/tutorials/forge-modding-112/basic-items/
 */


//this annotation defines this mod's information. It is attached to the starting class of the mod
@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class SimpleRancher {

    @Instance(Reference.MOD_ID)
    public static SimpleRancher instance;

    //creative tab definition
    public static final SimpleRancherCreativeTab CREATIVE_TAB = new SimpleRancherCreativeTab();

    //armor materials
    public static final ItemArmor.ArmorMaterial ARMORMAT_REINFORCED_LEATHER = EnumHelper.addArmorMaterial("REINFORCED_LEATHER", Reference.MOD_ID+":reinforced_leather", 7, new int[]{2, 3, 4, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F ).setRepairItem(new ItemStack(Items.LEATHER));
    public static final ItemTool.ToolMaterial TOOLMAT_QUARTZEDGED = EnumHelper.addToolMaterial("QUARTZ_EDGED", 2, 250, 6.0F, 2.0F, 14 ).setRepairItem(new ItemStack(Items.QUARTZ));



    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;


    //these run before, during and after game load
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        System.out.println(Reference.NAME + " is loading!");
        proxy.preInit();

        GameRegistry.registerWorldGenerator(new ModWorldGen(),3);

        //this registers our GUI handler for all GUIs in the mod
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        ModRecipes.init();

        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit();
    }





    /**
     * Registration Handler for Items
     */




}