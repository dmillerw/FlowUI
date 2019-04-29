package me.dmillerw.flow.widgets;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import me.dmillerw.flow.Dimensions;
import me.dmillerw.flow.Insets;

import java.util.List;

public abstract class ContainerWidget extends Widget {

    protected List<Widget> subwidgets = Lists.newArrayList();

    public ContainerWidget() {
        super();
    }

    public ContainerWidget(String id) {
        super(id);
    }

    public final void addSubwidget(Widget child) {
        this.subwidgets.add(child);
    }

    public final ImmutableList<Widget> getSubwidgets() {
        return ImmutableList.copyOf(subwidgets);
    }

    public abstract Dimensions calculateMinimumDimensions(Dimensions maxBounds);

    public abstract void layoutChildren();

    /* INSETS */

    protected Insets getInsets() {
        return Insets.addTogether(getMargin(), getPadding());
    }
}
