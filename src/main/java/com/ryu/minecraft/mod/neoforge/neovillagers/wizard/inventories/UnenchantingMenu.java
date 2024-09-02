package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.inventories;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.helpers.UnenchantingHelper;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

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
            UnenchantingMenu.this.onTake(pStack);
        }
        
    }
    
    public static final String MENU_NAME = "unenchanting";
    
    private static final int[] REQUIRE_EXPERIENCE = new int[] { 5, 10, 15, 20 };
    private static final int RESULT_X = 134;
    private static final int RESULT_Y_INCREMENT = 18;
    private static final int RESULT_Y_START = 8;
    private static final int SLOT_INPUT_ITEM_INDEX = 0;
    private static final int SLOT_LAPIS_BLOCK_INDEX = 2;
    private static final int SLOT_LAST_INVENTORY_INDEX = 43; // plus one
    private static final int SLOT_START_INVENTORY_INDEX = 7;
    private static final int SLOT_START_RESULT_INDEX = 3;
    private static final int SLOT_WRITABLE_BOOK_INDEX = 1;
    
    private final ContainerLevelAccess access;
    private final int[] enchantDamage = new int[] { 0, 0, 0, 0 };
    private final int[] enchantLevel = new int[] { -1, -1, -1, -1 };
    private final int[] enchantMinLevel = new int[] { 0, 0, 0, 0 };
    private final Container inputSlots = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            UnenchantingMenu.this.slotsChanged(this);
        }
    };
    private final Player player;
    private final ResultContainer[] resultSlots = new ResultContainer[4];
    
    private int currentNumLapis = 0;
    private int totalPower = 0;
    
    // Client constructor
    public UnenchantingMenu(int pContainerId, Inventory pInventory) {
        this(pContainerId, pInventory, ContainerLevelAccess.NULL);
    }
    
    // Server constructor
    public UnenchantingMenu(int pContainerId, Inventory pInventory, ContainerLevelAccess pAccess) {
        super(SetupMenus.UNENCHANTING_CONTAINER.get(), pContainerId);
        
        this.player = pInventory.player;
        this.access = pAccess;
        this.access.execute(
                (level, blockPos) -> this.totalPower = UnenchantingHelper.calculateTotalPower(level, blockPos));
        
        this.addSlot(new SlotEnchantInput(this.inputSlots, UnenchantingMenu.SLOT_INPUT_ITEM_INDEX, 26, 20));
        this.addSlot(new SlotBookInput(this.inputSlots, UnenchantingMenu.SLOT_WRITABLE_BOOK_INDEX, 66, 30));
        this.addSlot(
                new ItemSlotInput(this.inputSlots, UnenchantingMenu.SLOT_LAPIS_BLOCK_INDEX, 26, 41, Items.LAPIS_BLOCK));
        
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
    
    private void changeResult(ResultContainer resultContainer, Enchantment pEnchantment, int pLevel, int i) {
        if (resultContainer.getItem(0).isEmpty()) {
            final Map<Enchantment, Integer> resultEnchantmentMap = Maps.newLinkedHashMap();
            final ItemStack resultItem = new ItemStack(Items.ENCHANTED_BOOK);
            final ItemStack inputItem = this.inputSlots.getItem(UnenchantingMenu.SLOT_INPUT_ITEM_INDEX);
            
            final int newLevel = UnenchantingHelper.defineLevel(pEnchantment, pLevel, this.totalPower);
            
            resultEnchantmentMap.put(pEnchantment, newLevel);
            EnchantmentHelper.setEnchantments(resultEnchantmentMap, resultItem);
            resultContainer.setItem(0, resultItem);
            
            this.enchantLevel[i - 1] = newLevel;
            this.enchantMinLevel[i - 1] = i * 5;
            
            if (inputItem.getItem() == Items.ENCHANTED_BOOK) {
                this.enchantDamage[i - 1] = 0;
            } else {
                int damage = i * 10;
                
                if (this.totalPower > 20) {
                    damage = (damage * 10) / 100;
                } else if (this.totalPower > 0) {
                    final int minDamage = damage / 10;
                    final int varDamage = damage - minDamage;
                    final int gainDamage = (varDamage * this.totalPower * 5) / 100;
                    damage = minDamage + (varDamage - gainDamage);
                }
                this.enchantDamage[i - 1] = damage;
            }
        }
        
    }
    
    private void cleanEnchantInfo() {
        for (int i = 0; i < this.resultSlots.length; i++) {
            this.resultSlots[i].setItem(0, ItemStack.EMPTY);
            this.enchantMinLevel[i] = 0;
            this.enchantDamage[i] = 0;
            this.enchantLevel[i] = -1;
        }
    }
    
    public int getCurrentNumLapis() {
        return this.currentNumLapis;
    }
    
    public int[] getEnchantDamage() {
        return this.enchantDamage;
    }
    
    public int[] getEnchantMinLevel() {
        return this.enchantMinLevel;
    }
    
    private boolean hasAllInputSlot() {
        boolean hasValues = false;
        if (!this.inputSlots.isEmpty()) {
            final boolean hasItem = !this.inputSlots.getItem(UnenchantingMenu.SLOT_INPUT_ITEM_INDEX).isEmpty();
            if (!this.player.getAbilities().instabuild) {
                final boolean hasBook = !this.inputSlots.getItem(UnenchantingMenu.SLOT_WRITABLE_BOOK_INDEX).isEmpty();
                final boolean hasLapis = !this.inputSlots.getItem(UnenchantingMenu.SLOT_LAPIS_BLOCK_INDEX).isEmpty();
                hasValues = hasBook && hasLapis && hasItem;
            } else {
                hasValues = hasItem;
            }
        }
        return hasValues;
    }
    
    public boolean isInCreativeMode() {
        return this.player.getAbilities().instabuild;
    }
    
    protected boolean mayPickup(int pIndex) {
        boolean mayPickup = this.isInCreativeMode();
        if (!mayPickup) {
            final ItemStack inputLapis = this.inputSlots.getItem(UnenchantingMenu.SLOT_LAPIS_BLOCK_INDEX);
            
            mayPickup = (this.player.experienceLevel >= UnenchantingMenu.REQUIRE_EXPERIENCE[pIndex])
                    && (inputLapis.getCount() >= (pIndex + 1));
        }
        return mayPickup;
    }
    
    protected void onTake(ItemStack pStack) {
        final Optional<Enchantment> resultEnchantment = EnchantmentHelper.getEnchantments(pStack).keySet().stream()
                .findFirst();
        
        if (!resultEnchantment.isPresent()) {
            return;
        }
        
        final ItemStack inputItem = this.inputSlots.getItem(UnenchantingMenu.SLOT_INPUT_ITEM_INDEX);
        final Map<Enchantment, Integer> inputEnchantMap = EnchantmentHelper.getEnchantments(inputItem);
        final Rarity rarity = resultEnchantment.get().getRarity();
        
        int cost = 0;
        int damage;
        
        inputEnchantMap.remove(resultEnchantment.get());
        EnchantmentHelper.setEnchantments(inputEnchantMap, inputItem);
        
        switch (rarity) {
        case VERY_RARE:
            cost = 4;
            damage = this.enchantDamage[3];
            break;
        case RARE:
            cost = 3;
            damage = this.enchantDamage[2];
            break;
        case UNCOMMON:
            cost = 2;
            damage = this.enchantDamage[1];
            break;
        case COMMON:
        default:
            cost = 1;
            damage = this.enchantDamage[0];
        }
        
        if (inputItem.isDamageableItem() && (damage > 0)) {
            inputItem.setDamageValue(inputItem.getDamageValue() + ((inputItem.getMaxDamage() * damage) / 100));
            if (inputItem.getDamageValue() >= (inputItem.getMaxDamage() - 1)) {
                inputItem.shrink(1);
            } else {
                this.inputSlots.setChanged();
            }
        } else {
            if (inputItem.is(Items.ENCHANTED_BOOK)) {
                if (inputEnchantMap.size() == 0) {
                    this.inputSlots.setItem(UnenchantingMenu.SLOT_INPUT_ITEM_INDEX, new ItemStack(Items.BOOK));
                } else {
                    final ItemStack newItemStack = new ItemStack(Items.ENCHANTED_BOOK);
                    EnchantmentHelper.setEnchantments(inputEnchantMap, newItemStack);
                    this.inputSlots.setItem(UnenchantingMenu.SLOT_INPUT_ITEM_INDEX, newItemStack);
                }
            }
        }
        this.inputSlots.removeItem(UnenchantingMenu.SLOT_WRITABLE_BOOK_INDEX, 1);
        this.inputSlots.removeItem(UnenchantingMenu.SLOT_LAPIS_BLOCK_INDEX, cost);
        this.player.giveExperienceLevels(-cost);
        this.broadcastChanges();
    }
    
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        final Slot slot = this.slots.get(pIndex);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        
        final ItemStack slotItem = slot.getItem();
        final ItemStack slotItemCopy = slotItem.copy();
        
        if (pIndex < UnenchantingMenu.SLOT_START_RESULT_INDEX) { // If slot is input
            if (!this.moveItemStackTo(slotItem, UnenchantingMenu.SLOT_START_INVENTORY_INDEX,
                    UnenchantingMenu.SLOT_LAST_INVENTORY_INDEX, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < UnenchantingMenu.SLOT_START_INVENTORY_INDEX) { // If slot is output
            if (!this.moveItemStackTo(slotItem, UnenchantingMenu.SLOT_START_INVENTORY_INDEX,
                    UnenchantingMenu.SLOT_LAST_INVENTORY_INDEX, true)) {
                return ItemStack.EMPTY;
            }
            slot.onTake(pPlayer, slotItemCopy);
        } else if ((pIndex < UnenchantingMenu.SLOT_LAST_INVENTORY_INDEX) && !this.moveItemStackTo(slotItem,
                UnenchantingMenu.SLOT_INPUT_ITEM_INDEX, UnenchantingMenu.SLOT_START_RESULT_INDEX, true)) { // If slot is from player inventory
            return ItemStack.EMPTY;
        }
        
        if (slotItem.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        
        if (slotItem.getCount() == slotItemCopy.getCount()) {
            return ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }
    
    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((pLevel, pPos) -> this.clearContainer(pPlayer, this.inputSlots));
    }
    
    @Override
    public void slotsChanged(Container pContainer) {
        this.cleanEnchantInfo();
        if (this.hasAllInputSlot()) {
            final ItemStack inputItem = this.inputSlots.getItem(UnenchantingMenu.SLOT_INPUT_ITEM_INDEX);
            final Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.getEnchantments(inputItem);
            
            enchantmentMap.keySet().forEach(enchantment -> {
                final Rarity rarity = enchantment.getRarity();
                final int level = enchantmentMap.get(enchantment);
                switch (rarity) {
                case VERY_RARE:
                    this.changeResult(this.resultSlots[3], enchantment, level, 4);
                    break;
                case RARE:
                    this.changeResult(this.resultSlots[2], enchantment, level, 3);
                    break;
                case UNCOMMON:
                    this.changeResult(this.resultSlots[1], enchantment, level, 2);
                    break;
                case COMMON:
                    this.changeResult(this.resultSlots[0], enchantment, level, 1);
                    break;
                default:
                    // Nothing
                }
            });
            this.currentNumLapis = this.inputSlots.getItem(UnenchantingMenu.SLOT_LAPIS_BLOCK_INDEX).getCount();
            this.broadcastChanges();
        } else {
            super.slotsChanged(pContainer);
        }
    }
    
    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(this.access, pPlayer, SetupBlocks.UNENCHANTING.get());
    }
    
}
