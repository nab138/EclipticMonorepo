package me.nabdev.ecliptic.core.volume;

import finalforeach.cosmicreach.blocks.BlockState;

public interface IBlockVolume {
    BlockState getBlock(int x, int y, int z);
}
