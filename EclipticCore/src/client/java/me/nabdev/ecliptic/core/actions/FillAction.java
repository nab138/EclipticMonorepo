package me.nabdev.ecliptic.core.actions;

import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.world.Zone;
import me.nabdev.ecliptic.core.threading.FillingThread;
import me.nabdev.ecliptic.core.utils.Selection;
import me.nabdev.ecliptic.core.volume.IBlockVolume;
import me.nabdev.ecliptic.core.volume.RealVolume;
import me.nabdev.ecliptic.core.volume.SolidVolume;

import static me.nabdev.ecliptic.core.utils.ChatUtils.nanoToSec;
import static me.nabdev.ecliptic.core.utils.ChatUtils.sendMsg;

public class FillAction implements IAction {
    private final IBlockVolume volume;
    private final RealVolume oldBlocks;
    private final Selection selection;

    public FillAction(Selection selection, IBlockVolume volume) {
        this.volume = volume;
        this.selection = selection;
        this.oldBlocks = selection.blankVolume();
    }

    public static FillAction of(Selection selection, IBlockVolume volume) {
        return new FillAction(selection, volume);
    }

    public static FillAction of(Selection selection, BlockState state) {
        return new FillAction(selection, SolidVolume.of(state));
    }

    @Override
    public void applyInternal(Zone zone, boolean verbose) {
        FillingThread.post(zone, oldBlocks, volume, selection, (b) -> true, (t, n) -> {
            if(verbose) sendMsg("Filled " + n + " block(s) in " + nanoToSec(t) + "s");
        },true);
    }

    @Override
    public void undoInternal(Zone zone, boolean verbose, Runnable onSuccess) {
        FillingThread.post(zone, null, oldBlocks, selection, (b) -> true, (t, n) -> {
            if(verbose) sendMsg("Undid filling " + n + " block(s) in " + nanoToSec(t) + "s");
            if(onSuccess != null) onSuccess.run();
        },true);
    }
}
