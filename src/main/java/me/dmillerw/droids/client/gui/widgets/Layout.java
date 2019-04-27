package me.dmillerw.droids.client.gui.widgets;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.dmillerw.droids.client.gui.GuiBase;
import me.dmillerw.droids.client.gui.widgets.impl.layout.RootLayout;

import java.util.List;
import java.util.Map;

public class Layout {

    public static Layout shrinkToFit(GuiBase gui, Widget child) {
        RootLayout layout = RootLayout.flexible(child);
        child.setParent(layout);
        return new Layout(gui, layout);
    }

    public static Layout fromGui(GuiBase gui, Widget child) {
        RootLayout layout = RootLayout.fromGui(gui, child);
        child.setParent(layout);
        return new Layout(gui, layout);
    }

    private GuiBase parentGui;

    private List<Widget> widgets = Lists.newArrayList();
    private Map<String, Widget> idToWidgetMap = Maps.newHashMap();

    private List<Drawable> drawables = Lists.newArrayList();
    private List<Clickable> clickables = Lists.newArrayList();
    private List<Typable> typables = Lists.newArrayList();

    private ContainerWidget rootWidget;

    private Point layoutPosition;
    private Dimensions layoutDimensions;

    public Layout(GuiBase parentGui, ContainerWidget rootWidget) {
        this.parentGui = parentGui;
        this.rootWidget = rootWidget;

        addWidget(this.rootWidget);
        calculateMinimumDimensions();
        calculateInitialPositions();
    }

    public Widget getById(String text) {
        return idToWidgetMap.get(text);
    }

    public void draw(int mouseX, int mouseY) {
        drawables.forEach((d) -> d.draw(mouseX, mouseY));
    }

    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        for (Clickable clickable : clickables) {
            if (clickable.onMouseClick(mouseX, mouseY, mouseButton))
                return;
        }
    }

    public void onKeyTyped(char key, int keycode) {
        for (Typable typable : typables) {
            if (typable.onKeyTyped(key, keycode))
                return;
        }
    }

    private void addWidget(Widget widget) {
        widgets.add(widget);
        idToWidgetMap.put(widget.getId(), widget);

        if (widget instanceof Drawable) drawables.add((Drawable) widget);
        if (widget instanceof Clickable) clickables.add((Clickable) widget);
        if (widget instanceof Typable) typables.add((Typable) widget);

        if (widget instanceof ContainerWidget) {
            ((ContainerWidget)widget).getSubwidgets().forEach((w) -> {
                w.setParent(widget);
                addWidget(w);
            });
        }
    }

    private void calculateMinimumDimensions() {
        this.layoutDimensions = rootWidget.getMinimumDimensions();
    }

    private void calculateInitialPositions() {
        rootWidget.layoutChildren();
        this.layoutPosition = rootWidget.currentPosition;
    }

    public Point getLayoutPosition() {
        return layoutPosition;
    }

    public void setLayoutPosition(Point layoutPosition) {
        this.layoutPosition = layoutPosition;
        this.rootWidget.setPosition(layoutPosition);
    }

    public Dimensions getLayoutDimensions() {
        return layoutDimensions;
    }
}
