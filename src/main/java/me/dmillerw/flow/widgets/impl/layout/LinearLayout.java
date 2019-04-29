package me.dmillerw.flow.widgets.impl.layout;

import com.google.common.collect.Lists;
import me.dmillerw.flow.widgets.ContainerWidget;
import me.dmillerw.flow.Dimensions;
import me.dmillerw.flow.Point;
import me.dmillerw.flow.widgets.Widget;

import java.util.List;

public class LinearLayout extends ContainerWidget {

    public static LinearLayoutBuilder buider() {
        return new LinearLayoutBuilder();
    }

    public static class LinearLayoutBuilder extends BaseBuilder<LinearLayoutBuilder, LinearLayout> {

        private Orientation orientation = Orientation.HORIZONTAL;
        private SizingRule sizingRule = SizingRule.EVENLY;
        private boolean expanded = false;
        private List<Widget> children = Lists.newArrayList();

        public LinearLayoutBuilder orientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public LinearLayoutBuilder sizingRule(SizingRule sizingRule) {
            this.sizingRule = sizingRule;
            return this;
        }

        public LinearLayoutBuilder expanded() {
            this.expanded = true;
            return this;
        }

        public LinearLayoutBuilder child(Widget child) {
            this.children.add(child);
            return this;
        }

        @Override
        protected LinearLayout construct() {
            return new LinearLayout(id, orientation, sizingRule, expanded, children);
        }
    }

    public static enum Orientation {
        HORIZONTAL, VERTICAL
    }

    // Evenly will try and give every child equal space in the layout, FSFC (First come first serve) allows widgets
    // to take up as much space as they'd like, and distributes the rest to whatever remains
    public static enum SizingRule {
        EVENLY, FCFS
    }

    private Orientation orientation;
    private SizingRule sizingRule;
    private boolean expanded = false;

    public LinearLayout(String id, Orientation orientation, SizingRule sizingRule, boolean expanded, List<Widget> children) {
        super(id);

        this.orientation = orientation;
        this.sizingRule = sizingRule;
        this.expanded = expanded;
        children.forEach(this::addSubwidget);
    }

    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        // Linear layouts by default will shrink to take up the least amount of vertical and horizontal space
        // If the expanded flag is set, the layout will only expand in the orientation direction, the other direction
        // will continue to shrink to fit contents
        return orientation == Orientation.HORIZONTAL
                ? calculateHorizontal(maxBounds)
                : calculateVertical(maxBounds);
    }

    private Dimensions calculateHorizontal(Dimensions maxBounds) {
        Dimensions ourBounds = Dimensions.withWidgetInsets(this);

        int availableSpace = maxBounds.getWidth() - ourBounds.getWidth();
        int initialSpace = availableSpace / subwidgets.size();
        int maxHeight = maxBounds.getHeight() - ourBounds.getHeight();
        int maxChildHeight = 0;

        for (int i = 0; i< subwidgets.size(); i++) {
            int space;
            if (sizingRule == SizingRule.EVENLY) {
                space = initialSpace;
            } else {
                space = availableSpace;
            }
            Dimensions max = new Dimensions(space, maxHeight);
            Widget child = subwidgets.get(i);
            Dimensions bounds = child.getMinimumDimensions(max);
            child.setMinimumDimensions(bounds);
            availableSpace -= bounds.getWidth();
            if (bounds.getHeight() > maxChildHeight)
                maxChildHeight = bounds.getHeight();

            ourBounds = ourBounds.add(bounds.getWidth(), 0);
        }

        ourBounds = ourBounds.add(0, maxChildHeight);

        return ourBounds;
    }

    private Dimensions calculateVertical(Dimensions maxBounds) {
        Dimensions ourBounds = Dimensions.withWidgetInsets(this);

        int availableSpace = maxBounds.getHeight() - ourBounds.getHeight();
        int initialSpace = availableSpace / subwidgets.size();
        int maxWidth = maxBounds.getWidth() - ourBounds.getWidth();
        int maxChildWidth = 0;

        for (int i = 0; i< subwidgets.size(); i++) {
            int space;
            if (sizingRule == SizingRule.EVENLY) {
                space = initialSpace;
            } else {
                space = availableSpace;
            }
            Dimensions max = new Dimensions(maxWidth, space);
            Widget child = subwidgets.get(i);
            Dimensions bounds = child.getMinimumDimensions(max);
            child.setMinimumDimensions(bounds);
            availableSpace -= bounds.getHeight();
            if (bounds.getWidth() > maxChildWidth)
                maxChildWidth = bounds.getWidth();

            ourBounds = ourBounds.add(0, bounds.getHeight());
        }

        ourBounds = ourBounds.add(maxChildWidth, 0);

        return ourBounds;
    }

    public void layoutChildren() {
        Point pos = getPosition().copy();
        pos = pos.offset(getMargin());
        pos = pos.offset(getPadding());

        int offset = 0;
        for (Widget child : subwidgets) {
            if (orientation == Orientation.HORIZONTAL) {
                child.setPosition(pos.offset(offset, 0));
                offset += child.getMinimumDimensions(null).getWidth();
            } else {
                child.setPosition(pos.offset(0, offset));
                offset += child.getMinimumDimensions(null).getHeight();
            }

            if (child instanceof ContainerWidget)
                ((ContainerWidget) child).layoutChildren();
        }
    }
}
