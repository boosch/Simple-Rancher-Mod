package com.boosch.simplerancher.network;

import com.boosch.simplerancher.block.Pedestal.TileEntityPedestal;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * This is the sister to the PacketUpdatePedestal class. This one goes from client to server, requesting an update.
 * It has an additional parameter for dimension since the server has multiple worlds, and we need to know which world
 * to update.
 * We don't need to send the 'what' because the what lives on the server - all we need to send is the 'where' so the
 * server can send the client information about the specific thing at a specific BlockPos in a specific world
 */
public class PacketRequestUpdatePedestal implements IMessage {

    private BlockPos pos;
    private int dimension;
    private ItemStack stack;
    private long lastChangeTime;

    /**
     * Once again three constructors - one base, one convenience, and one becuase of how Forge's SimpleImpl works
     * @param pos
     * @param dimension
     */
    public PacketRequestUpdatePedestal(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdatePedestal(TileEntityPedestal te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdatePedestal() {
    }

    /**
     * This is the static handler class for packets of this type
     * It contains a response packet - we need to get the instance of the minecraftServer which contains all worlds,
     * then we get the dimension from the packet to pick the right world. Last, we get the tile entity, and return the
     * sister-class-packet to update the client from the server (think call [this] and response [PacketUpdatePedestal]
     */
    public static class Handler implements IMessageHandler<PacketRequestUpdatePedestal, PacketUpdatePedestal> {

        @Override
        public PacketUpdatePedestal onMessage(PacketRequestUpdatePedestal message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntityPedestal te = (TileEntityPedestal)world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdatePedestal(te);
            } else {
                return null;
            }
        }

    }


    @Override
    public void fromBytes(ByteBuf buf) {
        dimension = buf.readInt();
        pos = BlockPos.fromLong(buf.readLong());
        //stack = ByteBufUtils.readItemStack(buf);
        //lastChangeTime = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dimension);
        buf.writeLong(pos.toLong());
        //ByteBufUtils.writeItemStack(buf, stack);
        //buf.writeLong(lastChangeTime);
    }
}
