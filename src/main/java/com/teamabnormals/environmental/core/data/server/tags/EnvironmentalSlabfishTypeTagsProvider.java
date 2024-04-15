package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalSlabfishTypeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishTypes.*;

public class EnvironmentalSlabfishTypeTagsProvider extends TagsProvider<SlabfishType> {

	public EnvironmentalSlabfishTypeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, EnvironmentalRegistries.SLABFISH_TYPE, provider, Environmental.MOD_ID, helper);
	}

	@Override
	public void addTags(HolderLookup.Provider provider) {
		this.tag(EnvironmentalSlabfishTypeTags.TRANSLUCENT).add(GHOST);
		this.tag(EnvironmentalSlabfishTypeTags.NOT_SOLD_BY_WANDERING_TRADER);

		// Dunes, Rainforest, Maple, Poise
		this.tag(EnvironmentalSlabfishTypeTags.COMMON).add(SWAMP, MARSH, MANGROVE);
		this.tag(EnvironmentalSlabfishTypeTags.UNCOMMON).add(
				PLAINS, HILL, FOREST,
				BLOSSOM, TAIGA, PINE, SNOWY,
				RIVER, BEACH, OCEAN,
				DESERT, SAVANNA, CAVE);
		this.tag(EnvironmentalSlabfishTypeTags.RARE).add(
				DARK_FOREST, FLOWER_FOREST,
				JUNGLE, BADLANDS, MOUNTAIN, CHERRY_GROVE,
				WARM_OCEAN, FROZEN_OCEAN,
				DEEPSLATE, LUSH_CAVES, DRIPSTONE_CAVES, NETHER);
		this.tag(EnvironmentalSlabfishTypeTags.EPIC).add(
				MUSHROOM, DEEP_DARK, ICE_SPIKES, BAMBOO,
				SKELETON, DROWNED, NIGHTMARE, TOTEM,
				CRIMSON, WARPED, SOUL_SAND_VALLEY, BASALT_DELTAS,
				END, CHORUS);
		this.tag(EnvironmentalSlabfishTypeTags.LEGENDARY).add(BROWN_MUSHROOM, SKY, WITHER, STRAY, GHOST);
	}
}