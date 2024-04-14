package com.teamabnormals.environmental.common.slabfish;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SlabfishProvider implements DataProvider {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	protected final PackOutput output;
	protected final Map<ResourceLocation, TagBuilder> builders = Maps.newLinkedHashMap();
	protected final String modId;
	protected final ExistingFileHelper existingFileHelper;

	public SlabfishProvider(PackOutput output, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		this.output = output;
		this.modId = modId;
		this.existingFileHelper = existingFileHelper;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cachedOutput) {
		Path path = this.output.getOutputFolder();
		Set<ResourceLocation> set = Sets.newHashSet();
		Consumer<SlabfishType> consumer = (slabfishType) -> {
			if (!set.add(slabfishType.getRegistryName())) {
				throw new IllegalStateException("Duplicate slabfish type " + slabfishType.getRegistryName());
			} else {
				Path path1 = createPath(path, slabfishType);
				DataProvider.saveStable(cachedOutput, slabfishType.serializeToJson(), path1);
			}
		};

		registerSlabfishTypes(consumer, existingFileHelper);
		return CompletableFuture.completedFuture(null);
	}

	protected void registerSlabfishTypes(Consumer<SlabfishType> consumer, ExistingFileHelper helper) {
		SlabfishManager.DEFAULT_SLABFISH.save(consumer, new ResourceLocation(Environmental.MOD_ID, "test"));
	}

	private static Path createPath(Path path, SlabfishType slabfish) {
		return path.resolve("data/" + slabfish.getRegistryName().getNamespace() + "/slabfish/type/" + slabfish.getRegistryName().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Slabfish";
	}
}
