package me.dmillerw.flow.widgets.impl.layout;

import me.dmillerw.flow.modal.GuiBase;
import me.dmillerw.flow.WidgetSize;
import me.dmillerw.flow.widgets.ContainerWidget;
import me.dmillerw.flow.Dimensions;
import me.dmillerw.flow.Point;
import me.dmillerw.flow.widgets.Widget;

public class RootLayout extends ContainerWidget {

    public static RootLayout flexible(Widget child) {
        return new RootLayout(0, 0, 0, 0, child, true);
    }

    public static RootLayout fromGui(GuiBase gui, Widget child) {
        return new RootLayout(gui.getGuiLeft(), gui.getGuiTop(), gui.getXSize(), gui.getYSize(), child, false);
    }

    public int x;
    public int y;
    public int width;
    public int height;

    private boolean flexible = false;

    public Widget child;

    private RootLayout(int x, int y, int width, int height, Widget child, boolean flexible) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.child = child;
        this.flexible = flexible;
        this.currentPosition = new Point(x, y);
        this.setRequestedDimensions(new WidgetSize(WidgetSize.SizingType.FIT_TO_CONTENT), new WidgetSize(WidgetSize.SizingType.FIT_TO_CONTENT));

        addSubwidget(child);
    }

    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        if (!flexible) maxBounds = new Dimensions(width, height);
        return child.getMinimumDimensions(maxBounds);
    }

    @Override
    public void layoutChildren() {
        child.setPosition(currentPosition);
        if (child instanceof ContainerWidget)
            ((ContainerWidget) child).layoutChildren();
    }
}
