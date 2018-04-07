package com.boosch.simplerancher.block.Pedestal;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.block.SimpleRancherBlockTileEntity;
import com.boosch.simplerancher.network.PacketRequestUpdatePedestal;
import com.boosch.simplerancher.network.PacketUpdatePedestal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityPedestal extends TileEntity {

    //holds the item being displayed
    public ItemStackHandler inventory = new ItemStackHandler(1){
        /**
         * This identifies when the tile entity has been updated that we need to update the CLIENT with a packet
         * The SendToAllAround ensures that every client player within 64 blocks of the entity is ALSO updated
         * @param slot
         */
        @Override
        public void onContentsChanged(int slot){
            if(!world.isRemote){
                lastChangeTime = world.getTotalWorldTime();
                SimpleRancher.network.sendToAllAround(new PacketUpdatePedestal(TileEntityPedestal.this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
            }
        }
    };

    //used for rendering the floaty item above it
    private long lastChangeTime;

    /**
     * This ensures that when the tile entity is loaded into the world for a player that the server get's updated
     * and in turn updates the client who triggered the load
     */
    @Override
    public void onLoad() {
        if (world.isRemote) {
            SimpleRancher.network.sendToServer(new PacketRequestUpdatePedestal(this));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setLong("lastChangeTime", lastChangeTime);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        this.lastChangeTime=compound.getLong("lastChangeTime");
        super.readFromNBT(compound);
    }

    public void setLastChangeTime(long l){
        this.lastChangeTime=l;
    }

    public long getLastChangeTime(){
        return this.lastChangeTime;
    }




    /**
     * Since our pedestal can now render an item, we need to let the engine know how much taller it is than the block definition
     * Otherwise, if the block was obscured, then the floaty item would also be obscured
     * @return
     */
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos(), getPos().add(1,2,1));
        //return super.getRenderBoundingBox();
    }

    /**
     * We’ll also override the hasCapability and getCapability methods.
     * In hasCapability we’ll return if the capability being tested is the IItemHandler capability instance,
     * or if it’s provided by the super method*.
     * @param capability
     * @param facing
     * @return
     */
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }


    /**
     * Likewise, in the getCapability method,
     * we’ll check if the capability being requested is the IItemHandler capability and if so,
     * return our inventory, and otherwise, delegate to the super method.
     */
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
    }

}
