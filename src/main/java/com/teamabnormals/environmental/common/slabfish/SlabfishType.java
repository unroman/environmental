package com.teamabnormals.environmental.common.slabfish;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalSlabfishTypeTags;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * <p>A single type of slabfish that exists.</p>
 *
 * @author Ocelot
 */
public record SlabfishType(Component displayName, ResourceLocation texture, Optional<ResourceLocation> backpack, int priority, SlabfishCondition[] conditions) implements Predicate<SlabfishConditionContext> {

	public static final Map<TagKey<SlabfishType>, Pair<Float, ChatFormatting>> RARITIES = Util.make(new HashMap<>(), map -> {
		map.put(EnvironmentalSlabfishTypeTags.COMMON, Pair.of(1.0F, ChatFormatting.GRAY));
		map.put(EnvironmentalSlabfishTypeTags.UNCOMMON, Pair.of(0.55F, ChatFormatting.GREEN));
		map.put(EnvironmentalSlabfishTypeTags.RARE, Pair.of(0.15F, ChatFormatting.AQUA));
		map.put(EnvironmentalSlabfishTypeTags.EPIC, Pair.of(0.8F, ChatFormatting.LIGHT_PURPLE));
		map.put(EnvironmentalSlabfishTypeTags.LEGENDARY, Pair.of(0.2F, ChatFormatting.GOLD));
	});

	public static final Codec<SlabfishType> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ExtraCodecs.COMPONENT.fieldOf("displayName").forGetter(entry -> entry.displayName),
				ResourceLocation.CODEC.fieldOf("texture").forGetter(entry -> entry.texture),
				ResourceLocation.CODEC.optionalFieldOf("backpack").forGetter(entry -> entry.backpack),
				Codec.INT.optionalFieldOf("priority", 0).forGetter(entry -> entry.priority),
				SlabfishCondition.CODEC.listOf().xmap(list -> list.toArray(SlabfishCondition[]::new), Arrays::asList).fieldOf("conditions").forGetter(entry -> entry.conditions)
		).apply(instance, SlabfishType::new);
	});

	public static final Codec<SlabfishType> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ResourceLocation.CODEC.optionalFieldOf("backpack").forGetter(entry -> entry.backpack)
	).apply(instance, (backpack) -> new SlabfishType(Component.empty(), new ResourceLocation(Environmental.MOD_ID, "entity/slabfish/type/swamp"), backpack, -1, new SlabfishCondition[0])));

	public ResourceLocation fullTexture() {
		return this.texture.withPath(string -> "textures/" + string + ".png");
	}

	@Override
	public boolean test(SlabfishConditionContext slabfishEntity) {
		for (SlabfishCondition condition : this.conditions)
			if (!condition.test(slabfishEntity))
				return false;
		return true;
	}

	@Override
	public String toString() {
		return "SlabfishType{" +
				//"registryName=" + registryName +
				", displayName=" + displayName.getString() +
				//", modLoaded=" + modLoaded +
				", priority=" + priority +
				'}';
	}

	public Holder<SlabfishType> holder(Level level) {
		Registry<SlabfishType> registry = EnvironmentalRegistries.slabfishTypes(level);
		return registry.getHolderOrThrow(registry.getResourceKey(this).get());
	}

	public boolean is(Level level, TagKey<SlabfishType> tag) {
		Registry<SlabfishType> registry = EnvironmentalRegistries.slabfishTypes(level);
		Optional<HolderSet.Named<SlabfishType>> set = registry.getTag(tag);
		if (set.isEmpty())
			return false;

		return set.get().contains(this.holder(level));
	}

	public boolean canBeSold(Level level) {
		return this.is(level, EnvironmentalSlabfishTypeTags.NOT_SOLD_BY_WANDERING_TRADER);
	}

	public boolean translucent(Level level) {
		return this.is(level, EnvironmentalSlabfishTypeTags.TRANSLUCENT);
	}

	public static TagKey<SlabfishType> getRandomRarity(float chance) {
		for (Map.Entry<TagKey<SlabfishType>, Pair<Float, ChatFormatting>> entry : SlabfishType.RARITIES.entrySet()) {
			if (chance <= entry.getValue().getFirst())
				return entry.getKey();
		}

		return EnvironmentalSlabfishTypeTags.COMMON;
	}

	public TagKey<SlabfishType> getRarity(Level level) {
		for (Map.Entry<TagKey<SlabfishType>, Pair<Float, ChatFormatting>> entry : SlabfishType.RARITIES.entrySet()) {
			if (this.is(level, entry.getKey()))
				return entry.getKey();
		}

		return EnvironmentalSlabfishTypeTags.COMMON;
	}
}
