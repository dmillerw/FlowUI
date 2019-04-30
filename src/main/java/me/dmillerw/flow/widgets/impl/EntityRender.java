package me.dmillerw.flow.widgets.impl;

import me.dmillerw.flow.Dimensions;
import me.dmillerw.flow.Drawable;
import me.dmillerw.flow.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;

public class EntityRender extends Widget implements Drawable {

    public static EntityRenderBuilder builder() {
        return new EntityRenderBuilder();
    }

    public static class EntityRenderBuilder extends BaseBuilder<EntityRenderBuilder, EntityRender> {

        private EntityLivingBase entity;

        public EntityRenderBuilder entity(EntityLivingBase entity) {
            this.entity = entity;
            return this;
        }

        @Override
        protected EntityRender construct() {
            return new EntityRender(id, entity);
        }

    }
    private EntityLivingBase entity;

    private EntityRender(String id, EntityLivingBase entity) {
        super(id);

        this.entity = entity;
    }

    @Override
    public Dimensions calculateMinimumDimensions(Dimensions maxBounds) {
        return Dimensions.withWidgetInsets(this).add(51, 72);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        int px = getPosition().getX() + 25;
        int py = getPosition().getY() + 67;
        int mx = mouseX - px;
        int my = mouseY - py;

        GuiInventory.drawEntityOnScreen(px, py, 30, -mx, -(my + 30), entity);
    }
}
