package me.dmillerw.flow.widgets.impl;

import me.dmillerw.flow.WidgetSize;
import me.dmillerw.flow.widgets.ContainerWidget;
import me.dmillerw.flow.Dimensions;
import me.dmillerw.flow.Insets;
import me.dmillerw.flow.widgets.impl.layout.LinearLayout;

public class NumberPicker extends ContainerWidget {

    private Button decButton;
    private Button incButton;
    private TextField textField;

    private LinearLayout row;

    public NumberPicker(String id) {
        super(id);

        decButton = Button.builder().text("-").buildWidget();
        incButton = Button.builder().text("+").buildWidget();
        textField = TextField.builder().margins(Insets.symmetrical(0, 4)).width(WidgetSize.SizingType.FIT_TO_CONTENT).drawBackground().buildWidget();

        row = LinearLayout.buider().orientation(LinearLayout.Orientation.HORIZONTAL).child(decButton).child(textField).child(incButton).buildWidget();

        addSubwidget(row);
    }

    @Override
    protected boolean shouldForceMinimumDimensions() {
        return true;
    }

    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        Dimensions bounds = Dimensions.withWidgetInsets(this).add(row.getMinimumDimensions(maxBounds));
        return bounds;
    }

    @Override
    public void layoutChildren() {
        row.setPosition(getPositionWithOffsets().copy());
    }
}
