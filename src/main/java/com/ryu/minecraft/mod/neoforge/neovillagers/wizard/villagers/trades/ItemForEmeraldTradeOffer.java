package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.villagers.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

public class ItemForEmeraldTradeOffer implements VillagerTrades.ItemListing {
    
    private final Item item;
    private final int emeraldCost;
    private final int numberOfItems;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;
    
    public ItemForEmeraldTradeOffer(ItemLike itemSell, int emeraldCost, int count, int maxUses, int xpVillager) {
        this.item = itemSell.asItem();
        this.emeraldCost = emeraldCost;
        this.numberOfItems = count;
        this.maxUses = maxUses;
        this.villagerXp = xpVillager;
        this.priceMultiplier = 0.05f;
    }
    
    @Override
    public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
        final ItemStack itemstack = new ItemStack(this.item, this.numberOfItems);
        return new MerchantOffer(new ItemCost(Items.EMERALD, this.emeraldCost), itemstack, this.maxUses,
                this.villagerXp, this.priceMultiplier);
    }
    
}
