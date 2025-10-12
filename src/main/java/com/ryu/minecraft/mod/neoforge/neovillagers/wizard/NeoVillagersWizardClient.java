package com.ryu.minecraft.mod.neoforge.neovillagers.wizard;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.client.gui.screens.UnenchantingScreen;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup.SetupMenus;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = NeoVillagersWizard.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = NeoVillagersWizard.MODID, value = Dist.CLIENT)
public class NeoVillagersWizardClient {
    
    @SubscribeEvent
    public static void registerMenuSreen(RegisterMenuScreensEvent event) {
        event.register(SetupMenus.UNENCHANTING.get(), UnenchantingScreen::new);
    }
    
    public NeoVillagersWizardClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    
}
