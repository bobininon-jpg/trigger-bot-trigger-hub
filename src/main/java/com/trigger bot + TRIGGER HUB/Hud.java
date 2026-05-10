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
    private int x = 10; // Position X
    private int y = 50; // Position Y

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && mc.player != null) {
            LivingEntity target = TriggerBot.getCurrentTarget();
            
            if (target != null && target.isAlive()) {
                MatrixStack ms = event.getMatrixStack();
                
                // Background
                AbstractGui.fill(ms, x, y, x + 120, y + 40, 0x90000000);
                
                // Skin Face
                if (target instanceof PlayerEntity) {
                    ResourceLocation skin = ((AbstractClientPlayerEntity)target).getLocationSkin();
                    mc.getTextureManager().bindTexture(skin);
                    AbstractGui.blit(ms, x + 5, y + 5, 32, 32, 8, 8, 8, 8, 64, 64);
                }

                // Name and HP text
                mc.fontRenderer.drawString(ms, target.getName().getString(), x + 42, y + 5, 0xFFFFFF);
                mc.fontRenderer.drawString(ms, (int)target.getHealth() + " HP", x + 42, y + 28, 0xFFFFFF);

                // Health Bar logic
                float health = target.getHealth();
                float maxHealth = target.getMaxHealth();
                float healthWidth = (70 * (health / maxHealth));

                // Empty Bar (Dark Red)
                AbstractGui.fill(ms, x + 42, y + 18, x + 112, y + 24, 0xFF550000);
                // Full Bar (Bright Red)
                AbstractGui.fill(ms, x + 42, y + 18, x + 42 + (int)healthWidth, y + 24, 0xFFFF0000);
            }
        }
    }
}