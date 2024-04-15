package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Optional;

public class SlabfishBreedCondition implements SlabfishCondition {
	public static final Codec<SlabfishBreedCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("parent").forGetter(SlabfishBreedCondition::getParent),
			ResourceLocation.CODEC.optionalFieldOf("partner").forGetter(c -> Optional.ofNullable(c.getPartner()))
	).apply(instance, (parent, partner) -> new SlabfishBreedCondition(parent, partner.orElse(null))));

	private final ResourceLocation parent;
	@Nullable
	private final ResourceLocation partner;

	public SlabfishBreedCondition(ResourceLocation parent, @Nullable ResourceLocation partner) {
		this.parent = parent;
		this.partner = partner;
	}

	public ResourceLocation getParent() {
		return parent;
	}

	@Nullable
	public ResourceLocation getPartner() {
		return partner;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		Pair<ResourceLocation, ResourceLocation> parentTypes = context.getParentTypes();
		if (parentTypes == null)
			return false;

		if (!this.parent.equals(parentTypes.getLeft()) && !this.parent.equals(parentTypes.getRight()))
			return false;

		if (this.partner == null)
			return true;

		return (this.parent.equals(parentTypes.getLeft()) && this.partner.equals(parentTypes.getRight())) || (this.parent.equals(parentTypes.getRight()) && this.partner.equals(parentTypes.getLeft()));
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.BREED.get();
	}
}