package me.dmillerw.flow.modal;

import net.minecraft.util.ResourceLocation;

import java.util.List;

public class GuiModalYesNo extends GuiModal {

    private final String title;
    private final String text;
    private final String negativeText;
    private final String positiveText;

    private List<String> contents;
//    private GuiWrappedButton buttonPositive;
//    private GuiWrappedButton buttonNegative;

    public GuiModalYesNo(ResourceLocation texture, String title, String text) {
        super(texture);

        this.title = title;
        this.text = text;
        this.negativeText = "No";
        this.positiveText = "Yes";
    }

    public GuiModalYesNo(ResourceLocation texture, String title, String text, String negativeText, String positiveText) {
        super(texture);

        this.title = title;
        this.text = text;
        this.negativeText = negativeText;
        this.positiveText = positiveText;
    }

    @Override
    protected void initialize() {
        int modalWidth = Math.min(252, Math.max(100, fontRenderer.getStringWidth(text)));
        contents = fontRenderer.listFormattedStringToWidth(text, modalWidth);
        int modalHeight = Math.min(256, 78 + 9 * contents.size());

        if (setGuiDimensions(modalWidth, modalHeight))
            return;

//        buttonNegative = new GuiWrappedButton(this, 10, ySize - 30, xSize / 2 - 20, 20, negativeText)
//                .onClick((button) -> Navigation.INSTANCE.pop(false));
//        buttonPositive = new GuiWrappedButton(this, xSize / 2 + 10, ySize - 30, xSize / 2 - 20, 20, positiveText)
//                .onClick((button) -> Navigation.INSTANCE.pop(true));
//
//        addElement("button_positive", buttonPositive);
//        addElement("button_negative", buttonNegative);
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        int center = guiLeft + xSize / 2;
        int titleX = center - fontRenderer.getStringWidth(title) / 2;

        mc.fontRenderer.drawString(title, titleX, guiTop + 10, 0xff544c3b);

        for (int i=0; i<contents.size(); i++) {
            String t = contents.get(i);
            int textX = center - fontRenderer.getStringWidth(t) / 2;
            int textY = guiTop + 29 + (fontRenderer.FONT_HEIGHT + 5) * i;

            mc.fontRenderer.drawString(t, textX, textY, 0xff544c3b);
        }
    }
}
