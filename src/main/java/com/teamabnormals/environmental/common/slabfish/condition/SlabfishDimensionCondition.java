package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in the specified dimension.</p>
 *
 * @author Ocelot
 */
public class SlabfishDimensionCondition implements SlabfishCondition {
	public static final Codec<SlabfishDimensionCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("dimension").forGetter(SlabfishDimensionCondition::getDimension)
	).apply(instance, SlabfishDimensionCondition::new));

	private final ResourceLocation dimensionRegistryName;

	public SlabfishDimensionCondition(ResourceLocation dimensionRegistryName) {
		this.dimensionRegistryName = dimensionRegistryName;
	}

	public ResourceLocation getDimension() {
		return dimensionRegistryName;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getDimension().equals(this.dimensionRegistryName);
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.DIMENSION.get();
	}
}
