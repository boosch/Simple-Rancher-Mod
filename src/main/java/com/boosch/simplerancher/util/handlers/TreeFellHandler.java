package com.boosch.simplerancher.util.handlers;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod.EventBusSubscriber
public class TreeFellHandler {

@SubscribeEvent
/**
 * This exists wholly to test if I can actually successfully subscribe to a global event
 */
    public static void itemPickup(EntityItemPickupEvent event){

        event.getEntity().sendMessage(new TextComponentString("Hey "+event.getEntity().getName()+" you picked up a(n) "+event.getItem().getName()));
    }


}
