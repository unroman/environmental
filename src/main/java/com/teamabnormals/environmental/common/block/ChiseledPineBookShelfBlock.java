package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.common.block.BlueprintChiseledBookShelfBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec2;

public class ChiseledPineBookShelfBlock extends BlueprintChiseledBookShelfBlock {

	public ChiseledPineBookShelfBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public int getHitSlot(Vec2 vec2) {
		int i = vec2.x <= 0.5F ? 0 : 1;
		int j = getSection(vec2.y);
		return j * 2 + i;
	}

	public static int getSection(float y) {
		if (y < 0.25F) {
			return 2;
		} else {
			return y < 0.5F ? 1 : 0;
		}
	}
}
