package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.villagers;

import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

public abstract class Worker {
    
    protected abstract ItemListing[] getLevel1();
    
    protected abstract ItemListing[] getLevel2();
    
    protected abstract ItemListing[] getLevel3();
    
    protected abstract ItemListing[] getLevel4();
    
    protected abstract ItemListing[] getLevel5();
    
    public void getTrades(VillagerTradesEvent event) {
        for (final ItemListing trade : this.getLevel1()) {
            event.getTrades().get(1).add(trade);
        }
        for (final ItemListing trade : this.getLevel2()) {
            event.getTrades().get(2).add(trade);
        }
        for (final ItemListing trade : this.getLevel3()) {
            event.getTrades().get(3).add(trade);
        }
        for (final ItemListing trade : this.getLevel4()) {
            event.getTrades().get(4).add(trade);
        }
        for (final ItemListing trade : this.getLevel5()) {
            event.getTrades().get(5).add(trade);
        }
    }
}
