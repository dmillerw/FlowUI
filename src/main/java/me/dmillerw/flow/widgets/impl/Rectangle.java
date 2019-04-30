package me.dmillerw.flow.widgets.impl;

import me.dmillerw.flow.Dimensions;
import me.dmillerw.flow.Drawable;
import me.dmillerw.flow.util.RenderUtils;
import me.dmillerw.flow.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.util.Random;

public class Rectangle extends Widget implements Drawable {

    public static RectangleBuilder builder() {
        return new RectangleBuilder();
    }

    public static class RectangleBuilder extends BaseBuilder<RectangleBuilder, Rectangle> {

        private int color;
        private BorderStyle borderStyle;

        public RectangleBuilder color(int color) {
            this.color = color;
            return this;
        }

        public RectangleBuilder borderStyle(BorderStyle borderStyle) {
            this.borderStyle = borderStyle;
            return this;
        }

        public RectangleBuilder randomColor() {
            this.color = new Random().nextInt(Integer.MAX_VALUE);
            return this;
        }

        @Override
        protected Rectangle construct() {
            return new Rectangle(color, borderStyle);
        }
    }

    public static enum BorderStyle {
        NONE, INSET
    }

    private BorderStyle borderStyle;
    private int color;
    
    public Rectangle(int color, BorderStyle borderStyle) {
        this.color = color;
        this.borderStyle = borderStyle;
    }
    
    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        return new Dimensions(1, 1);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getMinecraft();

        int xCoord = getPositionWithOffsets().getX();
        int yCoord = getPositionWithOffsets().getY();
        int widthIn = getMinimumDimensions().getWidth();
        int heightIn = getMinimumDimensions().getHeight();

        int r = (color >> 16) & 0xFF;
        int g = (color >>  8) & 0xFF;
        int b = (color      ) & 0xFF;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.pushAttrib();
        GlStateManager.disableTexture2D();

        // Colored portion
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)0).color(r, g, b, 255).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)0).color(r, g, b, 255).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)0).color(r, g, b, 255).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)0).color(r, g, b, 255).endVertex();

        tessellator.draw();

        GlStateManager.enableTexture2D();

        if (borderStyle == BorderStyle.INSET) {
            mc.getTextureManager().bindTexture(Widget.WIDGET_TEXTURE);

            RenderUtils.drawTexturedModalRect(xCoord, yCoord, 0, 0, 7, 1, 8, 1, heightIn - 1);
            RenderUtils.drawTexturedModalRect(xCoord, yCoord, 0, 0, 7, 1, 8, widthIn - 1, 1);
            RenderUtils.drawTexturedModalRect(xCoord, yCoord + heightIn - 1, 0, 0, 8, 1, 9, 1, 1);
            RenderUtils.drawTexturedModalRect(xCoord + widthIn - 1, yCoord, 0, 0, 8, 1, 9, 1, 1);
            RenderUtils.drawTexturedModalRect(xCoord + 1, yCoord + heightIn - 1, 0, 0, 9, 1, 10, widthIn - 1, 1);
            RenderUtils.drawTexturedModalRect(xCoord + widthIn - 1, yCoord + 1, 0, 0, 9, 1, 10, 1, heightIn - 1);
        }

        GlStateManager.popAttrib();
    }
}
