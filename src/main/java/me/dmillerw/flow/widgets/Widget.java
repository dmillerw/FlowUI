package me.dmillerw.flow.widgets;

import me.dmillerw.flow.Dimensions;
import me.dmillerw.flow.Insets;
import me.dmillerw.flow.Point;
import me.dmillerw.flow.WidgetSize;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public abstract class Widget {

    public static final ResourceLocation WIDGET_TEXTURE = new ResourceLocation("flowui", "textures/gui/widgets.png");

    public static abstract class BaseBuilder<B extends BaseBuilder, W extends Widget> {

        protected String id = UUID.randomUUID().toString();
        protected Insets margin = Insets.ZERO;
        protected Insets padding = Insets.ZERO;
        protected WidgetSize width = new WidgetSize(WidgetSize.SizingType.FIT_TO_CONTENT);
        protected WidgetSize height = new WidgetSize(WidgetSize.SizingType.FIT_TO_CONTENT);

        protected BaseBuilder() {
        }

        @SuppressWarnings("unchecked")
        private B _this() {
            return (B) this;
        }

        public B id(String id) {
            this.id = id;
            return _this();
        }

        public B margins(Insets margin) {
            this.margin = margin;
            return _this();
        }

        public B padding(Insets padding) {
            this.padding = padding;
            return _this();
        }

        public B width(int width) {
            this.width = new WidgetSize(width);
            return _this();
        }

        public B width(WidgetSize.SizingType sizingType) {
            this.width = new WidgetSize(sizingType);
            return _this();
        }

        public B height(int height) {
            this.height = new WidgetSize(height);
            return _this();
        }

        public B height(WidgetSize.SizingType sizingType) {
            this.height = new WidgetSize(sizingType);
            return _this();
        }

        public final W buildWidget() {
            W widget = construct();
            widget.setMargin(margin);
            widget.setPadding(padding);
            widget.setRequestedDimensions(width, height);
            return widget;
        }

        protected abstract W construct();
    }

    private String id;

    // Space OUTSIDE the border of the widget
    private Insets margin = Insets.ZERO;
    // Space INSIDE the border of the widget
    private Insets padding = Insets.ZERO;

    private WidgetSize requestedWidth;
    private WidgetSize requestedHeight;

    private Widget parentWidget;

    protected Point currentPosition = Point.ZERO;
    private Dimensions minimumDimensions;

    public Widget() {
    }

    public Widget(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Widget getParent() {
        return parentWidget;
    }

    public void setParent(Widget parentWidget) {
        this.parentWidget = parentWidget;
    }

    public Insets getMargin() {
        return margin;
    }

    public Insets getPadding() {
        return padding;
    }

    public void setMargin(Insets margin) {
        this.margin = margin;
    }

    public void setPadding(Insets padding) {
        this.padding = padding;
    }

    public void setRequestedDimensions(WidgetSize width, WidgetSize height) {
        this.requestedWidth = width;
        this.requestedHeight = height;
    }

    protected boolean shouldForceMinimumDimensions() {
        return false;
    }

    /* DIMENSIONS */

    public final Dimensions getMinimumDimensions() {
        return getMinimumDimensions(null);
    }

    public final Dimensions getMinimumDimensions(Dimensions maxBounds) {
        if (minimumDimensions == null) {
            if (maxBounds == null) {
                Minecraft mc = Minecraft.getMinecraft();
                ScaledResolution res = new ScaledResolution(mc);
                maxBounds = new Dimensions(res.getScaledWidth(), res.getScaledHeight());
            }

            Dimensions minBounds = calculateMinimumDimensions(maxBounds).clamp(maxBounds);

            int xBounds = 0;
            int yBounds = 0;

            if (shouldForceMinimumDimensions()) {
                xBounds = minBounds.getWidth();
                yBounds = minBounds.getHeight();
            } else {
                switch (requestedWidth.getSizingType()) {
                    case FIT_TO_CONTENT:
                        xBounds = minBounds.getWidth();
                        break;
                    case SIZED:
                        int insets = margin.left + margin.right + padding.left + padding.right;
                        xBounds = requestedWidth.getAxisSize() + insets;
                        break;
                    case FILL_PARENT:
                        xBounds = maxBounds.getWidth();
                        break;
                }

                switch (requestedHeight.getSizingType()) {
                    case FIT_TO_CONTENT:
                        yBounds = minBounds.getHeight();
                        break;
                    case SIZED: {
                        int insets = margin.top + margin.bottom + padding.top + padding.bottom;
                        yBounds = requestedHeight.getAxisSize() + insets;
                        break;
                    }
                    case FILL_PARENT:
                        yBounds = maxBounds.getHeight();
                        break;
                }
            }

            minimumDimensions = new Dimensions(xBounds, yBounds).clamp(maxBounds);
        }
        return minimumDimensions;
    }

    public void setMinimumDimensions(Dimensions bounds) {
        this.minimumDimensions = bounds;
    }

    public Point getPosition() {
        return currentPosition.copy();
    }

    public final Point getPositionWithOffsets() {
        return currentPosition.offset(margin).offset(padding).copy();
    }

    public final void setPosition(Point position) {
        this.currentPosition = position.copy();
        if (this instanceof ContainerWidget)
            ((ContainerWidget) this).layoutChildren();
        onPositionChanged();
    }

    /**
     * Called on every widget in the tree, starting from the top and working downwards
     * If an widget has subwidgets, it MUST call the calculation method on the child widgets and incorporate the
     * returned dimensions into their own returned value
     * The returned value should include the widget's margin and padding sizes as well
     *
     * @param maxBounds The maximum bounds this widget is allowed to render in
     * @return The minimum amount of space required for this widget
     */
    public abstract Dimensions calculateMinimumDimensions(Dimensions maxBounds);

    public void onPositionChanged() {
    }

    /* UTIL */

    public boolean mouseInside(int mouseX, int mouseY) {
        return mouseX >= getPosition().getX() && mouseX <= getPosition().getX() + getMinimumDimensions().getWidth() &&
                mouseY >= getPosition().getY() && mouseY <= getPosition().getY() + getMinimumDimensions().getHeight();
    }
}
