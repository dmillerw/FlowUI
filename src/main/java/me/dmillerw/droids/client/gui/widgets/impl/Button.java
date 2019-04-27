package me.dmillerw.droids.client.gui.widgets.impl;

import me.dmillerw.droids.client.gui.widgets.*;
import me.dmillerw.droids.common.util.type.VoidFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class Button extends VanillaWidget<GuiButtonExt> implements Drawable, Clickable {

    public static ButtonBuilder builder() {
        return new ButtonBuilder();
    }

    public static class ButtonBuilder extends BaseBuilder<ButtonBuilder, Button> {

        private String buttonText;
        private VoidFunction callback;

        public ButtonBuilder text(String buttonText) {
            this.buttonText = buttonText;
            return this;
        }

        public ButtonBuilder callback(VoidFunction callback) {
            this.callback = callback;
            return this;
        }

        @Override
        protected Button construct() {
            return new Button(id, buttonText, callback);
        }

    }
    private String text;
    private VoidFunction callback;

    public Button(String id, String text, VoidFunction callback) {
        super(id);

        this.text = text;
        this.callback = callback;
    }

    @Override
    public GuiButtonExt buildElement() {
        Point pos = getPositionWithOffsets();
        Dimensions bounds = getMinimumDimensions().shrink(getMargin()).shrink(getPadding());
        return new GuiButtonExt(0, pos.getX(), pos.getY(), bounds.getWidth(), bounds.getHeight(), text);
    }

    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        Dimensions bounds = Dimensions.withWidgetInsets(this);

        bounds = bounds.add(fontRenderer.getStringWidth(text) + 10, 20);

        bounds = bounds.clamp(maxBounds);

        return bounds;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        backingElement.drawButton(Minecraft.getMinecraft(), mouseX, mouseY, 0);
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        Minecraft mc = Minecraft.getMinecraft();
        if (backingElement.mousePressed(mc, mouseX, mouseY)) {
            backingElement.playPressSound(mc.getSoundHandler());
            if (callback != null) callback.call();
            return true;
        }
        return false;
    }
}
