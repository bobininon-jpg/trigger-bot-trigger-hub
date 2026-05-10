package com.xobon.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class TriggerBot {
    private final Minecraft mc = Minecraft.getInstance();
    private static boolean enabled = false;
    private static LivingEntity currentTarget = null;

    public static boolean isEnabled() { return enabled; }
    public static LivingEntity getCurrentTarget() { return currentTarget; }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.getKey() == GLFW.GLFW_KEY_R && event.getAction() == GLFW.GLFW_PRESS) {
            enabled = !enabled;
            if (mc.player != null) {
                String status = enabled ? "§aENABLED" : "§cDISABLED";
                mc.player.sendStatusMessage(new StringTextComponent("§7[§bXOBON§7] §fTriggerBot: " + status), true);
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && mc.player != null) {
            if (mc.objectMouseOver != null && mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY) {
                EntityRayTraceResult rayTrace = (EntityRayTraceResult) mc.objectMouseOver;
                if (rayTrace.getEntity() instanceof LivingEntity) {
                    currentTarget = (LivingEntity) rayTrace.getEntity();
                    
                    if (enabled && mc.player.getCooledAttackStrength(0) >= 1.0f) {
                        mc.playerController.attackEntity(mc.player, currentTarget);
                        mc.player.swingArm(Hand.MAIN_HAND);
                    }
                    return;
                }
            }
            currentTarget = null;
        }
    }
}