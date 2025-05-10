package me.nabdev.ecliptic.core;

import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.ModInitializer;
import me.nabdev.ecliptic.core.threading.FillingThread;

@SuppressWarnings("unused")
public class EclipticCore implements ModInitializer {
    @Override
    public void onInit(){
        FillingThread.init();
    }
}
