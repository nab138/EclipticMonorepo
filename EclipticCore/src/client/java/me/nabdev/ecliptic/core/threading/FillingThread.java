package me.nabdev.ecliptic.core.threading;

import com.badlogic.gdx.utils.PauseableThread;
import com.badlogic.gdx.utils.Queue;
import com.github.puzzle.game.util.BlockUtil;
import com.github.puzzle.game.util.IClientNetworkManager;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.networking.packets.blocks.PlaceBlockPacket;
import finalforeach.cosmicreach.world.Chunk;
import finalforeach.cosmicreach.world.Zone;
import me.nabdev.ecliptic.core.utils.ChunkUtils;
import me.nabdev.ecliptic.core.utils.Selection;
import me.nabdev.ecliptic.core.volume.IBlockVolume;
import me.nabdev.ecliptic.core.volume.RealVolume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class FillingThread implements Runnable {
    public static Logger LOGGER = LoggerFactory.getLogger("Ecliptic Filling Thread");
    public static FillingThread INSTANCE;
    public static PauseableThread parent;

    private static final Queue<Runnable> queuedRunnables = new Queue<>();
    private static final AtomicBoolean isRunning = new AtomicBoolean(false);

    public static boolean isRunning() {
        return isRunning.get();
    }

    /**
     * Fills a volume with blocks, and updates the chunks in the zone.
     * @param zone The zone to modify
     * @param oldBlocks A reference that will be filled with the existing blocks in the volume
     * @param newBlocks The new blocks to fill the volume with
     * @param selection The bounding box to fill
     * @param filter A filter to apply to the blocks in the volume. If null, all blocks will be filled.
     * @param onDone A runnable to run when the filling is done
     * @param nullIsAir If true, null blocks will be replaced with air. If false, null blocks will be skipped.
     */
    public static void post(Zone zone, RealVolume oldBlocks, IBlockVolume newBlocks, Selection selection, Function<BlockState, Boolean> filter, BiConsumer<Long, Integer> onDone, boolean nullIsAir) {
        queuedRunnables.addLast(() -> {
            synchronized (isRunning) {
                isRunning.set(true);
                int numBlocks = 0;
                long startTime = System.nanoTime();
                List<Chunk> chunksToUpdate = new ArrayList<>();

                for (int x = selection.minX; x <= selection.maxX; x++) {
                    for (int z = selection.minZ; z <= selection.maxZ; z++) {
                        for (int y = selection.minY; y <= selection.maxY; y++) {
                            if(oldBlocks != null) oldBlocks.setBlock(x - selection.minX, y - selection.minY, z - selection.minZ, BlockUtil.getBlockPosAtVec(zone, x, y, z).getBlockState());
                            if(filter != null && !filter.apply(BlockUtil.getBlockPosAtVec(zone, x, y, z).getBlockState())) continue;
                            BlockState block = newBlocks.getBlock(x - selection.minX, y - selection.minY, z - selection.minZ);
                            if (block == null) {
                                if (nullIsAir) block = Block.AIR.getDefaultBlockState();
                                else continue;
                            }
                            zone.setBlockState(block, x, y, z);
                            Chunk c = zone.getChunkAtBlock(x, y, z);
                            if (!chunksToUpdate.contains(c)) chunksToUpdate.add(c);
                            if (IClientNetworkManager.isConnected()) {
                                BlockPosition pos = BlockPosition.ofGlobal(zone, x, y, z);
                                IClientNetworkManager.sendAsClient(new PlaceBlockPacket(pos, block, -1));
                            }
                            numBlocks++;
                        }
                    }
                }

                ChunkUtils.remesh(chunksToUpdate, zone);
                if (onDone != null) onDone.accept(System.nanoTime() - startTime, numBlocks);
                isRunning.set(false);
            }
        });

        if (!started) start();
        else parent.onResume();
    }

    public FillingThread() {
        INSTANCE = this;
        parent = (PauseableThread) ThreadHelper.getThread("filling");
    }

    @Override
    public void run() {
        synchronized (queuedRunnables) {
            while (!queuedRunnables.isEmpty()) {
                Runnable runnable = queuedRunnables.removeFirst();
                if (runnable != null)
                    runnable.run();
                else
                    LOGGER.warn("Uh oh, A null runnable was found on the `FillingThread`");
            }
        }
        parent.onPause();
    }

    public static boolean started = false;

    public static void init() {
        parent = ThreadHelper.createPauseable("filling", new FillingThread());
    }

    public static void start() {
        started = true;
        if (parent == null) {
            throw new RuntimeException("Call `init()` on the `FillingThread` first.");
        }
        parent.start();
    }
}
