package me.nabdev.ecliptic.core.utils;

import finalforeach.cosmicreach.blocks.BlockPosition;
import me.nabdev.ecliptic.core.volume.RealVolume;

public class Selection {
    public int minX, minY, minZ;
    public int maxX, maxY, maxZ;

    public Selection(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public static Selection of(int x1, int y1, int z1, int x2, int y2, int z2) {
       int minX = Math.min(x1, x2);
       int minY = Math.min(y1, y2);
       int minZ = Math.min(z1, z2);
       int maxX = Math.max(x1, x2);
       int maxY = Math.max(y1, y2);
       int maxZ = Math.max(z1, z2);
       return new Selection(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static Selection of(BlockPosition pos1, BlockPosition pos2) {
        return Selection.of(pos1.getGlobalX(), pos1.getGlobalY(), pos1.getGlobalZ(),
                pos2.getGlobalX(), pos2.getGlobalY(), pos2.getGlobalZ());
    }

    public int sizeX() {
        return maxX - minX + 1;
    }

    public int sizeY() {
        return maxY - minY + 1;
    }

    public int sizeZ() {
        return maxZ - minZ + 1;
    }

    public RealVolume blankVolume() {
        return RealVolume.of(sizeX(), sizeY(), sizeZ());
    }
}
