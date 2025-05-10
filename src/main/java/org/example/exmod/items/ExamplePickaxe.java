package org.example.exmod.items;

import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.item.AbstractCosmicItem;
import org.example.exmod.Constants;

public class ExamplePickaxe extends AbstractCosmicItem {

    public ExamplePickaxe() {
        super(Identifier.of(Constants.MOD_ID, "example_pickaxe"));
        addTexture(ItemModelType.ITEM_MODEL_3D, Identifier.of(Constants.MOD_ID, "example_pickaxe.png"));
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean pIsTool() {
        return true;
    }

    @Override
    public float getEffectiveBreakingSpeed(ItemStack stack) {
        return stack.getItem().equals(this) ? 2.0f : 1.0f;
    }

    @Override
    public boolean isEffectiveBreaking(ItemStack itemStack, BlockState blockState) {
        return blockState.getBlockId().equals("base:aluminium_panel")
                || blockState.getBlockId().equals("base:asphalt")
                || blockState.getBlockId().equals("base:boombox")
                || blockState.getBlockId().equals("base:c4")
                || blockState.getBlockId().equals("base:hazard")
                || blockState.getBlockId().equals("base:light")
                || blockState.getBlockId().equals("base:magma")
                || blockState.getBlockId().equals("base:metal_panel")
                || blockState.getBlockId().equals("base:stone_basalt")
                || blockState.getBlockId().equals("base:stone_gabbro")
                || blockState.getBlockId().equals("base:stone_limestone")
                || blockState.getBlockId().equals("base:lunar_soil_packed");
    }

    @Override
    public String getName() {
        return "Example Pickaxe";
    }
}
