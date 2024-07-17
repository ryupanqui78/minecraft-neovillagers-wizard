package com.ryu.minecraft.mod.neoforge.neovillagers.wizard;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup.SetupMenus;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup.SetupVillagers;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(NeoVillagersWizard.MODID)
public class NeoVillagersWizard {
    
    public static final String MODID = "neovillagerswizard";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public NeoVillagersWizard(IEventBus modEventBus) {
        SetupBlocks.BLOCKS.register(modEventBus);
        SetupBlocks.ITEMS.register(modEventBus);
        SetupMenus.MENUS.register(modEventBus);
        
        SetupVillagers.register(modEventBus);
        
        modEventBus.addListener(this::addCreative);
    }
    
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(SetupBlocks.UNENCHANTING);
        }
    }
}
