package me.dmillerw.flow;

import net.minecraftforge.fml.common.Mod;

/**
 * We use a dummy mod because FlowUI provides resources, and this is the easiest way to ensure they're loaded
 * and available. If anyone has a better way, let me know
 */
@Mod(modid = "flowui", name = "Flow UI", version = "1.0")
public class DummyMod {}
