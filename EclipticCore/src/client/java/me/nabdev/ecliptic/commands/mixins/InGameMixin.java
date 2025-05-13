package me.nabdev.ecliptic.commands.mixins;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.ui.UI;
import me.nabdev.ecliptic.commands.items.SelectionWand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame.class)
public class InGameMixin {
    @Unique
    private static ShapeRenderer ecliptic$sr2;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UI;render()V"))
    public void renderEclipticUI(CallbackInfo ci) {
        if (ecliptic$sr2 == null) {
            ecliptic$sr2 = new ShapeRenderer();
        }

        ItemStack selected = UI.hotbar.getSelectedItemStack();
        if(selected != null && selected.getItem() instanceof SelectionWand && SelectionWand.getSelection() != null) {
            SelectionWand.getSelection().draw(ecliptic$sr2, new Color(0.7882352941f, 0, 0, 0.25f), new Color(0.9098039216f, 0.2745098039f, 0.2745098039f, 1));
        }

    }
}
