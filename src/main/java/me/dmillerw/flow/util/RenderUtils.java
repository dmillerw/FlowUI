package me.dmillerw.flow.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderUtils {

    public static void drawTooltip(int x, int y, String tooltip) {
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        int i = fontRenderer.getStringWidth(tooltip);

        int l1 = x + 12;
        int i2 = y - 12;
        int k = 8;

        if (l1 + i > gui.width) {
            l1 -= 28 + i;
        }

        if (i2 + k + 6 > gui.height) {
            i2 = gui.height - k - 6;
        }

        int l = -267386864;
        drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864, 300.0F);
        drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864, 300.0F);
        drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864, 300.0F);
        drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864, 300.0F);
        drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864, 300.0F);
        int i1 = 1347420415;
        int j1 = 1344798847;
        drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847, 300.0F);
        drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847, 300.0F);
        drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415, 300.0F);
        drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847, 300.0F);

        fontRenderer.drawStringWithShadow(tooltip, (float) l1, (float) i2, -1);

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
    }

    public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor, float zLevel) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) right, (double) top, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) left, (double) top, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) left, (double) bottom, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double) right, (double) bottom, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
