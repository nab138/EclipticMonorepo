package me.nabdev.ecliptic.core;

import com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls.ClientPostModInitializer;
import finalforeach.cosmicreach.items.Item;
import io.github.puzzle.cosmic.item.AbstractCosmicItem;
import me.nabdev.ecliptic.commands.items.SelectionWand;

public class EclipticCorePostinit implements ClientPostModInitializer {
    public static Item selectionWand;
    @Override
    public void onPostInit() {
        AbstractCosmicItem wand = new SelectionWand();
        selectionWand = wand;
        AbstractCosmicItem.register(wand);
    }
}
