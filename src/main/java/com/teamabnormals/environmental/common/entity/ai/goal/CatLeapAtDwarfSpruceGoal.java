package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.block.DwarfSpruceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CatLeapAtDwarfSpruceGoal extends MoveToBlockGoal {
	private final Cat cat;
	private boolean leaping;

	public CatLeapAtDwarfSpruceGoal(Cat cat) {
		super(cat, 0.6D, 6);
		this.cat = cat;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		return this.cat.isTame() && !this.cat.isOrderedToSit() && !this.cat.isLying() && super.canUse();
	}

	@Override
	public boolean canContinueToUse() {
		return ((!this.cat.isOnGround() && !this.cat.isInFluidType()) || !this.leaping) && super.canContinueToUse();
	}

	@Override
	public void start() {
		super.start();
		this.cat.setInSittingPose(false);
		this.leaping = false;
	}

	@Override
	public void tick() {
		super.tick();
		double d0 = this.blockPos.getX() + 0.5D - this.cat.getX();
		double d1 = this.blockPos.getY() - this.cat.getY();
		double d2 = this.blockPos.getZ() + 0.5D - this.cat.getZ();
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);

		if (d3 < 2.0D && Math.abs(d1) < 4.0D && this.cat.isOnGround()) {
			Vec3 vec3 = this.cat.getDeltaMovement();
			Vec3 vec31 = new Vec3(this.blockPos.getX() - this.cat.getX(), 0.0D, this.blockPos.getZ() - this.cat.getZ());
			if (vec31.lengthSqr() > 1.0E-7D)
				vec31 = vec31.normalize().scale(0.3D).add(vec3.scale(0.2D));
			double yd = Math.max((this.blockPos.getY() - this.cat.getY()) * 0.5D, 0.3D);
			this.mob.setDeltaMovement(vec31.x, yd, vec31.z);
			this.leaping = true;

			BlockState blockstate = this.cat.level.getBlockState(this.blockPos);
			this.cat.level.setBlockAndUpdate(this.blockPos, ((DwarfSpruceBlock) blockstate.getBlock()).getWithoutTorchesState(blockstate));
		}
	}

	@Override
	protected boolean findNearestBlock() {
		boolean flag = super.findNearestBlock();
		if (flag) {
			List<BlockPos> treepositions = new ArrayList<>();
			treepositions.add(this.blockPos);
			for (int i = 1; i < 3; i = i > 0 ? -i : -i + 1) {
				BlockPos blockpos = this.blockPos.offset(0, i, 0);
				if (this.isValidTarget(this.cat.level, blockpos))
					treepositions.add(blockpos);
			}
			this.blockPos = treepositions.get(this.cat.getRandom().nextInt(treepositions.size()));
			System.out.println("WAA");
		}
		return flag;
	}

	@Override
	protected boolean isValidTarget(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos).getBlock() instanceof DwarfSpruceBlock dwarfspruce && dwarfspruce.getTorch() != null;
	}
}