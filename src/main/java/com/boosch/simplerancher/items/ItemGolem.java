package com.boosch.simplerancher.items;


import com.boosch.simplerancher.entity.EntitySimpleRancherGolem;
import com.boosch.simplerancher.entity.EntityStrawGolem;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@Mod.EventBusSubscriber
public class ItemGolem extends SimpleRancherItemBase implements FlavorText {


    protected String name;
    protected String type;
    protected String flavorText;

    public ItemGolem(String name, String type, String flavorText){

        super(name);
        this.name = name;
        this.type = type;
        this.flavorText=flavorText;
    }




    public String getFlavorText(){
        return flavorText;
    }
    public String getType(){
        return type;
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(worldIn.getBlockState(pos).getMaterial().equals(Material.AIR) ||
                worldIn.getBlockState(pos).getMaterial().equals(Material.WATER)||
                worldIn.getBlockState(pos).getMaterial().equals(Material.LAVA)||
                worldIn.getBlockState(pos).getMaterial().equals(Material.FIRE)||
                worldIn.getBlockState(pos).getMaterial().equals(Material.CACTUS)){
            player.sendMessage(new TextComponentString(TextFormatting.DARK_RED+""+TextFormatting.ITALIC+"The golem refuses to let go of your hand. It's too dangerous!"));
            return EnumActionResult.FAIL;
        }
        else{
            if(!worldIn.isRemote) {
                EntitySimpleRancherGolem newGolem = new EntitySimpleRancherGolem(worldIn, "straw", pos);
                newGolem.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), 0.0F, 0.0F);
                worldIn.spawnEntity(newGolem);
                player.getHeldItemMainhand().shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }


        //this is the default
        //return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
