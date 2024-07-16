package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.villagers;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.villagers.trades.EmeraldForItemTradeOffer;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.villagers.trades.ItemForEmeraldTradeOffer;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.Items;

public class Wizard extends Worker {
    
    public static final String ENTITY_NAME = "wizard";
    public static final String ENTITY_POI_NAME = "wizard_poi";
    
    @Override
    protected ItemListing[] getLevel1() {
        final ItemListing option1 = new EmeraldForItemTradeOffer(Items.NETHERRACK, 20, 16, 2);
        final ItemListing option2 = new EmeraldForItemTradeOffer(Items.BLACKSTONE, 20, 16, 4);
        final ItemListing option3 = new EmeraldForItemTradeOffer(Items.OBSIDIAN, 4, 8, 6);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, };
    }
    
    @Override
    protected ItemListing[] getLevel2() {
        final ItemListing option1 = new EmeraldForItemTradeOffer(Items.SOUL_SAND, 16, 8, 8);
        final ItemListing option2 = new EmeraldForItemTradeOffer(Items.MAGMA_BLOCK, 16, 8, 4);
        final ItemListing option3 = new EmeraldForItemTradeOffer(Items.GRAVEL, 16, 8, 4);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, };
    }
    
    @Override
    protected ItemListing[] getLevel3() {
        final ItemListing option1 = new ItemForEmeraldTradeOffer(Items.GLOWSTONE_DUST, 1, 8, 12, 6);
        final ItemListing option2 = new EmeraldForItemTradeOffer(Items.BASALT, 24, 32, 2);
        final ItemListing option3 = new ItemForEmeraldTradeOffer(Items.CRIMSON_STEM, 2, 5, 6, 6);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, };
    }
    
    @Override
    protected ItemListing[] getLevel4() {
        final ItemListing option1 = new EmeraldForItemTradeOffer(Items.SOUL_SOIL, 16, 8, 8);
        final ItemListing option2 = new ItemForEmeraldTradeOffer(Items.QUARTZ, 1, 3, 12, 6);
        final ItemListing option3 = new ItemForEmeraldTradeOffer(Items.WARPED_STEM, 2, 5, 6, 6);
        final ItemListing option4 = new EmeraldForItemTradeOffer(Items.WARPED_WART_BLOCK, 16, 8, 8);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, option4, };
    }
    
    @Override
    protected ItemListing[] getLevel5() {
        final ItemListing option1 = new ItemForEmeraldTradeOffer(Items.CRIMSON_ROOTS, 1, 3, 12, 6);
        final ItemListing option2 = new ItemForEmeraldTradeOffer(Items.WARPED_ROOTS, 1, 3, 12, 6);
        final ItemListing option3 = new ItemForEmeraldTradeOffer(Items.SHROOMLIGHT, 1, 4, 6, 6);
        final ItemListing option4 = new EmeraldForItemTradeOffer(Items.NETHER_WART_BLOCK, 16, 8, 8);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, option4, };
    }
    
}
