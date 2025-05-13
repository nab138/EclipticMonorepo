package me.nabdev.ecliptic.commands.commands;

import com.github.puzzle.game.commands.ClientCommandManager;
import com.github.puzzle.game.commands.ClientCommandSource;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.blocks.MissingBlockStateResult;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.items.ItemStack;
import me.nabdev.ecliptic.commands.items.SelectionWand;
import me.nabdev.ecliptic.core.EclipticCorePostinit;
import me.nabdev.ecliptic.core.actions.FillAction;
import me.nabdev.ecliptic.core.utils.UndoUtils;

public class Commands {

    public static void register() {
        LiteralArgumentBuilder<ClientCommandSource> fillCmd = ClientCommandManager.literal("?fill");
        fillCmd.then(ClientCommandManager.argument("blockstate", StringArgumentType.greedyString())
                .executes(context -> {
                    String blockState = StringArgumentType.getString(context, "blockstate");
                    FillAction.of(SelectionWand.getSelection(), BlockState.getInstance(blockState, MissingBlockStateResult.MISSING_OBJECT)).apply(InGame.getLocalPlayer().getZone());
                    return 0;
                })
        );

        LiteralArgumentBuilder<ClientCommandSource> undoCmd = ClientCommandManager.literal("?undo");
        undoCmd.executes(context -> {
            UndoUtils.undo(InGame.getLocalPlayer().getZone());
            return 0;
        });
        LiteralArgumentBuilder<ClientCommandSource> redoCmd = ClientCommandManager.literal("?redo");
        redoCmd.executes(context -> {
            UndoUtils.redo(InGame.getLocalPlayer().getZone());
            return 0;
        });
        LiteralArgumentBuilder<ClientCommandSource> wandCmd = ClientCommandManager.literal("?wand");
        wandCmd.executes(context -> {
            InGame.getLocalPlayer().inventory.addItemStack(new ItemStack(EclipticCorePostinit.selectionWand));
            return 0;
        });

        ClientCommandManager.DISPATCHER.register(fillCmd);
        ClientCommandManager.DISPATCHER.register(undoCmd);
        ClientCommandManager.DISPATCHER.register(wandCmd);
        ClientCommandManager.DISPATCHER.register(redoCmd);
    }

}
