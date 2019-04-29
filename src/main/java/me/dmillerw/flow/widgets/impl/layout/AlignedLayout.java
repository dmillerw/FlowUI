package me.dmillerw.flow.widgets.impl.layout;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.dmillerw.flow.Dimensions;
import me.dmillerw.flow.Insets;
import me.dmillerw.flow.Point;
import me.dmillerw.flow.widgets.*;

import java.util.List;
import java.util.Map;

public class AlignedLayout extends ContainerWidget {

    public static AlignedWidgetBuilder builder() {
        return new AlignedWidgetBuilder();
    }

    public static class AlignedWidgetBuilder extends BaseBuilder<AlignedWidgetBuilder, AlignedLayout> {

        private List<Widget> children = Lists.newArrayList();
        private Map<String, Alignment> widgetToAlignmentMap = Maps.newHashMap();

        public AlignedWidgetBuilder child(Alignment alignment, Widget child) {
            this.children.add(child);
            this.widgetToAlignmentMap.put(child.getId(), alignment);
            return this;
        }

        @Override
        protected AlignedLayout construct() {
            return new AlignedLayout(id, children, widgetToAlignmentMap);
        }
    }

    public static enum Alignment {
        TOP_LEFT, TOP_MIDDLE, TOP_RIGHT,
        MIDDLE_LEFT, MIDDLE, MIDDLE_RIGHT,
        BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT
    }

    private Map<String, Alignment> widgetToAlignmentMap;

    public AlignedLayout(String id, List<Widget> children, Map<String, Alignment> map) {
        super(id);

        children.forEach(this::addSubwidget);
        this.widgetToAlignmentMap = map;
    }

    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        subwidgets.forEach((c) -> c.getMinimumDimensions(maxBounds));
        return maxBounds.copy();
    }

    @Override
    public void layoutChildren() {
        Insets insets = getInsets();
        Point ourPos = getPosition().offset(insets);

        Dimensions ourInnerBounds = getMinimumDimensions().shrink(insets);

        for (Widget child : subwidgets) {
            Dimensions childBounds = child.getMinimumDimensions();
            switch (widgetToAlignmentMap.get(child.getId())) {
                case TOP_LEFT: {
                    child.setPosition(ourPos.copy());

                    break;
                }

                case TOP_MIDDLE: {
                    Point pos = ourPos.offset(ourInnerBounds.getWidth() / 2, 0);
                    pos = pos.offset(-(childBounds.getWidth() / 2), 0);

                    child.setPosition(pos.copy());

                    break;
                }

                case TOP_RIGHT: {
                    Point pos = ourPos.offset(ourInnerBounds.getWidth(), 0);
                    pos = pos.offset(-childBounds.getWidth(), 0);

                    child.setPosition(pos.copy());

                    break;
                }

                case MIDDLE_LEFT: {
                    Point pos = ourPos.offset(0, ourInnerBounds.getHeight() / 2);
                    pos = pos.offset(0, -childBounds.getHeight() / 2);

                    child.setPosition(pos.copy());

                    break;
                }

                case BOTTOM_LEFT: {
                    Point pos = ourPos.offset(0, ourInnerBounds.getHeight());
                    pos = pos.offset(0, -childBounds.getHeight());

                    child.setPosition(pos);

                    break;
                }

                default: break;
            }
        }
    }
}
