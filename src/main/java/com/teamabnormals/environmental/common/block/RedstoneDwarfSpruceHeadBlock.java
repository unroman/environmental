package com.teamabnormals.environmental.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class RedstoneDwarfSpruceHeadBlock extends DwarfSpruceHeadBlock implements RedstoneDwarfSpruceBlock {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public RedstoneDwarfSpruceHeadBlock(Properties properties, Supplier<Item> torch) {
		super(properties, torch);
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, false).setValue(POWERED, false));
	}

	@Override
	protected BlockState getBodyState(BlockState originalState) {
		return super.getBodyState(originalState).setValue(LIT, originalState.getValue(LIT)).setValue(POWERED, originalState.getValue(POWERED));
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
		level.updateNeighborsAt(pos, this);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
		level.updateNeighborsAt(pos, this);
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean moving) {
		this.updateStateNeighborChange(level, state, pos);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		this.updateStateTick(level, state, pos);
		super.tick(state, level, pos, random);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TOP, STAR, LIT, POWERED);
	}
}