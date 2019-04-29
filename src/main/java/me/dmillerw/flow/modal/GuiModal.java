package me.dmillerw.flow.modal;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public abstract class GuiModal extends GuiBase {

    public GuiModal(ResourceLocation texture) {
        super(texture);
    }

    @Override
    protected void drawBackground(int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);

        final int z = 0;
        drawTexturedModalRect(guiLeft, guiTop, z, 0, 0, 2, 2, 2, 2);
        drawTexturedModalRect(guiLeft + xSize - 2, guiTop, z, 4, 0, 6, 2, 2, 2);
        drawTexturedModalRect(guiLeft + xSize - 2, guiTop + ySize - 2, z, 4, 4, 6, 6, 2, 2);
        drawTexturedModalRect(guiLeft, guiTop + ySize - 2, z, 0, 4, 2, 6, 2, 2);

        drawTexturedModalRect(guiLeft + 2, guiTop, z, 1, 0, 5, 2, xSize - 4, 2);
        drawTexturedModalRect(guiLeft + 2, guiTop + ySize - 2, z, 1, 4, 5, 6, xSize - 4, 2);
        drawTexturedModalRect(guiLeft + 2, guiTop + 2, z, 2, 2, 4, 4, xSize - 4, ySize - 4);
        drawTexturedModalRect(guiLeft, guiTop + 2, z, 0, 1, 2, 5, 2, ySize - 4);
        drawTexturedModalRect(guiLeft + xSize - 2, guiTop + 2, z, 4, 1, 6, 5, 2, ySize - 4);
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
    }

    public void drawTexturedModalRect(int x, int y, int z, int startU, int startV, int endU, int endV, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) (x + 0), (double) (y + height), (double) z).tex((double) ((float) (startU) * 0.00390625F), (double) ((float) (endV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), (double) z).tex((double) ((float) (endU) * 0.00390625F), (double) ((float) (endV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + 0), (double) z).tex((double) ((float) (endU) * 0.00390625F), (double) ((float) (startV + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + 0), (double) (y + 0), (double) z).tex((double) ((float) (startU) * 0.00390625F), (double) ((float) (startV + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }
}
