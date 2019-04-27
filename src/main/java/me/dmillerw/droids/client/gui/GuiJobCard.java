//package me.dmillerw.droids.client.gui;
//
//import com.google.common.base.Predicate;
//import com.google.common.collect.Lists;
//import me.dmillerw.droids.client.gui.element.GuiCallbackButton;
//import me.dmillerw.droids.client.gui.element.GuiCallbackTextField;
//import me.dmillerw.droids.common.ModInfo;
//import me.dmillerw.droids.common.inventory.ContainerNull;
//import me.dmillerw.droids.common.item.ItemJobCard;
//import me.dmillerw.droids.common.job.JobDefinition;
//import me.dmillerw.droids.common.job.parameter.Parameter;
//import me.dmillerw.droids.common.job.parameter.ParameterKey;
//import me.dmillerw.droids.common.job.parameter.ParameterMap;
//import me.dmillerw.droids.common.network.packets.SUpdateJobCard;
//import me.dmillerw.droids.common.util.type.Area;
//import me.dmillerw.droids.common.util.type.BlockFilter;
//import me.dmillerw.droids.common.util.type.ItemFilter;
//import net.minecraft.client.gui.GuiInput;
//import net.minecraft.client.gui.inventory.GuiContainer;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import org.codehaus.plexus.util.StringUtils;
//import org.lwjgl.input.Keyboard;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GuiJobCard extends GuiContainer {
//
//    private static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.ID, "textures/gui/configurator_empty.png");
//
//    private static final Predicate<String> NUMERIC_VALIDATOR = new Predicate<String>() {
//        @Override
//        public boolean apply(String s) {
//            return s.isEmpty() || StringUtils.isNumeric(s.replace("-", ""));
//        }
//    };
//
//    private static final int BUTTON_SET_JOB = 0;
//    private static final int BUTTON_PARAM_BACK = 1;
//    private static final int BUTTON_PARAM_FORWARD = 2;
//
//    private static final int PARAM_SPACE_X = 8;
//    private static final int PARAM_SPACE_Y = 29;
//    private static final int PARAM_SPACE_W = 161;
//    private static final int PARAM_SPACE_H = 140;
//
//    private final EntityPlayer player;
//    private final ItemStack item;
//
//    private final List<GuiInput> fieldList = Lists.newArrayList();
//
//    private List<String> errors = Lists.newArrayList();
//
//    private GuiCallbackButton buttonCancel;
//    private GuiCallbackButton buttonSave;
//
//    private JobDefinition currentJob;
//    private List<Parameter> parameters;
//    private ParameterMap parameterMap;
//    private int selectedParameter = 0;
//
//    private boolean didParamsChange = false;
//
//    public GuiJobCard(EntityPlayer player, ItemStack item) {
//        super(new ContainerNull());
//
//        this.xSize = 176;
//        this.ySize = 238;
//
//        this.player = player;
//        this.item = item;
//    }
//
//    @Override
//    public void initGui() {
//        super.initGui();
//
//        currentJob = ItemJobCard.getJobFromCard(item);
//        parameters = currentJob != null ? currentJob.getParameters() : new ArrayList<>();
//        parameterMap = currentJob != null ? ItemJobCard.getParametersFromCard(currentJob, item) : null;
//
//        rebuildElements();
//    }
//
//    @Override
//    public void updateScreen() {
//        super.updateScreen();
//    }
//
//    private void rebuildElements() {
//        this.buttonList.clear();
//        this.labelList.clear();
//        this.fieldList.clear();
//
////        this.selectedParameter = 0;
//
//        ItemStack card = item;
//        if (card.isEmpty())
//            return;
//
//        if (currentJob == null) {
//            String text = "Set Task";
//            int textWidth = fontRenderer.getStringWidth(text) * 2;
//            int x = width / 2 - textWidth / 2;
//            int y = 35;
//
//            addButton(new GuiCallbackButton(BUTTON_SET_JOB, x, guiTop + y, textWidth, 20, text)
//                    .onClick((b) -> mc.displayGuiScreen(new GuiSetJob(item))));
//
//            return;
//        }
//
//        addButton(new GuiCallbackButton(BUTTON_PARAM_BACK, guiLeft + 30, guiTop + 7, 20, 18, "<")
//                .onClick((b) -> decParamIndex()));
//
//        addButton(new GuiCallbackButton(BUTTON_PARAM_FORWARD, guiLeft + 148, guiTop + 7, 20, 18, ">")
//                .onClick((b) -> incParamIndex()));
//
//        buttonCancel = new GuiCallbackButton(0, guiLeft + xSize + 4, guiTop + 4, 18, 18, "C");
//        buttonSave = new GuiCallbackButton(0, guiLeft + xSize + 4, guiTop + 28, 18, 18, "O").onClick((button) -> {
//            if (didParamsChange && errors.isEmpty()) {
//                didParamsChange = false;
//                buttonSave.enabled = false;
//
//
//            }
//
////            ItemJobCard.updateParameters(lastCard, parameterMap);
//
//            SUpdateJobCard packet = new SUpdateJobCard();
//            packet.jobId = currentJob.key;
//            packet.parameterMap = parameterMap;
//
//            packet.sendToServer();
//        });
//
//        addButton(buttonCancel);
//        addButton(buttonSave);
//
//        ParameterKey parameter = parameters.get(selectedParameter).key;
//        switch (parameter.type) {
//            case NUMBER: {
//                buildNumberParam(parameter, parameterMap.getNumber(parameter));
//            }
//
//            case BLOCK_POS: {
//                buildBlockPosParam(parameter);
//                break;
//            }
//
//            case AREA: {
//                buildAreaParam(parameter);
//                break;
//            }
//
//            case ITEM: {
//                buildItemParam(parameter, parameterMap.getItemFilter(parameter));
//                break;
//            }
//
//            case BLOCK: {
//                buildBlockParam(parameter, parameterMap.getBlockFilter(parameter));
//                break;
//            }
//        }
//    }
//
//    @Override
//    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
//        super.mouseClicked(mouseX, mouseY, mouseButton);
//
//        for (GuiInput field : fieldList) {
//            if (field.mouseClicked(mouseX, mouseY, mouseButton)) {
//                return;
//            }
//        }
//    }
//
//    @Override
//    protected void keyTyped(char typedChar, int keyCode) throws IOException {
//        super.keyTyped(typedChar, keyCode);
//
//        for (GuiInput field : fieldList) {
//            if (field.textboxKeyTyped(typedChar, keyCode)) {
//                return;
//            }
//        }
//
//        if (keyCode == Keyboard.KEY_R)
//            rebuildElements();
//    }
//
//    @Override
//    public void onGuiClosed() {
//        super.onGuiClosed();
//
//
//    }
//
//    @Override
//    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        this.drawDefaultBackground();
//        super.drawScreen(mouseX, mouseY, partialTicks);
//        this.renderHoveredToolTip(mouseX, mouseY);
//
//        fieldList.forEach(GuiInput::drawTextBox);
//
//        if (currentJob == null)
//            return;
//
//        if (parameters == null || parameters.isEmpty())
//            return;
//
//        String text = parameters.get(selectedParameter).key.key;
//        int textWidth = fontRenderer.getStringWidth(text);
//
//        fontRenderer.drawString(text, guiLeft + 100 - textWidth / 2, guiTop + 10, 0);
//
//        ParameterKey parameter = parameters.get(selectedParameter).key;
//        switch (parameter.type) {
//            case NUMBER: {
//                drawNumberParam(parameterMap.getNumber(parameter), mouseX, mouseY);
//                break;
//            }
//
//            case BLOCK_POS: {
//                drawBlockPosParam(parameterMap.getBlockPos(parameter), mouseX, mouseY);
//                break;
//            }
//
//            case AREA: {
//                drawAreaParam(parameterMap.getArea(parameter), mouseX, mouseY);
//                break;
//            }
//
//            case ITEM: {
//                drawItemParam(parameterMap.getItemFilter(parameter), mouseX, mouseY);
//                break;
//            }
//
//            case BLOCK: {
//                drawBlockParam(parameterMap.getBlockFilter(parameter), mouseX, mouseY);
//                break;
//            }
//        }
//
//        if (buttonSave != null) {
//            if (buttonSave.isMouseOver()) {
//                drawHoveringText(errors, mouseX, mouseY, fontRenderer);
//            }
//        }
//    }
//
//    @Override
//    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        this.mc.getTextureManager().bindTexture(TEXTURE);
//        int i = (this.width - this.xSize) / 2;
//        int j = (this.height - this.ySize) / 2;
//        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
//    }
//
//    private void decParamIndex() {
//        selectedParameter--;
//        if (selectedParameter < 0)
//            selectedParameter = 0;
//
//        rebuildElements();
//    }
//
//    // TODO:
//    // Finish implemeting this GUI, BlockPos is done, needs to be replcated for Area param
//    // Also, packet for updating ParamMap on ITemStack
//
//    private void incParamIndex() {
//        selectedParameter++;
//        if (selectedParameter >= currentJob.getParameters().size())
//            selectedParameter = currentJob.getParameters().size() - 1;
//
//        rebuildElements();
//    }
//
//    private void calculateErrors() {
//        errors.clear();
//
//        if (currentJob == null)
//            return;
//
//        currentJob.calculateParameterErrors(parameterMap, errors);
//
//        if (errors.isEmpty()) {
//            buttonSave.enabled = true;
//        } else {
//            buttonSave.enabled = false;
//        }
//    }
//
//    /* PARAM SPECIFIC */
//
//    private void buildNumberParam(ParameterKey key, double value) {
//        int fieldX = guiLeft + PARAM_SPACE_X + 4;
//        int fieldY = guiTop + PARAM_SPACE_Y + 4;
//        // Dec/Inc buttons, width create 18
//        // Margin create 4 on either side
//        // Text input, fills remaining space
//        int fieldWidth = PARAM_SPACE_W - 36 - 8;
//
//        GuiCallbackTextField field = new GuiCallbackTextField(0, fontRenderer, fieldX, fieldY, fieldWidth, 20);
//        field.setText(Double.toString(value));
//        field.onChanged((f) -> {
//            String text = f.getText();
//            int i = 0;
//
//            try {
//                i = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            updateParameter(key, i);
//        });
//
//        fieldList.add(field);
//    }
//
//    private void buildBlockPosParam(ParameterKey key) {
//        BlockPos value = parameterMap.getBlockPos(key);
//
//        int fieldX = guiLeft + PARAM_SPACE_X + 4 + 18;
//        int fieldY = guiTop + PARAM_SPACE_Y + 4;
//        // Dec/Inc buttons, width create 18
//        // Margin create 4 on either side
//        // Text input, fills remaining space
//        int fieldWidth = PARAM_SPACE_W - 36 - 8;
//
//        GuiCallbackTextField inputX = new GuiCallbackTextField(0, fontRenderer, fieldX, fieldY, fieldWidth, 20);
//        inputX.setText(Integer.toString(value.getX()));
//        inputX.setValidator(NUMERIC_VALIDATOR);
//        inputX.onChanged((f) -> {
//            BlockPos subvalue = parameterMap.getBlockPos(key);
//            String text = f.getText();
//            int i = 0;
//
//            try {
//                i = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            updateParameter(key, new BlockPos(i, subvalue.getY(), subvalue.getZ()));
//        });
//
//        GuiCallbackButton inputXDec = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X, guiTop + PARAM_SPACE_Y + 4, 18, 20, "-");
//        inputXDec.onClick((b) -> {
//            BlockPos subvalue = parameterMap.getBlockPos(key);
//            int x = subvalue.getX();
//            x = x - 1;
//
//            inputX.setText(Integer.toString(x));
//
////            updateParameter(key, new BlockPos(x, subvalue.getY(), subvalue.getZ()));
//        });
//
//        GuiCallbackButton inputXInc = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W - 18, guiTop + PARAM_SPACE_Y + 4, 18, 20, "+");
//        inputXInc.onClick((b) -> {
//            BlockPos subvalue = parameterMap.getBlockPos(key);
//            int x = subvalue.getX();
//            x = x + 1;
//
//            inputX.setText(Integer.toString(x));
//
////            updateParameter(key, new BlockPos(x, subvalue.getY(), subvalue.getZ()));
//        });
//
//        GuiCallbackTextField inputY = new GuiCallbackTextField(0, fontRenderer, fieldX, fieldY + 24, fieldWidth, 20);
//        inputY.setText(Integer.toString(value.getY()));
//        inputY.setValidator(NUMERIC_VALIDATOR);
//        inputY.onChanged((f) -> {
//            BlockPos subvalue = parameterMap.getBlockPos(key);
//            String text = f.getText();
//            int y = 0;
//
//            try {
//                y = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            updateParameter(key, new BlockPos(subvalue.getX(), y, subvalue.getZ()));
//        });
//
//        GuiCallbackButton inputYDec = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X, guiTop + PARAM_SPACE_Y + 28, 18, 20, "-");
//        inputYDec.onClick((b) -> {
//            BlockPos subvalue = parameterMap.getBlockPos(key);
//            int y = subvalue.getY();
//            y = y - 1;
//
//            inputY.setText(Integer.toString(y));
//
////            updateParameter(key, new BlockPos(subvalue.getX(), y, subvalue.getZ()));
//        });
//
//        GuiCallbackButton inputYInc = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W - 18, guiTop + PARAM_SPACE_Y + 28, 18, 20, "+");
//        inputYInc.onClick((b) -> {
//            BlockPos subvalue = parameterMap.getBlockPos(key);
//            int y = subvalue.getY();
//            y = y + 1;
//
//            inputY.setText(Integer.toString(y));
//
////            updateParameter(key, new BlockPos(subvalue.getX(), y, subvalue.getZ()));
//        });
//
//        GuiCallbackTextField inputZ = new GuiCallbackTextField(0, fontRenderer, fieldX, fieldY + 48, fieldWidth, 20);
//        inputZ.setText(Integer.toString(value.getZ()));
//        inputZ.setValidator(NUMERIC_VALIDATOR);
//        inputZ.onChanged((f) -> {
//            BlockPos subvalue = parameterMap.getBlockPos(key);
//            String text = f.getText();
//            int i = 0;
//
//            try {
//                i = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            updateParameter(key, new BlockPos(subvalue.getX(), subvalue.getY(), i));
//        });
//
//        GuiCallbackButton inputZDec = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X, guiTop + PARAM_SPACE_Y + 52, 18, 20, "-");
//        inputZDec.onClick((b) -> {
//            BlockPos subvalue = parameterMap.getBlockPos(key);
//            int z = subvalue.getZ();
//            z = z - 1;
//
//            inputZ.setText(Integer.toString(z));
//
////            updateParameter(key, new BlockPos(subvalue.getX(), subvalue.getY(), z));
//        });
//
//        GuiCallbackButton inputZInc = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W - 18, guiTop + PARAM_SPACE_Y + 52, 18, 20, "+");
//        inputZInc.onClick((b) -> {
//            BlockPos subvalue = parameterMap.getBlockPos(key);
//            int z = subvalue.getZ();
//            z = z + 1;
//
//            inputZ.setText(Integer.toString(z));
//
////            updateParameter(key, new BlockPos(subvalue.getX(), subvalue.getY(), z));
//        });
//
//        fieldList.add(inputX);
//        buttonList.add(inputXDec);
//        buttonList.add(inputXInc);
//
//        fieldList.add(inputY);
//        buttonList.add(inputYDec);
//        buttonList.add(inputYInc);
//
//        fieldList.add(inputZ);
//        buttonList.add(inputZDec);
//        buttonList.add(inputZInc);
//    }
//
//    private void buildAreaParam(ParameterKey key) {
//        Area value = parameterMap.getArea(key);
//        BlockPos start = value.startPos;
//        BlockPos end = value.endPos;
//
//        int fieldX = guiLeft + PARAM_SPACE_X + 4 + 12;
//        int fieldY = guiTop + PARAM_SPACE_Y + 4;
//        // Dec/Inc buttons, width create 18
//        // Margin create 4 on either side
//        // Text inputStart, fills remaining space
//        int fieldWidth = PARAM_SPACE_W / 2 - 24 - 8;
//
//        GuiCallbackTextField inputStartX = new GuiCallbackTextField(0, fontRenderer, fieldX, fieldY, fieldWidth, 20);
//        inputStartX.setText(Integer.toString(start.getX()));
//        inputStartX.setValidator(NUMERIC_VALIDATOR);
//        inputStartX.onChanged((f) -> {
//            BlockPos substart = parameterMap.getArea(key).startPos;
//            String text = f.getText();
//            int i = 0;
//
//            try {
//                i = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            setAreaParamStart(key, new BlockPos(i, substart.getY(), substart.getZ()));
//        });
//
//        GuiCallbackButton inputStartXDec = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X, guiTop + PARAM_SPACE_Y + 4, 12, 20, "-");
//        inputStartXDec.onClick((b) -> {
//            BlockPos substart = parameterMap.getArea(key).startPos;
//            int x = substart.getX();
//            x = x - 1;
//
//            inputStartX.setText(Integer.toString(x));
//
////            setAreaParamStart(key, new BlockPos(x, substart.getY(), substart.getZ()));
//        });
//
//        GuiCallbackButton inputStartXInc = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W / 2 - 12, guiTop + PARAM_SPACE_Y + 4, 12, 20, "+");
//        inputStartXInc.onClick((b) -> {
//            BlockPos substart = parameterMap.getArea(key).startPos;
//            int x = substart.getX();
//            x = x + 1;
//
//            inputStartX.setText(Integer.toString(x));
//
////            setAreaParamStart(key, new BlockPos(x, substart.getY(), substart.getZ()));
//        });
//
//        GuiCallbackTextField inputStartY = new GuiCallbackTextField(0, fontRenderer, fieldX, fieldY + 24, fieldWidth, 20);
//        inputStartY.setText(Integer.toString(start.getY()));
//        inputStartY.setValidator(NUMERIC_VALIDATOR);
//        inputStartY.onChanged((f) -> {
//            BlockPos substart = parameterMap.getArea(key).startPos;
//            String text = f.getText();
//            int i = 0;
//
//            try {
//                i = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            setAreaParamStart(key, new BlockPos(substart.getX(), i, substart.getZ()));
//        });
//
//        GuiCallbackButton inputStartYDec = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X, guiTop + PARAM_SPACE_Y + 28, 12, 20, "-");
//        inputStartYDec.onClick((b) -> {
//            BlockPos substart = parameterMap.getArea(key).startPos;
//            int y = substart.getY();
//            y = y - 1;
//
//            inputStartY.setText(Integer.toString(y));
//
////            setAreaParamStart(key, new BlockPos(substart.getX(), y, substart.getZ()));
//        });
//
//        GuiCallbackButton inputStartYInc = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W / 2 - 12, guiTop + PARAM_SPACE_Y + 28, 12, 20, "+");
//        inputStartYInc.onClick((b) -> {
//            BlockPos substart = parameterMap.getArea(key).startPos;
//            int y = substart.getY();
//            y = y + 1;
//
//            inputStartY.setText(Integer.toString(y));
//
////            setAreaParamStart(key, new BlockPos(substart.getX(), y, substart.getZ()));
//        });
//
//        GuiCallbackTextField inputStartZ = new GuiCallbackTextField(0, fontRenderer, fieldX, fieldY + 48, fieldWidth, 20);
//        inputStartZ.setText(Integer.toString(start.getZ()));
//        inputStartZ.setValidator(NUMERIC_VALIDATOR);
//        inputStartZ.onChanged((f) -> {
//            BlockPos substart = parameterMap.getArea(key).startPos;
//            String text = f.getText();
//            int i = 0;
//
//            try {
//                i = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            setAreaParamStart(key, new BlockPos(substart.getX(), substart.getY(), i));
//        });
//
//        GuiCallbackButton inputStartZDec = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X, guiTop + PARAM_SPACE_Y + 52, 12, 20, "-");
//        inputStartZDec.onClick((b) -> {
//            BlockPos substart = parameterMap.getArea(key).startPos;
//            int z = substart.getZ();
//            z = z - 1;
//
//            inputStartZ.setText(Integer.toString(z));
//
////            setAreaParamStart(key, new BlockPos(substart.getX(), substart.getY(), z));
//        });
//
//        GuiCallbackButton inputStartZInc = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W / 2 - 12, guiTop + PARAM_SPACE_Y + 52, 12, 20, "+");
//        inputStartZInc.onClick((b) -> {
//            BlockPos substart = parameterMap.getArea(key).startPos;
//            int z = substart.getZ();
//            z = z + 1;
//
//            inputStartZ.setText(Integer.toString(z));
//
////            setAreaParamStart(key, new BlockPos(substart.getX(), substart.getY(), z));
//        });
//
//        fieldList.add(inputStartX);
//        buttonList.add(inputStartXDec);
//        buttonList.add(inputStartXInc);
//
//        fieldList.add(inputStartY);
//        buttonList.add(inputStartYDec);
//        buttonList.add(inputStartYInc);
//
//        fieldList.add(inputStartZ);
//        buttonList.add(inputStartZDec);
//        buttonList.add(inputStartZInc);
//
//        GuiCallbackTextField inputEndX = new GuiCallbackTextField(0, fontRenderer, fieldX + PARAM_SPACE_W / 2, fieldY, fieldWidth, 20);
//        inputEndX.setText(Integer.toString(end.getX()));
//        inputEndX.setValidator(NUMERIC_VALIDATOR);
//        inputEndX.onChanged((f) -> {
//            BlockPos subend = parameterMap.getArea(key).endPos;
//            String text = f.getText();
//            int i = 0;
//
//            try {
//                i = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            setAreaParamEnd(key, new BlockPos(i, subend.getY(), subend.getZ()));
//        });
//
//        GuiCallbackButton inputEndXDec = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W / 2, guiTop + PARAM_SPACE_Y + 4, 12, 20, "-");
//        inputEndXDec.onClick((b) -> {
//            BlockPos subend = parameterMap.getArea(key).endPos;
//            int x = subend.getX();
//            x = x - 1;
//
//            inputEndX.setText(Integer.toString(x));
//
////            setAreaParamEnd(key, new BlockPos(x, subend.getY(), subend.getZ()));
//        });
//
//        GuiCallbackButton inputEndXInc = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W - 12, guiTop + PARAM_SPACE_Y + 4, 12, 20, "+");
//        inputEndXInc.onClick((b) -> {
//            BlockPos subend = parameterMap.getArea(key).endPos;
//            int x = subend.getX();
//            x = x + 1;
//
//            inputEndX.setText(Integer.toString(x));
//
////            setAreaParamEnd(key, new BlockPos(x, subend.getY(), subend.getZ()));
//        });
//
//        GuiCallbackTextField inputEndY = new GuiCallbackTextField(0, fontRenderer, fieldX + PARAM_SPACE_W / 2, fieldY + 24, fieldWidth, 20);
//        inputEndY.setText(Integer.toString(end.getY()));
//        inputEndY.setValidator(NUMERIC_VALIDATOR);
//        inputEndY.onChanged((f) -> {
//            BlockPos subend = parameterMap.getArea(key).endPos;
//            String text = f.getText();
//            int i = 0;
//
//            try {
//                i = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            setAreaParamEnd(key, new BlockPos(subend.getX(), i, subend.getZ()));
//        });
//
//        GuiCallbackButton inputEndYDec = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W / 2, guiTop + PARAM_SPACE_Y + 28, 12, 20, "-");
//        inputEndYDec.onClick((b) -> {
//            BlockPos subend = parameterMap.getArea(key).endPos;
//            int y = subend.getY();
//            y = y - 1;
//
//            inputEndY.setText(Integer.toString(y));
//
////            setAreaParamEnd(key, new BlockPos(subend.getX(), y, subend.getZ()));
//        });
//
//        GuiCallbackButton inputEndYInc = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W - 12, guiTop + PARAM_SPACE_Y + 28, 12, 20, "+");
//        inputEndYInc.onClick((b) -> {
//            BlockPos subend = parameterMap.getArea(key).endPos;
//            int y = subend.getY();
//            y = y + 1;
//
//            inputEndY.setText(Integer.toString(y));
//
////            setAreaParamEnd(key, new BlockPos(subend.getX(), y, subend.getZ()));
//        });
//
//        GuiCallbackTextField inputEndZ = new GuiCallbackTextField(0, fontRenderer, fieldX + PARAM_SPACE_W / 2, fieldY + 48, fieldWidth, 20);
//        inputEndZ.setText(Integer.toString(end.getZ()));
//        inputEndZ.setValidator(NUMERIC_VALIDATOR);
//        inputEndZ.onChanged((f) -> {
//            BlockPos subend = parameterMap.getArea(key).startPos;
//            String text = f.getText();
//            int i = 0;
//
//            try {
//                i = Integer.parseInt(text);
//            } catch (Exception ignore) {
//            }
//
//            setAreaParamEnd(key, new BlockPos(subend.getX(), subend.getY(), i));
//        });
//
//        GuiCallbackButton inputEndZDec = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W / 2, guiTop + PARAM_SPACE_Y + 52, 12, 20, "-");
//        inputEndZDec.onClick((b) -> {
//            BlockPos subend = parameterMap.getArea(key).endPos;
//            int z = subend.getZ();
//            z = z - 1;
//
//            inputEndZ.setText(Integer.toString(z));
//
////            setAreaParamEnd(key, new BlockPos(subend.getX(), subend.getY(), z));
//        });
//
//        GuiCallbackButton inputEndZInc = new GuiCallbackButton(0, guiLeft + PARAM_SPACE_X + PARAM_SPACE_W - 12, guiTop + PARAM_SPACE_Y + 52, 12, 20, "+");
//        inputEndZInc.onClick((b) -> {
//            BlockPos subend = parameterMap.getArea(key).endPos;
//            int z = subend.getZ();
//            z = z + 1;
//
//            inputEndZ.setText(Integer.toString(z));
//
////            setAreaParamEnd(key, new BlockPos(subend.getX(), subend.getY(), z));
//        });
//
//        fieldList.add(inputEndX);
//        buttonList.add(inputEndXDec);
//        buttonList.add(inputEndXInc);
//
//        fieldList.add(inputEndY);
//        buttonList.add(inputEndYDec);
//        buttonList.add(inputEndYInc);
//
//        fieldList.add(inputEndZ);
//        buttonList.add(inputEndZDec);
//        buttonList.add(inputEndZInc);
//    }
//
//    private void buildItemParam(ParameterKey key, ItemFilter value) {
//
//    }
//
//    private void buildBlockParam(ParameterKey key, BlockFilter value) {
//
//    }
//
//    private void drawNumberParam(double value, int mouseX, int mouseY) {
//
//    }
//
//    private void drawBlockPosParam(BlockPos value, int mouseX, int mouseY) {
//
//    }
//
//    private void drawAreaParam(Area value, int mouseX, int mouseY) {
//
//    }
//
//    private void drawItemParam(ItemFilter value, int mouseX, int mouseY) {
//
//    }
//
//    private void drawBlockParam(BlockFilter value, int mouseX, int mouseY) {
//
//    }
//
//    private void setAreaParamStart(ParameterKey key, BlockPos value) {
//        Area area = parameterMap.getArea(key);
//        System.out.println("area param start");
//        updateParameter(key, new Area(value, area.endPos));
//    }
//
//    private void setAreaParamEnd(ParameterKey key, BlockPos value) {
//        Area area = parameterMap.getArea(key);
//        System.out.println("area param end");
//        updateParameter(key, new Area(area.startPos, value));
//    }
//
//    private void updateParameter(ParameterKey key, Object value) {
//        String v = value.toString();
//        if (value instanceof Area)
//            v = ((Area) value).startPos.toString() + " | " + ((Area) value).endPos.toString();
//
//        System.out.println(key.type + ":" + key.key + " = " + v);
//
//        parameterMap.setParameter(key, value);
//
//        didParamsChange = true;
//        buttonSave.enabled = true;
//
//        calculateErrors();
//    }
//}
