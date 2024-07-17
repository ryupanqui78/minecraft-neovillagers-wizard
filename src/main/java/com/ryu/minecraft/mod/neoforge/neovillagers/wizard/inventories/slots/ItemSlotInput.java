package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.inventories.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemSlotInput extends Slot {
    
    private final Item item;
    
    public ItemSlotInput(Container pContainer, int pSlot, int pX, int pY, Item pItem) {
        super(pContainer, pSlot, pX, pY);
        this.item = pItem;
    }
    
    @Override
    public boolean mayPlace(ItemStack pStack) {
        return pStack.getItem() == this.item;
    }
    
}
