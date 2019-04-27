/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package me.dmillerw.droids.client.util;

import net.minecraft.client.gui.Gui;

@FunctionalInterface
public interface DrawFunction<T extends Gui> {

    void drawElement(T element, int mouseX, int mouseY);
}
