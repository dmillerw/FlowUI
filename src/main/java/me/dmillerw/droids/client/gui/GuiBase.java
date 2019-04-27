package me.dmillerw.droids.client.gui;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public abstract class GuiBase extends GuiScreen {

    private ResourceLocation texture;

    protected int xSize;
    protected int ySize;

    protected int guiLeft;
    protected int guiTop;

    protected boolean doesGuiPauseGame = false;

    public GuiBase(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public final void initGui() {
        super.initGui();

        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;

        initialize();
    }

    protected abstract void initialize();

    @Override
    public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        final float delta = Mouse.getDWheel();
        if (delta != 0) {
            onMouseScroll(mouseX, mouseY, delta);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);

        drawBackground(mouseX, mouseY);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);

        drawForeground(mouseX, mouseY);

        this.mc.getTextureManager().bindTexture(texture);
}

    protected void drawBackground(int mouseX, int mouseY) {
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    protected void drawForeground(int mouseX, int mouseY) {}

    protected void onMouseScroll(int mouseX, int mouseY, float delta) {}

    /* CALLBACKS */

    public void onGuiPaused() {}

    /* UTIL */

    protected boolean setGuiDimensions(int xSize, int ySize) {
        if (xSize != this.xSize || ySize != this.ySize) {
            this.xSize = xSize;
            this.ySize = ySize;
            this.initGui();

            return true;
        }

        return false;
    }

    protected void playClickSound() {
        mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    public final void bindTexture() {
        mc.getTextureManager().bindTexture(texture);
    }

    public float getZLevel() {
        return zLevel;
    }

    public final int getGuiLeft() {
        return guiLeft;
    }

    public final int getGuiTop() {
        return guiTop;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }
}
