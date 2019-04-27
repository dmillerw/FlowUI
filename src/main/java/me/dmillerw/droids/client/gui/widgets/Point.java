package me.dmillerw.droids.client.gui.widgets;

public class Point {

    public static final Point ZERO = new Point(0, 0);

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point offset(Insets insets) {
        return this.offset(insets.left, insets.top);
    }

    public Point offset(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }

    public Point copy() {
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
