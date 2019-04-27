package me.dmillerw.droids.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Stack;
import java.util.function.Consumer;

@SideOnly(Side.CLIENT)
public class Navigation {

    private static class GuiInstance<T> {

        private final GuiBase gui;
        private final Consumer<T> futureCallback;

        private GuiInstance(GuiBase gui) {
            this.gui = gui;
            this.futureCallback = null;
        }

        private GuiInstance(GuiBase gui, Consumer<T> futureCallback) {
            this.gui = gui;
            this.futureCallback = futureCallback;
        }
    }

    public static final Navigation INSTANCE = new Navigation();

    private final Stack<GuiInstance<?>> guiStack = new Stack<>();

    public void clear() {
        guiStack.clear();

        Minecraft.getMinecraft().displayGuiScreen(null);
    }

    public void push(GuiBase gui) {
        if (!guiStack.isEmpty()) {
            GuiInstance current = guiStack.peek();
            current.gui.onGuiPaused();
        }

        guiStack.push(new GuiInstance<Void>(gui));

        Minecraft.getMinecraft().displayGuiScreen(gui);
    }

    public <T> void push(GuiBase gui, Consumer<T> futureCallback) {
        if (!guiStack.isEmpty()) {
            GuiInstance current = guiStack.peek();
            current.gui.onGuiPaused();
        }

        guiStack.push(new GuiInstance<>(gui, futureCallback));

        Minecraft.getMinecraft().displayGuiScreen(gui);
    }

    public void pushReplacement(GuiBase gui) {
        guiStack.clear();
        guiStack.push(new GuiInstance<>(gui));

        Minecraft.getMinecraft().displayGuiScreen(gui);
    }

    public void pop() {
        guiStack.pop();
        if (!guiStack.isEmpty()) {
            Minecraft.getMinecraft().displayGuiScreen(guiStack.peek().gui);
        } else {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    public <T> void pop(T result) {
        GuiInstance<T> instance = (GuiInstance<T>) guiStack.pop();
        if (instance.futureCallback != null) {
            instance.futureCallback.accept(result);
        }

        if (!guiStack.isEmpty()) {
            Minecraft.getMinecraft().displayGuiScreen(guiStack.peek().gui);
        } else {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }
}
