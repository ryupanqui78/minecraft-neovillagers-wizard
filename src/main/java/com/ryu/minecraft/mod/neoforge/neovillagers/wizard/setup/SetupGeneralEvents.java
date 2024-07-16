package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.NeoVillagersWizard;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.villagers.Wizard;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.villagers.Worker;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@Mod.EventBusSubscriber(modid = NeoVillagersWizard.MODID)
public class SetupGeneralEvents {
    
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == SetupVillagers.WIZARD.get()) {
            final Worker worker = new Wizard();
            worker.getTrades(event);
        }
    }
    
    private SetupGeneralEvents() {
    }
}
