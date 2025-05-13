package me.nabdev.ecliptic.core.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.gamestates.GameState;
import me.nabdev.ecliptic.core.volume.RealVolume;

public class Selection {
    public int minX, minY, minZ;
    public int maxX, maxY, maxZ;

    public static Camera rawWorldCamera = GameState.IN_GAME.getWorldCamera();
    public static final float inflate = 0.01f;
    //public static final float borderInflate = 0.001f;

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
        int minX = Math.min(pos1.getGlobalX(), pos2.getGlobalX());
        int minY = Math.min(pos1.getGlobalY(), pos2.getGlobalY());
        int minZ = Math.min(pos1.getGlobalZ(), pos2.getGlobalZ());
        int maxX = Math.max(pos1.getGlobalX(), pos2.getGlobalX());
        int maxY = Math.max(pos1.getGlobalY(), pos2.getGlobalY());
        int maxZ = Math.max(pos1.getGlobalZ(), pos2.getGlobalZ());
        return new Selection(minX, minY, minZ, maxX, maxY, maxZ);
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


    public void draw(ShapeRenderer sr, Color fill, Color border){
        sr.setProjectionMatrix(rawWorldCamera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(fill);
        float width = maxX + inflate - (minX - inflate) + 1;
        float height = maxY + inflate - (minY - inflate) + 1;
        float depth = maxZ + inflate - (minZ - inflate) + 1;
        sr.box(minX - inflate, minY - inflate, maxZ + inflate + 1, width, height, depth);
        sr.box(maxX + inflate + 1, maxY + inflate + 1, minZ - inflate, -width, -height, -depth);
        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(border);
        sr.box(minX - inflate, minY - inflate, maxZ + inflate + 1, width, height, depth);
        //sr.box(bb.min.x + eps * 0.5f, bb.min.y + eps * 0.5f, bb.min.z + bb.getDepth() - eps * 0.5f, bb.getWidth() - eps, bb.getHeight() - eps, bb.getDepth() - eps);
        sr.end();
    }
}
