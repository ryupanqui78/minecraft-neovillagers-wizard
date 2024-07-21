package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.client.gui.screens;

import java.util.ArrayList;
import java.util.List;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.NeoVillagersWizard;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.inventories.UnenchantingMenu;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UnenchantingScreen extends AbstractContainerScreen<UnenchantingMenu> {
    
    private static final int SIZE_BUTTON_RESULT = 17;
    private static final int SIZE_CONTENT = 18;
    private static final int SIZE_EXPERIENCE_IMAGE = 16;
    
    private static final int BUTTON_EXPERIENCE_POS_X = 115;
    private static final int BUTTON_EXPERIENCE_POS_Y = 7;
    
    private static final ResourceLocation TEXTURE = new ResourceLocation(NeoVillagersWizard.MODID,
            "textures/gui/container/unenchanting.png");
    
    public UnenchantingScreen(UnenchantingMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    
    private List<Component> generateToolTip(int pLvlExp, int pCost, int pDamage) {
        final List<Component> list = new ArrayList<>();
        if (this.minecraft.player.experienceLevel < pCost) {
            list.add((Component.translatable("container.enchant.level.requirement", pCost))
                    .withStyle(ChatFormatting.RED));
        } else {
            final int numLapis = this.menu.getCurrentNumLapis();
            MutableComponent lapisRequiredMessage;
            MutableComponent levelRequiredMessage;
            
            if (pLvlExp == 1) {
                lapisRequiredMessage = Component.translatable("container.unenchanting.block.lapis.one");
                levelRequiredMessage = Component.translatable("container.enchant.level.one");
            } else {
                lapisRequiredMessage = Component.translatable("container.unenchanting.block.lapis.many", pLvlExp);
                levelRequiredMessage = Component.translatable("container.enchant.level.many", pLvlExp);
            }
            list.add(lapisRequiredMessage.withStyle(numLapis >= pLvlExp ? ChatFormatting.GRAY : ChatFormatting.RED));
            list.add(levelRequiredMessage.withStyle(ChatFormatting.GRAY));
            
            if (pDamage > 0) {
                MutableComponent damageMessage;
                damageMessage = Component.translatable("container.unenchanting.damage", pDamage);
                list.add(damageMessage.withStyle(ChatFormatting.GRAY));
            }
        }
        return list;
    }
    
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        
        for (int btnExp = 0; btnExp < 4; ++btnExp) {
            final int lvlExp = btnExp + 1;
            final boolean isHover = this.isHovering(UnenchantingScreen.BUTTON_EXPERIENCE_POS_X,
                    UnenchantingScreen.BUTTON_EXPERIENCE_POS_Y + (UnenchantingScreen.SIZE_CONTENT * btnExp),
                    UnenchantingScreen.SIZE_BUTTON_RESULT, UnenchantingScreen.SIZE_BUTTON_RESULT, pMouseX, pMouseY);
            if (isHover && (this.menu.getEnchantMinLevel()[btnExp] > 0)) {
                final int cost = this.menu.getEnchantMinLevel()[btnExp];
                final int damage = this.menu.getEnchantDamage()[btnExp];
                final List<Component> list = this.generateToolTip(lvlExp, cost, damage);
                pGuiGraphics.renderComponentTooltip(this.font, list, pMouseX, pMouseY);
                break;
            }
        }
    }
    
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(UnenchantingScreen.TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth,
                this.imageHeight);
        
        for (int resultIndex = 0; resultIndex < 4; ++resultIndex) {
            final int btnExpX = this.leftPos + UnenchantingScreen.BUTTON_EXPERIENCE_POS_X;
            final int btnExpY = this.topPos + UnenchantingScreen.BUTTON_EXPERIENCE_POS_Y
                    + (UnenchantingScreen.SIZE_CONTENT * resultIndex);
            
            if (this.menu.getEnchantMinLevel()[resultIndex] == 0) {
                pGuiGraphics.blit(UnenchantingScreen.TEXTURE, btnExpX, btnExpY, 0, 184,
                        UnenchantingScreen.SIZE_BUTTON_RESULT, UnenchantingScreen.SIZE_CONTENT);
            } else {
                final int expImageX = UnenchantingScreen.SIZE_EXPERIENCE_IMAGE * resultIndex;
                final int resultButtonY = this.topPos + 8 + (UnenchantingScreen.SIZE_CONTENT * resultIndex);
                if ((this.minecraft.player.experienceLevel < this.menu.getEnchantMinLevel()[resultIndex])
                        || (this.menu.getCurrentNumLapis() < (resultIndex + 1))) {
                    pGuiGraphics.blit(UnenchantingScreen.TEXTURE, btnExpX, btnExpY, 0, 184,
                            UnenchantingScreen.SIZE_CONTENT, UnenchantingScreen.SIZE_CONTENT);
                    pGuiGraphics.blit(UnenchantingScreen.TEXTURE, btnExpX, resultButtonY, expImageX, 236,
                            UnenchantingScreen.SIZE_EXPERIENCE_IMAGE, UnenchantingScreen.SIZE_EXPERIENCE_IMAGE);
                } else {
                    final int k2 = pMouseX - btnExpX;
                    final int l2 = pMouseY - btnExpY;
                    final int posY = this.topPos + UnenchantingScreen.BUTTON_EXPERIENCE_POS_Y
                            + (UnenchantingScreen.SIZE_CONTENT * resultIndex);
                    if ((k2 >= 0) && (l2 >= 0) && (k2 < 108) && (l2 < 19)) {
                        pGuiGraphics.blit(UnenchantingScreen.TEXTURE, btnExpX, posY, 0, 202,
                                UnenchantingScreen.SIZE_CONTENT, UnenchantingScreen.SIZE_CONTENT);
                    } else {
                        pGuiGraphics.blit(UnenchantingScreen.TEXTURE, btnExpX, posY, 0, 166,
                                UnenchantingScreen.SIZE_CONTENT, UnenchantingScreen.SIZE_CONTENT);
                    }
                    pGuiGraphics.blit(UnenchantingScreen.TEXTURE, btnExpX + 1, resultButtonY, expImageX, 220,
                            UnenchantingScreen.SIZE_EXPERIENCE_IMAGE, UnenchantingScreen.SIZE_EXPERIENCE_IMAGE);
                }
            }
        }
    }
    
}
