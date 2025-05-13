package me.nabdev.ecliptic.core;

import com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls.ClientModInitializer;
import me.nabdev.ecliptic.commands.commands.Commands;
import me.nabdev.ecliptic.core.threading.FillingThread;

@SuppressWarnings("unused")
public class EclipticCore implements ClientModInitializer {

    @Override
    public void onInit() {
        Commands.register();
        FillingThread.init();
    }


}