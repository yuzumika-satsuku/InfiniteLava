package com.example.infinitelava;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = InfiniteLavaMod.MODID,
     name = InfiniteLavaMod.NAME,
     version = InfiniteLavaMod.VERSION)
public class InfiniteLavaMod {

    public static final String MODID = "infinitelava";
    public static final String NAME  = "Infinite Lava";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new InfiniteLavaHandler());
    }
}
