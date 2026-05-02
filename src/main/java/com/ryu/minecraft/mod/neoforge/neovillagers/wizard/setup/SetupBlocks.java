package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.setup;

import java.util.function.Function;

import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.NeoVillagersWizard;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.blocks.UnenchantingBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlocks {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoVillagersWizard.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoVillagersWizard.MODID);
    
    public static final DeferredBlock<UnenchantingBlock> UNENCHANTING = SetupBlocks
            .registerSingleBlock(UnenchantingBlock.BLOCK_NAME, UnenchantingBlock::new, 2.5f);
    
    private static <B extends Block> DeferredBlock<B> registerSingleBlock(String pName, Function<BlockBehaviour.Properties, ? extends B> func, float pStrength) {
        final DeferredBlock<B> block = SetupBlocks.BLOCKS.registerBlock(pName, func,
                () -> BlockBehaviour.Properties.of().strength(pStrength).requiresCorrectToolForDrops());
        SetupBlocks.ITEMS.registerSimpleBlockItem(block);
        return block;
    }
    
    private SetupBlocks() {
    }
    
}
