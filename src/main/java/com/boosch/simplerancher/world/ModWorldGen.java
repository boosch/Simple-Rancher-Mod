package com.boosch.simplerancher.world;

import com.boosch.simplerancher.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class ModWorldGen implements IWorldGenerator{

    /**
     * Hooks into minecrafts world generation flow - the generate method is called for every dimension.
     * Methods should be added for every world-specific generation task
     * @param random
     * @param chunkX
     * @param chunkZ
     * @param world
     * @param chunkGenerator
     * @param chunkProvider
     */
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){

        //only generate in the Overworld
        if(world.provider.getDimension() == 0){
            generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkprovider){

        generateOre(ModBlocks.QUARTZ_CRYSTAL_ORE.getDefaultState(), world, random, chunkX*16, chunkZ*16, 16, 48, 4+random.nextInt(4), 3);
    }

    /**
     * Helper method to generate ores regardless of the type of ore, with varying positions (Y=Height) in the world
     * accounts for chances and size of vein
     * @param ore - what we're making
     * @param world - where we're making it
     * @param random -
     * @param x -
     * @param z -
     * @param minY - the lowest elevation at which it can spawn
     * @param maxY - the highest elevation at which it can spawn
     * @param size - the block-count of the vein
     * @param chances - the number of tries per chunk to add this ore
     */
    private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances){

        int deltaY = maxY-minY;

        for( int i = 0; i< chances; i++){

            BlockPos pos = new BlockPos(x+random.nextInt(16), minY+random.nextInt(deltaY), z+random.nextInt(16));

            WorldGenMinable generator = new WorldGenMinable(ore, size);
            generator.generate(world, random, pos);
        }

    }

}
