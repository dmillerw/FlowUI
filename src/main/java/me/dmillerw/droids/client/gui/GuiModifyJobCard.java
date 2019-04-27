package me.dmillerw.droids.client.gui;

//import me.dmillerw.droids.client.gui.element.GuiWrappedButton;
import me.dmillerw.droids.common.ModInfo;
import me.dmillerw.droids.common.item.ItemJobCard;
import me.dmillerw.droids.common.job.JobDefinition;
import me.dmillerw.droids.common.job.parameter.Parameter;
import me.dmillerw.droids.common.job.parameter.ParameterMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class GuiModifyJobCard extends GuiBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.ID, "textures/gui/configurator_empty.png");

    private static final int LINES_PER_PAGE = 8;

    private EntityPlayer player;
    private ItemStack itemStack;

    private JobDefinition job;
    private ParameterMap parameterMap;
    private List<Parameter> parameters;

//    private GuiWrappedButton buttonScrollBack;
//    private GuiWrappedButton buttonScrollForward;

//    private GuiWrappedButton[] preferenceButtons = new GuiWrappedButton[LINES_PER_PAGE];

    private int currentPageIndex = 0;

    public GuiModifyJobCard(EntityPlayer player, ItemStack itemStack) {
        super(TEXTURE);

        this.player = player;
        this.itemStack = itemStack;

        this.job = ItemJobCard.getJobFromCard(itemStack);
        this.parameterMap = ItemJobCard.getParametersFromCard(job, itemStack);
        this.parameters = job.getParameters();

        this.xSize = 176;
        this.ySize = 238;
    }

    @Override
    protected void initialize() {
//        buttonScrollBack = new GuiWrappedButton(this, 4, 4, 18, 18, "<");
//        buttonScrollBack.onClick((button) -> scroll(-1));
//        buttonScrollForward = new GuiWrappedButton(this, xSize - 4, 4, 18, 18, ">");
//        buttonScrollForward.onClick((button) -> scroll(1));

        rebuildButtons();
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);

        String string = "1/1";
        int width = fontRenderer.getStringWidth(string);
        fontRenderer.drawString(string, guiLeft + xSize / 2 - width / 2, guiTop + 4, 0xFFFFFFFF);
    }

    private void rebuildButtons() {
//        clearElements();

//        addElement("button_back", buttonScrollBack);
//        addElement("button_forward", buttonScrollForward);

        for (int i=0; i<LINES_PER_PAGE; i++) {
            final int index = i;
            Parameter parameter = i < parameters.size() ? parameters.get(i) : null;

            if (parameter == null) {
//                GuiWrappedButton button = new GuiWrappedButton(this, 8, 8 + 22 * i, xSize - 8, 20, "Preference " + i);
//                button.setActive(false);
//                button.onClick((b) -> onPreferenceButtonClicked(index));

//                preferenceButtons[i] = button;
            } else {
//                GuiWrappedButton button = new GuiWrappedButton(this, 8, 8 + 22 * i, xSize - 8, 20, parameter.key.type + ": " + parameter.key.key);
//                button.onClick((b) -> onPreferenceButtonClicked(index));
//
//                preferenceButtons[i] = button;
            }
        }

        for (int i=0; i<LINES_PER_PAGE; i++) {
//            addElement("button_param_" + i, preferenceButtons[i]);
        }
    }

    private void scroll(int delta) {

    }

    private void onPreferenceButtonClicked(int index) {

    }
}
