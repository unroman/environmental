package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in any of the specified biomes if they are registered.</p>
 *
 * @author Ocelot
 */
public class SlabfishInBiomeCondition implements SlabfishCondition {
	public static final Codec<SlabfishInBiomeCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(entry -> entry.biomes)
	).apply(instance, SlabfishInBiomeCondition::new));

	private final HolderSet<Biome> biomes;

	public SlabfishInBiomeCondition(HolderSet<Biome> biomes) {
		this.biomes = biomes;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return this.biomes.contains(context.getBiome());
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.IN_BIOME.get();
	}
}
