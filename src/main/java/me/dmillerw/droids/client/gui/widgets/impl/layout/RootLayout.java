package me.dmillerw.droids.client.gui.widgets.impl.layout;

import me.dmillerw.droids.client.gui.GuiBase;
import me.dmillerw.droids.client.gui.WidgetSize;
import me.dmillerw.droids.client.gui.widgets.ContainerWidget;
import me.dmillerw.droids.client.gui.widgets.Dimensions;
import me.dmillerw.droids.client.gui.widgets.Point;
import me.dmillerw.droids.client.gui.widgets.Widget;

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
