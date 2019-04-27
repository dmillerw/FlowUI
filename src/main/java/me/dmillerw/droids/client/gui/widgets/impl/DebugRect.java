package me.dmillerw.droids.client.gui.widgets.impl;

import me.dmillerw.droids.client.gui.widgets.Dimensions;
import me.dmillerw.droids.client.gui.widgets.Drawable;
import me.dmillerw.droids.client.gui.widgets.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class DebugRect extends Widget implements Drawable {

    public static DebugRectBuilder builder() {
        return new DebugRectBuilder();
    }

    public static class DebugRectBuilder extends BaseBuilder<DebugRectBuilder, DebugRect> {

        private int color;

        public DebugRectBuilder color(int color) {
            this.color = color;
            return this;
        }

        public DebugRectBuilder randomColor() {
            this.color = new Random().nextInt(Integer.MAX_VALUE);
            return this;
        }

        @Override
        protected DebugRect construct() {
            return new DebugRect(color);
        }
    }

    private int color;
    
    public DebugRect(int color) {
        this.color = color;
    }
    
    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        return new Dimensions(1, 1);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        GlStateManager.pushAttrib();
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        int xCoord = getPositionWithOffsets().getX();
        int yCoord = getPositionWithOffsets().getY();
        int widthIn = getMinimumDimensions().getWidth();
        int heightIn = getMinimumDimensions().getHeight();

        int r = (color>>16)&0xFF;
        int g = (color>>8)&0xFF;
        int b = (color>>0)&0xFF;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)0).color(r, g, b, 255).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)0).color(r, g, b, 255).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)0).color(r, g, b, 255).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)0).color(r, g, b, 255).endVertex();
        tessellator.draw();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.popAttrib();
    }
}
