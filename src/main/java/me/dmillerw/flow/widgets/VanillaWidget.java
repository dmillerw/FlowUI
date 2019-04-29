package me.dmillerw.flow.widgets;

import me.dmillerw.flow.Dimensions;
import net.minecraft.client.gui.Gui;

public abstract class VanillaWidget<G extends Gui> extends Widget {

    protected G backingElement;

    protected VanillaWidget() {
        super();
    }

    protected VanillaWidget(String id) {
        super(id);
    }

    @Override
    public final void onPositionChanged() {
        this.backingElement = buildElement();
    }

    public abstract Dimensions calculateMinimumDimensions(Dimensions maxBounds);

    public abstract G buildElement();
}
