package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.NeoVillagersWizard;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.inventories.UnenchantingMenu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupMenus {
    
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU,
            NeoVillagersWizard.MODID);
    
    public static final DeferredHolder<MenuType<?>, MenuType<UnenchantingMenu>> UNENCHANTING_CONTAINER = SetupMenus.MENUS
            .register(UnenchantingMenu.MENU_NAME,
                    () -> new MenuType<UnenchantingMenu>(UnenchantingMenu::new, FeatureFlags.DEFAULT_FLAGS));
    
    private SetupMenus() {
    }
}
