package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.inventories;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.inventories.slots.ItemSlotInput;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup.SetupMenus;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class UnenchantingMenu extends AbstractContainerMenu {
    
    public class SlotBookInput extends Slot {
        
        public SlotBookInput(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }
        
        @Override
        public boolean mayPlace(ItemStack pStack) {
            return (pStack.getItem() == Items.WRITABLE_BOOK) && !pStack.isEnchanted();
        }
        
    }
    
    public class SlotEnchantInput extends Slot {
        
        public SlotEnchantInput(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }
        
        @Override
        public boolean mayPlace(ItemStack pStack) {
            return ((pStack.getItem() != Items.BOOK) && pStack.isEnchanted())
                    || (pStack.getItem() == Items.ENCHANTED_BOOK);
        }
        
    }
    
    public class SlotOutput extends Slot {
        
        public SlotOutput(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }
        
        @Override
        public boolean mayPickup(Player pPlayer) {
            return UnenchantingMenu.this.mayPickup(this.getSlotIndex());
        }
        
        @Override
        public boolean mayPlace(ItemStack pStack) {
            return false;
        }
        
        @Override
        public void onTake(Player pPlayer, ItemStack pStack) {
            UnenchantingMenu.this.onTake(pPlayer, pStack);
        }
        
    }
    
    public static final String MENU_NAME = "unenchanting";
    private static final int RESULT_X = 134;
    private static final int RESULT_Y_INCREMENT = 18;
    private static final int RESULT_Y_START = 8;
    
    private final ContainerLevelAccess access;
    private final Container inputSlots = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            UnenchantingMenu.this.slotsChanged(this);
        }
    };
    private final ResultContainer[] resultSlots = new ResultContainer[4];
    
    // Client constructor
    public UnenchantingMenu(int pContainerId, Inventory pInventory) {
        this(pContainerId, pInventory, ContainerLevelAccess.NULL);
    }
    
    // Server constructor
    public UnenchantingMenu(int pContainerId, Inventory pInventory, ContainerLevelAccess pAccess) {
        super(SetupMenus.UNENCHANTING_CONTAINER.get(), pContainerId);
        
        this.access = pAccess;
        
        this.addSlot(new SlotEnchantInput(this.inputSlots, 0, 26, 20));
        this.addSlot(new SlotBookInput(this.inputSlots, 1, 66, 30));
        this.addSlot(new ItemSlotInput(this.inputSlots, 2, 26, 41, Items.LAPIS_BLOCK));
        
        for (int i = 0; i < this.resultSlots.length; i++) {
            this.resultSlots[i] = new ResultContainer();
            this.addSlot(new SlotOutput(this.resultSlots[i], i, UnenchantingMenu.RESULT_X,
                    UnenchantingMenu.RESULT_Y_START + (UnenchantingMenu.RESULT_Y_INCREMENT * i)));
        }
        
        this.addInventorySlots(pInventory);
    }
    
    private void addInventorySlots(Inventory inventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + (i * 9) + 9, 8 + (j * 18), 84 + (i * 18)));
            }
        }
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, 8 + (k * 18), 142));
        }
    }
    
    protected boolean mayPickup(int pIndex) {
        // TODO Auto-generated method stub
        return false;
    }
    
    protected void onTake(Player pPlayer, ItemStack pStack) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(this.access, pPlayer, SetupBlocks.UNENCHANTING.get());
    }
}
