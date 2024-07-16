package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.villagers.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

public class EmeraldForItemTradeOffer implements VillagerTrades.ItemListing {
    
    private final Item item;
    private final int cost;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;
    
    public EmeraldForItemTradeOffer(ItemLike itemBuy, int cost, int maxUses, int xpVillager) {
        this.item = itemBuy.asItem();
        this.cost = cost;
        this.maxUses = maxUses;
        this.villagerXp = xpVillager;
        this.priceMultiplier = 0.05F;
    }
    
    @Override
    public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
        final ItemStack itemstack = new ItemStack(this.item, this.cost);
        return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp,
                this.priceMultiplier);
    }
    
}
