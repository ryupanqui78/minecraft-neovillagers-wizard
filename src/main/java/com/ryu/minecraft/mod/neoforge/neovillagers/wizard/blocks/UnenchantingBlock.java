package com.ryu.minecraft.mod.neoforge.neovillagers.wizard.blocks;

import com.mojang.serialization.MapCodec;
import com.ryu.minecraft.mod.neoforge.neovillagers.wizard.inventories.UnenchantingMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UnenchantingBlock extends Block {
    
    public static final String BLOCK_NAME = "unenchanting";
    public static final MapCodec<UnenchantingBlock> CODEC = BlockBehaviour.simpleCodec(UnenchantingBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    
    private static final Component CONTAINER_TITLE = Component.translatable("screen.container.unenchanting");
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
    
    @Override
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((pContainerId, playerInv, pAccess) -> new UnenchantingMenu(pContainerId,
                playerInv, ContainerLevelAccess.create(pLevel, pPos)), UnenchantingBlock.CONTAINER_TITLE);
    }
    
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && (pPlayer instanceof ServerPlayer)) {
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            return InteractionResult.CONSUME;
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }
    
}
