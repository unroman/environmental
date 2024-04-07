package com.teamabnormals.environmental.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface RedstoneDwarfSpruceBlock {

	default void updateStateNeighborChange(Level level, BlockState state, BlockPos pos) {
		if (!level.isClientSide) {
			boolean powered = level.hasNeighborSignal(pos);
			boolean lit = powered || isTreePowered(level, pos);

			if (!state.getValue(BlockStateProperties.POWERED) && powered)
				level.setBlock(pos, state.setValue(BlockStateProperties.LIT, true).setValue(BlockStateProperties.POWERED, true), 2);
			else if (state.getValue(BlockStateProperties.LIT) != lit || state.getValue(BlockStateProperties.POWERED) != powered)
				level.scheduleTick(pos, (Block) this, 4);
		}
	}

	default void updateStateTick(Level level, BlockState state, BlockPos pos) {
		boolean powered = level.hasNeighborSignal(pos);
		boolean lit = powered || isTreePowered(level, pos);

		if (state.getValue(BlockStateProperties.LIT) != lit || state.getValue(BlockStateProperties.POWERED) != powered)
			level.setBlock(pos, state.setValue(BlockStateProperties.LIT, lit).setValue(BlockStateProperties.POWERED, powered), 2);
	}

	static BlockState setLitPoweredState(BlockState state, Level level, BlockPos pos) {
		boolean powered = level.hasNeighborSignal(pos);
		boolean lit = powered || isTreePowered(level, pos);
		return state.setValue(BlockStateProperties.LIT, lit).setValue(BlockStateProperties.POWERED, powered);
	}

	private static boolean isTreePowered(Level level, BlockPos pos) {
		return isTreePowered(level, pos, Direction.DOWN) || isTreePowered(level, pos, Direction.UP);
	}

	private static boolean isTreePowered(Level level, BlockPos pos, Direction direction) {
		MutableBlockPos mutable = pos.mutable();
		while (true) {
			mutable.move(direction);
			BlockState blockstate = level.getBlockState(mutable);
			if (!(blockstate.getBlock() instanceof RedstoneDwarfSpruceHeadBlock) && !(blockstate.getBlock() instanceof RedstoneDwarfSprucePlantBlock) || !blockstate.getValue(BlockStateProperties.LIT))
				return false;

			if (blockstate.getValue(BlockStateProperties.POWERED))
				return true;
		}
	}
}