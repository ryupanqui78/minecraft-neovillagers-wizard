package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.NeoVillagersWizard;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.client.gui.screens.UnenchantingScreen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = NeoVillagersWizard.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SetupClientModEvents {
    
    @SubscribeEvent
    public static void registerMenuSreen(RegisterMenuScreensEvent event) {
        event.register(SetupMenus.UNENCHANTING_CONTAINER.get(), UnenchantingScreen::new);
    }
    
    private SetupClientModEvents() {
    }
}
