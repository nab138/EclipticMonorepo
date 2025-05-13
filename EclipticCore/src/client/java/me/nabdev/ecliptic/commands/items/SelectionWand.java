package me.nabdev.ecliptic.commands.items;

import com.github.puzzle.game.util.BlockSelectionUtil;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.api.block.IBlockPosition;
import io.github.puzzle.cosmic.api.entity.player.IPlayer;
import io.github.puzzle.cosmic.api.item.IItemSlot;
import io.github.puzzle.cosmic.item.AbstractCosmicItem;
import io.github.puzzle.cosmic.util.APISide;
import me.nabdev.ecliptic.core.Constants;
import me.nabdev.ecliptic.core.utils.Selection;

import static me.nabdev.ecliptic.core.utils.ChatUtils.blockPosToString;
import static me.nabdev.ecliptic.core.utils.ChatUtils.sendMsg;

public class SelectionWand extends AbstractCosmicItem {
    public SelectionWand(){
        super(Identifier.of(Constants.MOD_ID, "selection_wand"));
        addTexture(ItemModelType.ITEM_MODEL_3D, Identifier.of(Constants.MOD_ID, "selection_wand.png"));
    }

    private static BlockPosition firstPos = null;
    private static BlockPosition secondPos = null;

    public static Selection getSelection(){
        if(firstPos == null || secondPos == null) return null;
        return Selection.of(firstPos, secondPos);
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
    public boolean canTargetBlockForBreaking(BlockState blockState) {
        return false;
    }

    @Override
    public boolean canMergeWith(Item item) {
        return false;
    }

    @Override
    public float getEffectiveBreakingSpeed(ItemStack stack) {
        return 0.0f;
    }

    @Override
    public boolean isEffectiveBreaking(ItemStack itemStack, BlockState blockState) {
        return true;
    }

    @Override
    public String getName() {
        return "Selection Wand";
    }

    @Override
    public boolean pUse(APISide side, IItemSlot itemSlot, IPlayer player, IBlockPosition _targetPlaceBlockPos, IBlockPosition _pos, boolean isLeftClick) {
        if(side == APISide.SERVER) return false;
        BlockPosition pos = BlockSelectionUtil.getBlockPositionLookingAt();
        if(pos == null) {
            return false;
        }
        sendMsg((isLeftClick ? "First" : "Second") + " position set to " + blockPosToString(pos));
        if(isLeftClick) {
            firstPos = pos.copy();
        } else {
            secondPos = pos.copy();
        }
        return true;
    }
}
