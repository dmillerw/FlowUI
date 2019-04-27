package me.dmillerw.droids.client.gui.widgets.impl;

import me.dmillerw.droids.client.gui.widgets.*;
import me.dmillerw.droids.client.util.FunctionalUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class TextField extends VanillaWidget<GuiTextField> implements Drawable, Clickable, Typable {

    public static TextFieldBuilder builder() {
        return new TextFieldBuilder();
    }

    public static class TextFieldBuilder extends BaseBuilder<TextFieldBuilder, TextField> {

        private Predicate<String> validator = s -> true;

        private boolean drawBackground = false;
        private Consumer<TextField> keyTypedCallback;

        public TextFieldBuilder validator(Predicate<String> validator) {
            this.validator = validator;
            return this;
        }

        public TextFieldBuilder drawBackground() {
            this.drawBackground = true;
            return this;
        }

        public TextFieldBuilder onKeyTyped(Consumer<TextField> keyTypedCallback) {
            this.keyTypedCallback = keyTypedCallback;
            return this;
        }

        @Override
        protected TextField construct() {
            return new TextField(id, validator, drawBackground, keyTypedCallback);
        }

    }
    private Predicate<String> validator;

    private boolean drawBackground;
    private Consumer<TextField> keyTypedCallback;
    private TextField(String id, Predicate<String> validator, boolean drawBackground, Consumer<TextField> keyTypedCallback) {
        super(id);

        this.validator = validator;
        this.drawBackground = drawBackground;
        this.keyTypedCallback = keyTypedCallback;
    }

    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        return Dimensions.withWidgetInsets(this).add(100, 20);
    }

    @Override
    public GuiTextField buildElement() {
        Minecraft mc = Minecraft.getMinecraft();
        Point pos = getPositionWithOffsets();
        Dimensions bounds = getMinimumDimensions().shrink(getMargin()).shrink(getPadding());
        GuiTextField textField = new GuiTextField(0, mc.fontRenderer, pos.getX(), pos.getY(), bounds.getWidth(), bounds.getHeight());
        textField.setEnableBackgroundDrawing(this.drawBackground);
        textField.setValidator(FunctionalUtils.javaToGoogle(validator));
        return textField;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        backingElement.drawTextBox();
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        return backingElement.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean onKeyTyped(char key, int keycode) {
        if (backingElement.isFocused()) {
            return backingElement.textboxKeyTyped(key, keycode);
        }
        return false;
    }
}
