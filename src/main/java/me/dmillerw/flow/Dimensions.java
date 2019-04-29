package me.dmillerw.flow;

import me.dmillerw.flow.widgets.Widget;

public class Dimensions {

    public static final Dimensions ZERO = new Dimensions(0, 0);
    public static final Dimensions INFINITE = new Dimensions(Integer.MAX_VALUE, Integer.MAX_VALUE);

    public static Dimensions withWidgetInsets(Widget widget) {
        return new Dimensions(
                widget.getMargin().getHorizontal() + widget.getPadding().getHorizontal(),
                widget.getMargin().getVertical() + widget.getPadding().getVertical());
    }

    private int width;
    private int height;

    public Dimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Dimensions add(Dimensions dimensions) {
        return new Dimensions(width + dimensions.getWidth(), height + dimensions.getHeight());
    }

    public Dimensions add(Insets insets) {
        return add((insets.left + insets.right), (insets.top + insets.bottom));
    }

    public Dimensions add(int width, int height) {
        int w = this.width + width;
        int h = this.height + height;
        return new Dimensions(w, h);
    }

    public Dimensions shrink(Insets insets) {
        int w = width - (insets.left + insets.right);
        int h = height - (insets.top + insets.bottom);
        return new Dimensions(w, h);
    }

    public Dimensions clamp(Dimensions other) {
        int w = Math.min(width, other.width);
        int h = Math.min(height, other.height);
        return new Dimensions(w, h);
    }

    public Dimensions copy() {
        return new Dimensions(width, height);
    }

    @Override
    public String toString() {
        return "Dimensions{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
