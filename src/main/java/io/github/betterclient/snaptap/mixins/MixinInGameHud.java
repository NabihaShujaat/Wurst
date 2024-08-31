package io.github.betterclient.snaptap.mixins;

import io.github.betterclient.snaptap.SnapTap;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Inject(method = "render", at = @At("RETURN"))
    public void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if(SnapTap.KEYSTROKES_TOGGLED)
            SnapTap.render(context);
    }
}
