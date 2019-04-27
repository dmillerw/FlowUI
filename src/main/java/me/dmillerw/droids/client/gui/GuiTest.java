package me.dmillerw.droids.client.gui;

import me.dmillerw.droids.client.gui.widgets.Layout;
import me.dmillerw.droids.client.gui.widgets.impl.*;
import me.dmillerw.droids.client.gui.widgets.impl.layout.LinearLayout;
import me.dmillerw.droids.common.ModInfo;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiTest extends GuiBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.ID, "textures/gui/configurator_empty.png");

    private Layout layout;

    public GuiTest() {
        super(TEXTURE);

        this.xSize = 176;
        this.ySize = 238;
    }

    @Override
    protected void initialize() {
        LinearLayout.LinearLayoutBuilder builder = LinearLayout.buider()
                .orientation(LinearLayout.Orientation.HORIZONTAL)
                .child(TextField.builder().drawBackground().buildWidget())
                .child(TextField.builder().drawBackground().buildWidget());

        this.layout = Layout.fromGui(this, builder.buildWidget());
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);

        layout.draw(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        layout.onMouseClick(mouseX, mouseY, mouseButton);

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        layout.onKeyTyped(typedChar, keyCode);
    }
}
