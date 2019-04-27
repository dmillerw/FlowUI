package me.dmillerw.droids.client.gui;

import com.google.common.collect.Maps;
import me.dmillerw.droids.common.ModInfo;
import me.dmillerw.droids.common.job.JobDefinition;
import me.dmillerw.droids.common.job.JobRegistry;
import me.dmillerw.droids.common.network.packets.SUpdateJobCard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuiSetJob extends GuiScreen {

    private static class JobBox {

        public final String jobKey;
        public final ItemStack renderIcon;
        public final String title;
        public final List<String> description;
        public final int height;

        private JobBox(String jobKey, ItemStack renderIcon, String title, List<String> description, int height) {
            this.jobKey = jobKey;
            this.renderIcon = renderIcon;
            this.title = title;
            this.description = description;
            this.height = height;
        }
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.ID, "textures/gui/set_job.png");

    private static final int BUTTON_CANCEL = 0;
    private static final int BUTTON_OK = 1;

    private static final int BOX_WIDTH = 160;

    private int xSize;
    private int ySize;
    private int guiLeft;
    private int guiTop;

    private final ItemStack itemStack;

    private final Map<String, JobBox> jobRenderBoxes = Maps.newHashMap();
    private final List<JobDefinition> jobs = new ArrayList<>(JobRegistry.INSTANCE.getJobs());

    private GuiButtonExt buttonCancel;
    private GuiButtonExt buttonOk;

    private float scrollOffset;
    private float totalScrollHeight;
    private int selectedIndex = -1;

    public GuiSetJob(ItemStack itemStack) {
        this.itemStack = itemStack;

        this.xSize = 195;
        this.ySize = 245;
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        super.setWorldAndResolution(mc, width, height);

        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;

        calculateRenderBoxes();

        buttonCancel = new GuiButtonExt(BUTTON_CANCEL, guiLeft + 8, guiTop + 229, BOX_WIDTH / 2 - 2, 11, "Cancel");
        buttonOk = new GuiButtonExt(BUTTON_OK, guiLeft + 8 + BOX_WIDTH / 2 + 4, guiTop + 229, BOX_WIDTH / 2 - 2, 11, "Select");
        buttonOk.enabled = false;

        addButton(buttonCancel);
        addButton(buttonOk);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final float delta = Mouse.getDWheel();
        if (delta != 0) {
            onMouseScroll(mouseX, mouseY, delta);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int currentYIndex = 0;
        for (int i=0; i<jobs.size(); i++) {
            final JobDefinition job = jobs.get(i);
            final JobBox box = jobRenderBoxes.get(job.key);
            if (box == null)
                continue;

            int x = guiLeft + 9;
            int y = guiTop + 21 - (int)scrollOffset + currentYIndex;

            if (y > guiTop + 205)
                continue;

            int fontColor = i == selectedIndex ? 0xFF00FF00 : 0xFFFFFFFF;

            mc.getRenderItem().renderItemIntoGUI(box.renderIcon, x + 2, y + 2);
            fontRenderer.drawString(job.key, x + 25, y + 7, fontColor);

//            for (int j=0; j<box.description.size(); j++) {
//                String string = box.description.get(j);
//                fontRenderer.drawString(string, x + 5, y + 26 + 10 * j, fontColor);
//            }

            currentYIndex += box.height;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void onMouseScroll(int mouseX, int mouseY, float delta) {
        if (delta > 0)
            scrollOffset--;
        else if (delta < 0)
            scrollOffset++;

        if (scrollOffset < 0.0)
            scrollOffset = 0;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int currentYIndex = 0;
        for (int i=0; i<jobs.size(); i++) {
            final JobDefinition job = jobs.get(i);
            final JobBox box = jobRenderBoxes.get(job.key);
            if (box == null)
                continue;

            int y = guiTop + 21 - (int)scrollOffset + currentYIndex;

            if (y > guiTop + 236)
                continue;
            else if (y + box.height < guiTop + 21)
                continue;

            if (mouseX >= guiLeft + 9 && mouseX <= guiLeft + 168) {
                if (mouseY >= y && mouseY <= y + box.height) {
                    selectedIndex = i;
                    buttonList.get(1).enabled = true;
                    return;
                }
            }

            currentYIndex += box.height;
        }
    }

    private void calculateRenderBoxes() {
        final int xyEdgePadding = 5;
        final int iconHeight = 18;
        final int iconTextSpace = 5;
        final int lineSpace = 10;

        int sumHeight = 0;
        for (JobDefinition definition : jobs) {
            int totalHeight = 0;
            totalHeight += xyEdgePadding + iconHeight;

            final int width = BOX_WIDTH - xyEdgePadding * 2;
            List<String> lines = fontRenderer.listFormattedStringToWidth(definition.description, width);

//            totalHeight += 10 * lines.size() + 10;

            sumHeight += totalHeight;

            jobRenderBoxes.put(definition.key, new JobBox(
                    definition.key, definition.getIcon(), definition.key, lines, totalHeight));
        }

        totalScrollHeight = sumHeight;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case BUTTON_OK: {
                SUpdateJobCard packet = new SUpdateJobCard();
                packet.jobId = jobs.get(selectedIndex).key;
                packet.sendToServer();

                mc.displayGuiScreen(null);

                break;
            }
            case BUTTON_CANCEL: {
                mc.displayGuiScreen(null);
                break;
            }
        }
    }
}
