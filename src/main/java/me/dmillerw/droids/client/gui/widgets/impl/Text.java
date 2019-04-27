package me.dmillerw.droids.client.gui.widgets.impl;

import com.google.common.collect.Lists;
import me.dmillerw.droids.client.gui.widgets.Dimensions;
import me.dmillerw.droids.client.gui.widgets.Drawable;
import me.dmillerw.droids.client.gui.widgets.Point;
import me.dmillerw.droids.client.gui.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.List;

public class Text extends Widget implements Drawable {

    public static TextBuilder buider() {
        return new TextBuilder();
    }

    public static class TextBuilder extends BaseBuilder<TextBuilder, Text> {

        public String text;
        public boolean wrapLines;
        public int textColor;

        public TextBuilder text(String text) {
            this.text = text;
            return this;
        }

        public TextBuilder wrapLines() {
            this.wrapLines = true;
            return this;
        }

        public TextBuilder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        @Override
        protected Text construct() {
            return new Text(id, this.text, wrapLines);
        }
    }

    private String text;
    private boolean wrapLines;

    public Text(String id, String text, boolean wrapLines) {
        super(id);

        this.text = text;
        this.wrapLines = wrapLines;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        Dimensions bounds = Dimensions.withWidgetInsets(this);

        int maxWidth = maxBounds.getWidth() - bounds.getWidth();

        if (wrapLines) {
            int width = fontRenderer.getStringWidth(text);
            if (width > maxWidth) {
                List<String> lines = fontRenderer.listFormattedStringToWidth(text, maxWidth);

                bounds = bounds.add(maxWidth,(fontRenderer.FONT_HEIGHT + 2) * lines.size());
            } else {
                bounds = bounds.add(width, fontRenderer.FONT_HEIGHT);
            }
        } else {
            bounds = bounds.add(fontRenderer.getStringWidth(text), fontRenderer.FONT_HEIGHT);
        }

        bounds = bounds.clamp(maxBounds);

        return bounds;
    }

    @Override
    public void draw(int x, int y) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int width = getMinimumDimensions().getWidth();
        Point pos = getPositionWithOffsets();
        List<String> split;
        if (wrapLines) {
            split = fontRenderer.listFormattedStringToWidth(text, width);
        } else {
            split = Lists.newArrayList(text);
        }
        for (int i=0; i<split.size(); i++) {
            fontRenderer.drawString(split.get(i), pos.getX(), pos.getY() + (fontRenderer.FONT_HEIGHT + 2) * i, 0x00000000);
        }
    }
}
