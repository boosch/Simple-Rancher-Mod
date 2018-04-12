package com.boosch.simplerancher.items;

import com.boosch.simplerancher.entity.golem.EntityHarvestGolem;
import com.boosch.simplerancher.entity.golem.EntitySimpleRancherGolem;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemGolemHarvest extends ItemGolemBase{

    public ItemGolemHarvest(String name, String type, String flavorText){

        super(name, type, flavorText);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.getBlockState(pos).getMaterial().equals(Material.AIR) ||
                worldIn.getBlockState(pos).getMaterial().equals(Material.WATER) ||
                worldIn.getBlockState(pos).getMaterial().equals(Material.LAVA) ||
                worldIn.getBlockState(pos).getMaterial().equals(Material.FIRE) ||
                worldIn.getBlockState(pos).getMaterial().equals(Material.CACTUS)) {
            player.sendMessage(new TextComponentString(TextFormatting.DARK_RED + "" + TextFormatting.ITALIC + "The golem refuses to let go of your hand. It's too dangerous!"));
            return EnumActionResult.FAIL;
        } else {
            if (!worldIn.isRemote) {
                EntityHarvestGolem newGolem = new EntityHarvestGolem(worldIn, pos);
                newGolem.setLocationAndAngles(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5, 0.0F, 0.0F);
                worldIn.spawnEntity(newGolem);
                player.getHeldItemMainhand().shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
    }
}
