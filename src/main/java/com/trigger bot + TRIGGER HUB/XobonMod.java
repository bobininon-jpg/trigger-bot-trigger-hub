package com.xobon.mod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("xobonmod")
public class XobonMod {
    public XobonMod() {
        // Registering our modules
        MinecraftForge.EVENT_BUS.register(new TriggerBot());
        MinecraftForge.EVENT_BUS.register(new Hud());
    }
}