package io.github.betterclient.snaptap;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SnapTap implements ModInitializer {
    public static long LEFT_STRAFE_LAST_PRESS_TIME = 0;
    public static long RIGHT_STRAFE_LAST_PRESS_TIME = 0;
    public static KeyBinding TOGGLE_BIND;
    public static boolean TOGGLED = true;

    public static KeyBinding KEYSTROKES_TOGGLE_BIND;
    public static boolean KEYSTROKES_TOGGLED = true;

    @Override
    public void onInitialize() {
        LEFT_STRAFE_LAST_PRESS_TIME = 0;
        RIGHT_STRAFE_LAST_PRESS_TIME = 0;
        TOGGLE_BIND = new KeyBinding("text.snaptap.toggle", InputUtil.GLFW_KEY_F8, "key.categories.misc") {
            @Override
            public void setPressed(boolean pressed) {
                if(pressed) {
                    TOGGLED = !TOGGLED;
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(
                            Text.translatable("text.snaptap.toggled",
                                    Text.translatable(TOGGLED ? "text.snaptap.enabled" : "options.ao.off")
                                            .fillStyle(Style.EMPTY
                                                    .withColor(TOGGLED ? Formatting.GREEN : Formatting.RED))));
                }

                super.setPressed(pressed);
            }
        };

        KEYSTROKES_TOGGLE_BIND = new KeyBinding("text.snaptap.keystrokestoggle", InputUtil.GLFW_KEY_F7, "key.categories.misc") {
            @Override
            public void setPressed(boolean pressed) {
                if(pressed) {
                    KEYSTROKES_TOGGLED = !KEYSTROKES_TOGGLED;
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(
                            Text.translatable("text.snaptap.toggledkeystokes",
                                    Text.translatable(KEYSTROKES_TOGGLED ? "text.snaptap.enabled" : "options.ao.off")
                                            .fillStyle(Style.EMPTY
                                                    .withColor(KEYSTROKES_TOGGLED ? Formatting.GREEN : Formatting.RED))));
                }

                super.setPressed(pressed);
            }
        };
    }

    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();

        KeyBinding leftKey = client.options.leftKey;
        KeyBinding rightKey = client.options.rightKey;

        KeybindingAccess left = (KeybindingAccess) leftKey;
        KeybindingAccess right = (KeybindingAccess) rightKey;

        if (left.snapTap$isPressedReal()) {
            context.fill(5, 5, 25, 25, 0xFF444444);
        } else {
            context.fill(5, 5, 25, 25, 0xFF000000);
        }

        if (right.snapTap$isPressedReal()) {
            context.fill(30, 5, 50, 25, 0xFF444444);
        } else {
            context.fill(30, 5, 50, 25, 0xFF000000);
        }

        context.drawCenteredTextWithShadow(client.textRenderer, leftKey.getBoundKeyLocalizedText(), 15, 11, -1);
        context.drawCenteredTextWithShadow(client.textRenderer, rightKey.getBoundKeyLocalizedText(), 40, 11, -1);
    }
}
