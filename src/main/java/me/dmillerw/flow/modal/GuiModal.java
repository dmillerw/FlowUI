package me.dmillerw.flow.modal;

import me.dmillerw.flow.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public abstract class GuiModal extends GuiBase {

    public GuiModal(ResourceLocation texture) {
        super(texture);
    }

    @Override
    protected void drawBackground(int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);

        final int z = 0;
        RenderUtils.drawTexturedModalRect(guiLeft, guiTop, z, 0, 0, 2, 2, 2, 2);
        RenderUtils.drawTexturedModalRect(guiLeft + xSize - 2, guiTop, z, 4, 0, 6, 2, 2, 2);
        RenderUtils.drawTexturedModalRect(guiLeft + xSize - 2, guiTop + ySize - 2, z, 4, 4, 6, 6, 2, 2);
        RenderUtils.drawTexturedModalRect(guiLeft, guiTop + ySize - 2, z, 0, 4, 2, 6, 2, 2);

        RenderUtils.drawTexturedModalRect(guiLeft + 2, guiTop, z, 1, 0, 5, 2, xSize - 4, 2);
        RenderUtils.drawTexturedModalRect(guiLeft + 2, guiTop + ySize - 2, z, 1, 4, 5, 6, xSize - 4, 2);
        RenderUtils.drawTexturedModalRect(guiLeft + 2, guiTop + 2, z, 2, 2, 4, 4, xSize - 4, ySize - 4);
        RenderUtils.drawTexturedModalRect(guiLeft, guiTop + 2, z, 0, 1, 2, 5, 2, ySize - 4);
        RenderUtils.drawTexturedModalRect(guiLeft + xSize - 2, guiTop + 2, z, 4, 1, 6, 5, 2, ySize - 4);
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
    }
}
