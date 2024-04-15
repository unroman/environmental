package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is inside of the specified block or block tag.</p>
 *
 * @author Ocelot
 */
public class SlabfishInFluidCondition implements SlabfishCondition {
	public static final Codec<SlabfishInFluidCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryCodecs.homogeneousList(Registries.FLUID).fieldOf("fluids").forGetter(entry -> entry.fluids)
	).apply(instance, SlabfishInFluidCondition::new));

	private final HolderSet<Fluid> fluids;

	public SlabfishInFluidCondition(HolderSet<Fluid> fluids) {
		this.fluids = fluids;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return this.fluids.contains(context.getFluid());
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.IN_FLUID.get();
	}
}
