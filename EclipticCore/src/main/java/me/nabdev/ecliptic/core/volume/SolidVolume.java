package me.nabdev.ecliptic.core.volume;

import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;

public class SolidVolume implements IBlockVolume {
    private final BlockState blockState;

    public SolidVolume(BlockState state) {
        this.blockState = state;
    }

    public static SolidVolume of(BlockState state) {
        return new SolidVolume(state);
    }

    public static SolidVolume air() {
        return new SolidVolume(Block.AIR.getDefaultBlockState());
    }

    public static SolidVolume empty() {
        return new SolidVolume(null);
    }

    @Override
    public BlockState getBlock(int x, int y, int z) {
        return blockState;
    }

    @Override
    public String toString() {
        return "SolidVolume{" +
                "blockState=" + blockState +
                '}';
    }
}
