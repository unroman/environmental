package com.teamabnormals.environmental.common.slabfish;

import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * <p>A client wrapper for {@link SlabfishManager} that reflects what the server has.</p>
 *
 * @author Ocelot
 */
public final class ClientSlabfishManager implements SlabfishManager {
	static final ClientSlabfishManager INSTANCE = new ClientSlabfishManager();

	private ClientSlabfishManager() {
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
	public Optional<BackpackType> getBackpackType(Registry<BackpackType> registry, ItemStack stack) {
		return registry.stream().filter(backpackType -> backpackType.test(stack)).findFirst();
	}

	@Override
	public Optional<SlabfishType> getRandomSlabfishType(Registry<SlabfishType> registry, Predicate<SlabfishType> predicate, RandomSource random) {
		throw new UnsupportedOperationException("Client does not have access to select random slabfish");
	}
}
