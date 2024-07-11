package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.NeoVillagersWizard;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.blocks.UnenchantingBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlocks {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoVillagersWizard.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoVillagersWizard.MODID);
    
    public static final DeferredBlock<UnenchantingBlock> UNENCHANTING_TABLE = SetupBlocks.BLOCKS.registerBlock(
            UnenchantingBlock.BLOCK_NAME, UnenchantingBlock::new,
            BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    
    public static final DeferredItem<BlockItem> UNENCHANTING_TABLE_ITEM = SetupBlocks.ITEMS
            .registerSimpleBlockItem(SetupBlocks.UNENCHANTING_TABLE);
    
    private SetupBlocks() {
    }
    
}
