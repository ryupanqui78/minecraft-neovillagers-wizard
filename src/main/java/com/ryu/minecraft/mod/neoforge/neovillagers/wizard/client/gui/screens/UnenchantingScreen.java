package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.client.gui.screens;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.NeoVillagersWizard;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.inventories.UnenchantingMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UnenchantingScreen extends AbstractContainerScreen<UnenchantingMenu> {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation(NeoVillagersWizard.MODID,
            "textures/gui/container/unenchanting.png");
    
    public UnenchantingScreen(UnenchantingMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(UnenchantingScreen.TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth,
                this.imageHeight);
    }
    
}
