package me.nabdev.ecliptic.core.commands;

import com.github.puzzle.game.commands.CommandManager;
import com.github.puzzle.game.commands.ServerCommandSource;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.blocks.MissingBlockStateResult;
import finalforeach.cosmicreach.gamestates.InGame;
import me.nabdev.ecliptic.core.actions.FillAction;
import me.nabdev.ecliptic.core.utils.Selection;
import me.nabdev.ecliptic.core.utils.UndoUtils;

public class Commands {

    public static void register() {
        LiteralArgumentBuilder<ServerCommandSource> cmd = CommandManager.literal("/fill");
        cmd.then(CommandManager.argument(ServerCommandSource.class, "x1", IntegerArgumentType.integer())
                .then(CommandManager.argument(ServerCommandSource.class, "y1", IntegerArgumentType.integer())
                        .then(CommandManager.argument(ServerCommandSource.class, "z1", IntegerArgumentType.integer())
                                .then(CommandManager.argument(ServerCommandSource.class, "x2", IntegerArgumentType.integer())
                                        .then(CommandManager.argument(ServerCommandSource.class, "y2", IntegerArgumentType.integer())
                                                .then(CommandManager.argument(ServerCommandSource.class, "z2", IntegerArgumentType.integer())
                                                        .then(CommandManager.argument(ServerCommandSource.class, "blockstate", StringArgumentType.greedyString())
                                                                .executes(context -> {
                                                                    int x1 = IntegerArgumentType.getInteger(context, "x1");
                                                                    int y1 = IntegerArgumentType.getInteger(context, "y1");
                                                                    int z1 = IntegerArgumentType.getInteger(context, "z1");
                                                                    int x2 = IntegerArgumentType.getInteger(context, "x2");
                                                                    int y2 = IntegerArgumentType.getInteger(context, "y2");
                                                                    int z2 = IntegerArgumentType.getInteger(context, "z2");
                                                                    String blockState = StringArgumentType.getString(context, "blockstate");
                                                                    FillAction.of(Selection.of(x1, y1, z1, x2, y2, z2), BlockState.getInstance(blockState, MissingBlockStateResult.MISSING_OBJECT)).apply(InGame.getLocalPlayer().getZone());
                                                                    return 0;
                                                                })
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        LiteralArgumentBuilder<ServerCommandSource> undoCmd = CommandManager.literal("/undo");
        undoCmd.executes(context -> {
            UndoUtils.undo(InGame.getLocalPlayer().getZone());
            return 0;
        });
        LiteralArgumentBuilder<ServerCommandSource> redoCmd = CommandManager.literal("/redo");
        redoCmd.executes(context -> {
            UndoUtils.redo(InGame.getLocalPlayer().getZone());
            return 0;
        });
        CommandManager.DISPATCHER.register(cmd);
        CommandManager.DISPATCHER.register(undoCmd);
        CommandManager.DISPATCHER.register(redoCmd);
    }

}
