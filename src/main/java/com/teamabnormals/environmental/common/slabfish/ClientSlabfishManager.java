package com.teamabnormals.environmental.common.slabfish;

import com.teamabnormals.environmental.common.network.message.SSyncBackpackTypeMessage;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>A client wrapper for {@link SlabfishManager} that reflects what the server has.</p>
 *
 * @author Ocelot
 */
public final class ClientSlabfishManager implements SlabfishManager {
	static final ClientSlabfishManager INSTANCE = new ClientSlabfishManager();

	private final Map<ResourceLocation, BackpackType> backpackTypes;

	private ClientSlabfishManager() {
		this.backpackTypes = new HashMap<>();
	}

	/**
	 * Receives the backpack types from the server.
	 *
	 * @param msg The message containing the new types
	 */
	public static void receive(SSyncBackpackTypeMessage msg) {
		INSTANCE.backpackTypes.clear();
		INSTANCE.backpackTypes.putAll(Arrays.stream(msg.getBackpackTypes()).collect(Collectors.toMap(BackpackType::getRegistryName, backpackType -> backpackType)));
	}

	@Override
	public Optional<BackpackType> getBackpackType(ResourceLocation registryName) {
		return Optional.ofNullable(this.backpackTypes.get(registryName));
	}

	@Override
	public Optional<SlabfishType> getSlabfishType(Registry<SlabfishType> registry, Predicate<SlabfishType> predicate, SlabfishConditionContext context) {
		throw new UnsupportedOperationException("Client does not have access to select random slabfish");
	}

	@Override
	public Optional<SweaterType> getSweaterType(Registry<SweaterType> registry, ItemStack stack) {
		return registry.stream().filter(sweaterType -> sweaterType.test(stack)).findFirst();
	}

	@Override
	public Optional<BackpackType> getBackpackType(ItemStack stack) {
		return this.backpackTypes.values().stream().filter(backpackType -> backpackType.test(stack)).findFirst();
	}

	@Override
	public Optional<SlabfishType> getRandomSlabfishType(Registry<SlabfishType> registry, Predicate<SlabfishType> predicate, RandomSource random) {
		throw new UnsupportedOperationException("Client does not have access to select random slabfish");
	}

	@Override
	public BackpackType[] getAllBackpackTypes() {
		return this.backpackTypes.values().toArray(new BackpackType[0]);
	}
}
