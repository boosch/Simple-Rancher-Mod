package com.boosch.simplerancher.network;

import com.boosch.simplerancher.block.Pedestal.TileEntityPedestal;
import com.boosch.simplerancher.entity.golem.EntitySimpleRancherGolem;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateSimpleRancherGolem implements IMessage{

    /**
     * a static inner Handler class in our PacketUpdateSimpleRancherGolem class that implements IMessageHandler.
     * This interface takes two generic type paramters: the type of the packet that the handler’s handling and
     * the type of the packet that the handler responds with.
     * The first type will be PacketUpdateSimpleRancherGolem and, because we don’t want to respond with a packet, the return
     * packet type will just be IMessage and we’ll return null from the handler method.
     *
     * What we’re going to do in the handler’s onMessage method is get the golem entity from the world and update
     * its AttackTimer via playAttackAnimation. Unfortunately, there’s a caveat to this so it’s a bit more complicated.
     * With Netty (the library Minecraft and Forge use for networking), packets are handled on a different thread
     * that’s not the main thread. Because we’re going to be interacting with and modifying the world, we can’t just
     * do it from a different thread because it could potentially cause a ConcurrentModificationException to be thrown.
     * To deal with this, we’ll call the Minecraft.addScheduledTask method which executes the given Runnable on the
     * main thread as soon as possible, so in this runnable, we can interact with the world.
     *
     * In the runnable, we simply get the golem entity from the client world (Minecraft.getMinecraft().world) and
     * modify tell it to play the animation.
     */
    public static class Handler implements IMessageHandler<PacketUpdateSimpleRancherGolem, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateSimpleRancherGolem message, MessageContext ctx){
            try {
                Minecraft.getMinecraft().addScheduledTask(() -> {

                    EntitySimpleRancherGolem golem = (EntitySimpleRancherGolem) Minecraft.getMinecraft().world.getEntityByID(message.golemID);
                    if(golem != null && golem.getEntityWorld()!= null && golem.getEntityWorld().isRemote)golem.playAttackAnimation();
                });
            }catch(Exception e){
                System.err.println("Handled an exception in the SimpleRancherGolem MessageHandler: "+e.getLocalizedMessage()+"\n"+e.getStackTrace());
            }
            return null;
        }
    }


    //private BlockPos pos;
    //private ItemStack stack;
    //private long lastChangeTime;
    //private EntitySimpleRancherGolem golem;
    private int golemID;

    /**
     * simply a convenience tool to make it easier to set this thing's values given a golem-entity
     * @param golem - the Entity for a specific entity golem
     */
    public PacketUpdateSimpleRancherGolem(EntitySimpleRancherGolem golem) {
        //this.golem = golem;
        this.golemID = golem.getEntityId();
    }

    /**
     * simply a convenience tool to make it easier to set this thing's values given a golem-entity ID
     * @param golemID - the ID for a specific entity golem instance
     */
    public PacketUpdateSimpleRancherGolem(int golemID) {
        this((EntitySimpleRancherGolem)Minecraft.getMinecraft().world.getEntityByID(golemID));
    }

    /**
     * This exists because of how Forge's SimpleImpl for networking creates the entity then populates its fields
     * via a call to fromBytes
     */
    public PacketUpdateSimpleRancherGolem() {
    }

    /**
     * These two functions read and write a ByteBuf obj which sends data over the network in minecraft
     * Because of how serialization works, they must do things in the exact same order on both sides (pack and unpack)
     * in a sort of FIFO model
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf) {
        this.golemID = buf.readInt();
        //this.golem = (EntitySimpleRancherGolem)Minecraft.getMinecraft().world.getEntityByID(golemID);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(golemID);
    }


}
