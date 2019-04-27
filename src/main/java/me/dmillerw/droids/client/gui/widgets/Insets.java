package me.dmillerw.droids.client.gui.widgets;

public class Insets {

    public static final Insets ZERO = all(0);

    public static Insets all(int inset) {
        return new Insets(inset, inset, inset, inset);
    }

    public static Insets symmetrical(int vertical, int horizontal) {
        return new Insets(vertical, vertical, horizontal, horizontal);
    }

    public static Insets of(int top, int bottom, int left, int right) {
        return new Insets(top, bottom, left, right);
    }

    public final int top;
    public final int bottom;
    public final int left;
    public final int right;

    public static Insets addTogether(Insets margin, Insets padding) {
        return new Insets(margin.top + padding.top, margin.bottom + padding.bottom, margin.left + padding.left, margin.right + padding.right);
    }

    public Insets(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public int getHorizontal() {
        return left + right;
    }

    public int getVertical() {
        return top + bottom;
    }

    @Override
    public String toString() {
        return "Insets{" +
                "top=" + top +
                ", bottom=" + bottom +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
