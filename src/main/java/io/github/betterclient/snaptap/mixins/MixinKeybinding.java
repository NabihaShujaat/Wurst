package io.github.betterclient.snaptap.mixins;

import io.github.betterclient.snaptap.KeybindingAccess;
import io.github.betterclient.snaptap.SnapTap;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public class MixinKeybinding implements KeybindingAccess {
    @Shadow @Final private InputUtil.Key defaultKey;

    @Shadow private boolean pressed;

    @Inject(method = "isPressed", at = @At("HEAD"), cancellable = true)
    public void onGetPressed(CallbackInfoReturnable<Boolean> cir) {
        if (!SnapTap.TOGGLED) return;

        if (this.defaultKey.getCode() == 65) {
            //Left
            if (this.pressed) {
                if (SnapTap.RIGHT_STRAFE_LAST_PRESS_TIME == 0) {
                    cir.setReturnValue(true);
                    cir.cancel();
                    return;
                }

                cir.setReturnValue(SnapTap.RIGHT_STRAFE_LAST_PRESS_TIME <= SnapTap.LEFT_STRAFE_LAST_PRESS_TIME);
                cir.cancel();
            }
        } else if (this.defaultKey.getCode() == 68) {
            //Right
            if (this.pressed) {
                if (SnapTap.LEFT_STRAFE_LAST_PRESS_TIME == 0) {
                    cir.setReturnValue(true);
                    cir.cancel();
                    return;
                }

                cir.setReturnValue(SnapTap.LEFT_STRAFE_LAST_PRESS_TIME <= SnapTap.RIGHT_STRAFE_LAST_PRESS_TIME);
                cir.cancel();
            }
        }
    }

    @Inject(method = "setPressed", at = @At("HEAD"))
    public void setPressed(boolean pressed, CallbackInfo ci) {
        if (!SnapTap.TOGGLED) return;

        if (this.defaultKey.getCode() == 65) {
            //Left
            if (pressed) {
                SnapTap.LEFT_STRAFE_LAST_PRESS_TIME = System.currentTimeMillis();
            } else {
                SnapTap.LEFT_STRAFE_LAST_PRESS_TIME = 0;
            }
        } else if (this.defaultKey.getCode() == 68) {
            //Right
            if (pressed) {
                SnapTap.RIGHT_STRAFE_LAST_PRESS_TIME = System.currentTimeMillis();
            } else {
                SnapTap.RIGHT_STRAFE_LAST_PRESS_TIME = 0;
            }
        }
    }

    @Unique
    public boolean snapTap$isPressedReal() {
        return this.pressed;
    }
}
