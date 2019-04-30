package me.dmillerw.flow.modal;

import me.dmillerw.flow.Insets;
import me.dmillerw.flow.Layout;
import me.dmillerw.flow.Point;
import me.dmillerw.flow.widgets.impl.Rectangle;
import me.dmillerw.flow.widgets.impl.layout.LinearLayout;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiModalLayout extends GuiModal {

    private Layout layout;

    public GuiModalLayout(ResourceLocation texture) {
        super(texture);
    }

    @Override
    protected void initialize() {
//        setGuiDimensions(200, 100);
//        layout = Layout.shrinkToFit(this, LinearLayout.buider()
//                .orientation(LinearLayout.Orientation.VERTICAL)
//                .child(LinearLayout.buider()
//                        .orientation(LinearLayout.Orientation.HORIZONTAL)
//                        .padding(Insets.all(4))
//                        .child(Button.builder().text("SEND").height(new WidgetSize(50)).buildWidget())
//                        .child(Button.builder().text("SEND").height(new WidgetSize(100)).buildWidget())
//                        .child(Button.builder().text("SEND").height(new WidgetSize(150)).buildWidget())
//                        .child(LinearLayout.buider()
//                                .orientation(LinearLayout.Orientation.VERTICAL)
//                                .child(Button.builder().text("SEND").width(new WidgetSize(50)).buildWidget())
//                                .child(Button.builder().text("SEND").width(new WidgetSize(100)).buildWidget())
//                                .child(Button.builder().text("SEND").width(new WidgetSize(150)).buildWidget())
//                                .buildWidget())
//                        .buildWidget()).buildWidget());

        layout = Layout.shrinkToFit(this, LinearLayout.buider()
                        .orientation(LinearLayout.Orientation.HORIZONTAL)
                        .padding(Insets.all(5))
                        .child(Rectangle.builder().height(100).width(100).borderStyle(Rectangle.BorderStyle.INSET).color(0x000000).buildWidget()).buildWidget());

                setGuiDimensions(layout.getLayoutDimensions().getWidth(), layout.getLayoutDimensions().getHeight());

        layout.setLayoutPosition(new Point(guiLeft, guiTop));

//        setGuiDimensions(200, 200);

//        layout = Layout.fromGui(this, AlignedLayout.builder()
//                .child(AlignedLayout.Alignment.TOP_LEFT, Text.buider().text("Hello").buildWidget())
//                .child(AlignedLayout.Alignment.BOTTOM_LEFT, LinearLayout.buider()
//                        .orientation(LinearLayout.Orientation.HORIZONTAL)
//                        .child(TextField.builder()
//                                .margins(Insets.all(5))
//                                .drawBackground()
//                                .buildWidget())
//                        .child(Button.builder()
//                                .margins(Insets.all(5))
//                                .text("Send")
//                                .buildWidget())
//                        .buildWidget())
//                .buildWidget());

//        layout = Layout.fromGui(this, LinearLayout.buider()
//        .orientation(LinearLayout.Orientation.VERTICAL)
//        .child(new Rectangle(-1, -1))
//        .child(new Rectangle(-1, -1))
//        .child(new Rectangle(-1, -1))
//        .child(new Rectangle(-1, -1))
//        .buildWidget());
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        try {
            layout.draw(mouseX, mouseY);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        layout.onMouseClick(mouseX, mouseY, mouseButton);

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        if (keyCode == Keyboard.KEY_R) {
            initialize();
        }

        layout.onKeyTyped(typedChar, keyCode);
    }
}
