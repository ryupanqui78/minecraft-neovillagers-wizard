package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup;

import com.google.common.collect.ImmutableSet;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.NeoVillagersWizard;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.villagers.Wizard;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupVillagers {
    
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister
            .create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, NeoVillagersWizard.MODID);
    
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister
            .create(BuiltInRegistries.VILLAGER_PROFESSION, NeoVillagersWizard.MODID);
    
    public static final DeferredHolder<PoiType, PoiType> WIZARD_POI = SetupVillagers.POI_TYPES.register(
            Wizard.ENTITY_POI_NAME,
            () -> new PoiType(
                    ImmutableSet.copyOf(SetupBlocks.UNENCHANTING.get().getStateDefinition().getPossibleStates()), 1,
                    1));
    
    public static final DeferredHolder<VillagerProfession, VillagerProfession> WIZARD = SetupVillagers.VILLAGER_PROFESSIONS
            .register(Wizard.ENTITY_NAME,
                    () -> new VillagerProfession(Wizard.ENTITY_NAME, x -> x.is(SetupVillagers.WIZARD_POI.getKey()),
                            x -> x.is(SetupVillagers.WIZARD_POI.getKey()), ImmutableSet.of(), ImmutableSet.of(),
                            SoundEvents.VILLAGER_WORK_LIBRARIAN));
    
    public static void register(IEventBus eventBus) {
        SetupVillagers.POI_TYPES.register(eventBus);
        SetupVillagers.VILLAGER_PROFESSIONS.register(eventBus);
    }
    
    private SetupVillagers() {
        
    }
}
