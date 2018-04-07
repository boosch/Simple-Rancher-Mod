package com.boosch.simplerancher.network;

import com.boosch.simplerancher.block.Pedestal.TileEntityPedestal;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * This governs the packet to update the client that a change has occurred to a pedestal tile entity
 * on the server.
 */
public class PacketUpdatePedestal implements IMessage {

    /**
     * a static inner Handler class in our PacketUpdatePedestal class that implements IMessageHandler.
     * This interface takes two generic type paramters: the type of the packet that the handler’s handling and
     * the type of the packet that the handler responds with.
     * The first type will be PacketUpdatePedestal and, because we don’t want to respond with a packet, the return
     * packet type will just be IMessage and we’ll return null from the handler method.
     *
     * What we’re going to do in the handler’s onMessage method is get the tile entity from the world and update
     * its inventory and lastChangeTime. Unfortunately, there’s a caveat to this so it’s a bit more complicated.
     * With Netty (the library Minecraft and Forge use for networking), packets are handled on a different thread
     * that’s not the main thread. Because we’re going to be interacting with and modifying the world, we can’t just
     * do it from a different thread because it could potentially cause a ConcurrentModificationException to be thrown.
     * To deal with this, we’ll call the Minecraft.addScheduledTask method which executes the given Runnable on the
     * main thread as soon as possible, so in this runnable, we can interact with the world.
     *
     * In the runnable, we simply get the tile entity from the client world (Minecraft.getMinecraft().world) and
     * modify its inventory and set its lastChangeTime field.
     */
    public static class Handler implements IMessageHandler<PacketUpdatePedestal, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdatePedestal message, MessageContext ctx){
            Minecraft.getMinecraft().addScheduledTask(()-> {
               TileEntityPedestal te = (TileEntityPedestal)Minecraft.getMinecraft().world.getTileEntity(message.pos);
               te.inventory.setStackInSlot(0,message.stack);
               te.setLastChangeTime(message.lastChangeTime);
            });
            return null;
        }
    }

    private BlockPos pos;
    private ItemStack stack;
    private long lastChangeTime;

    public PacketUpdatePedestal(BlockPos pos, ItemStack stack, long lastChangeTime) {
        this.pos = pos;
        this.stack = stack;
        this.lastChangeTime = lastChangeTime;
    }

    /**
     * simply a convenience tool to make it easier to set this thing's values given a tile-entity
     * @param te
     */
    public PacketUpdatePedestal(TileEntityPedestal te) {
        this(te.getPos(), te.inventory.getStackInSlot(0), te.getLastChangeTime());
    }

    /**
     * This exists because of how Forge's SimpleImpl for networking creates the entity then populates its fields
     * via a call to fromBytes
     */
    public PacketUpdatePedestal() {
    }

    /**
     * These two functions read and write a ByteBuf obj which sends data over the network in minecraft
     * Because of how serialization works, they must do things in the exact same order on both sides (pack and unpack)
     * in a sort of FIFO model
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeLong(lastChangeTime);
    }


}
