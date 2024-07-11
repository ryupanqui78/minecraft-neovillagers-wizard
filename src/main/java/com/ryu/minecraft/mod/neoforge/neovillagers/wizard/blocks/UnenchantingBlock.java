package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.blocks;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UnenchantingBlock extends Block {
    
    public static final String BLOCK_NAME = "unenchanting";
    public static final MapCodec<UnenchantingBlock> CODEC = BlockBehaviour.simpleCodec(UnenchantingBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    
    public UnenchantingBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UnenchantingBlock.FACING, Direction.NORTH));
    }
    
    @Override
    protected MapCodec<UnenchantingBlock> codec() {
        return UnenchantingBlock.CODEC;
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UnenchantingBlock.FACING);
    }
    
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter getter, BlockPos pPos, CollisionContext pContext) {
        return UnenchantingBlock.SHAPE;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(UnenchantingBlock.FACING,
                pContext.getHorizontalDirection().getOpposite());
    }
    
}
