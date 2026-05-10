package com.xobon.mod;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Hud {
    private final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            LivingEntity target = TriggerBot.getCurrentTarget();
            if (target != null && target.isAlive()) {
                MatrixStack ms = event.getMatrixStack();
                int x = 10, y = 50;
                
                // Background
                AbstractGui.fill(ms, x, y, x + 120, y + 40, 0x90000000);
                
                // Name
                mc.fontRenderer.drawString(ms, target.getName().getString(), x + 42, y + 5, 0xFFFFFF);
                
                // Health Bar
                float health = target.getHealth();
                float maxHealth = target.getMaxHealth();
                int barWidth = (int)(70 * (health / maxHealth));
                AbstractGui.fill(ms, x + 42, y + 18, x + 112, y + 24, 0xFF550000);
                AbstractGui.fill(ms, x + 42, y + 18, x + 42 + barWidth, y + 24, 0xFFFF0000);
                
                // HP Text
                mc.fontRenderer.drawString(ms, (int)health + " HP", x + 42, y + 28, 0xFFFFFF);
            }
        }
    }
}