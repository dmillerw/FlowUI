package me.dmillerw.flow;

public class WidgetSize {

    public static enum SizingType {

        FIT_TO_CONTENT,
        SIZED,
        FILL_PARENT
    }

    private SizingType sizingType;
    private int axisSize;

    public WidgetSize(SizingType sizingType) {
        this.sizingType = sizingType;
        this.axisSize = 0;
    }

    public WidgetSize(int axisSize) {
        this.sizingType = SizingType.SIZED;
        this.axisSize = axisSize;
    }

    public SizingType getSizingType() {
        return sizingType;
    }

    public int getAxisSize() {
        return axisSize;
    }
}
