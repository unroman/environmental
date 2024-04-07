package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBannerPatternTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBannerPatterns;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EnvironmentalBannerPatternTagsProvider extends BannerPatternTagsProvider {

	public EnvironmentalBannerPatternTagsProvider(DataGenerator generator, ExistingFileHelper helper) {
		super(generator, Environmental.MOD_ID, helper);
	}

	@Override
	public void addTags() {
		this.tag(EnvironmentalBannerPatternTags.PATTERN_ITEM_LUMBERER).add(EnvironmentalBannerPatterns.LUMBERER.get());
	}
}