package com.example.infinitelava;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 让岩浆像水一样无限生成：
 * 当某个"流动中的岩浆"周围（水平方向）有 2 个及以上的岩浆源时，
 * 把它也变成岩浆源。这样挖 2x2 的坑、对角放两桶岩浆，就能无限取岩浆。
 */
public class InfiniteLavaHandler {

    @SubscribeEvent
    public void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        World world = event.getWorld();
        if (world.isRemote) {
            return;
        }

        tryConvert(world, event.getPos());

        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
            tryConvert(world, event.getPos().offset(facing));
        }
    }

    private void tryConvert(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);

        if (state.getBlock() != Blocks.FLOWING_LAVA) {
            return;
        }
        if (state.getValue(BlockLiquid.LEVEL) == 0) {
            return;
        }

        int sources = 0;
        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
            IBlockState neighbor = world.getBlockState(pos.offset(facing));
            if (neighbor.getMaterial() == Material.LAVA
                    && neighbor.getValue(BlockLiquid.LEVEL) == 0) {
                sources++;
            }
        }

        if (sources >= 2) {
            world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 2);
        }
    }
}
