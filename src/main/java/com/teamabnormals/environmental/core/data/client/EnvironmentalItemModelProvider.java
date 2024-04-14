package com.teamabnormals.environmental.core.data.client;

import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import com.teamabnormals.environmental.common.entity.animal.koi.KoiBreed;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

import static com.teamabnormals.environmental.core.registry.EnvironmentalItems.*;

public class EnvironmentalItemModelProvider extends BlueprintItemModelProvider {

	public EnvironmentalItemModelProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Environmental.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		this.generatedItem(
				PINE_BOAT.getFirst(), PINE_BOAT.getSecond(), PINE_FURNACE_BOAT, LARGE_PINE_BOAT,
				CATTAIL_FLUFF, EnvironmentalBlocks.CATTAIL
		);

		this.koiBuckets();
	}

	private void koiBuckets() {
		for (KoiBreed breed : KoiBreed.values()) {
			String path = "item/" + ForgeRegistries.ITEMS.getKey(EnvironmentalItems.KOI_BUCKET.get()).getPath() + "/" + breed.name().toLowerCase(Locale.ROOT);
			this.withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(this.modid, path));
		}
	}
}