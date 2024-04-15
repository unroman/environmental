package com.teamabnormals.environmental.common.slabfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishSweaters;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishTypes;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>Loads all slabfish from data packs as a server implementation of {@link SlabfishManager}.</p>
 *
 * @author Ocelot
 */
public class SlabfishLoader extends SimpleJsonResourceReloadListener implements SlabfishManager {
	private static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
			.registerTypeAdapter(Component.class, new Component.Serializer())
			.create();

	static SlabfishLoader instance;


	public SlabfishLoader() {
		super(GSON, "environmental/slabfish");
		instance = this;
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {

	}

	@Override
	public Optional<SlabfishType> getSlabfishType(Registry<SlabfishType> registry, Predicate<SlabfishType> predicate, SlabfishConditionContext context) {
		return registry.stream().filter(slabfishType -> !registry.getKey(slabfishType).equals(EnvironmentalSlabfishTypes.SWAMP.location()) && predicate.test(slabfishType) && slabfishType.test(context)).max(Comparator.comparingInt(SlabfishType::priority));
	}

	@Override
	public Optional<SweaterType> getSweaterType(Registry<SweaterType> registry, ItemStack stack) {
		return registry.stream().filter(sweaterType -> sweaterType != registry.get(EnvironmentalSlabfishSweaters.EMPTY) && sweaterType.test(stack)).findFirst();
	}

	@Override
	public Optional<BackpackType> getBackpackType(Registry<BackpackType> registry, ItemStack stack) {
		return registry.stream().filter(backpackType -> backpackType.test(stack)).findFirst();
	}

	@Override
	public Optional<SlabfishType> getRandomSlabfishType(Registry<SlabfishType> registry, Predicate<SlabfishType> predicate, RandomSource random) {
		return Util.getRandomSafe(registry.stream().filter(predicate).collect(Collectors.toList()), random);
	}
}
