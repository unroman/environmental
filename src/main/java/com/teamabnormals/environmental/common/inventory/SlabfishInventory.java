package com.teamabnormals.environmental.common.inventory;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.DynamicInventory;
import com.teamabnormals.environmental.common.slabfish.SlabfishLoader;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

/**
 * <p>An implementation of {@link DynamicInventory} for {@link Slabfish}.</p>
 *
 * @author Ocelot
 */
public class SlabfishInventory extends DynamicInventory {
	private final Slabfish slabfish;

	public SlabfishInventory(Slabfish slabfish) {
		this.slabfish = slabfish;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		switch (index) {
			case 0:
				return SlabfishLoader.getSweaterType(EnvironmentalRegistries.slabfishSweaters(this.slabfish.getCommandSenderWorld()), stack).isPresent();
			case 1:
				return stack.is(Tags.Items.CHESTS_WOODEN);
			case 2:
				if (SlabfishLoader.getBackpackType(EnvironmentalRegistries.slabfishBackpacks(this.slabfish.getCommandSenderWorld()), stack).isEmpty())
					return false;
				SlabfishType slabfishType = this.slabfish.getSlabfishType();
				return this.slabfish.hasBackpack() && (slabfishType.isBackpackEmpty(this.slabfish.getCommandSenderWorld()));
			default:
				return super.canPlaceItem(index, stack);
		}
	}

	@Override
	public int getSlotStackLimit(int index) {
		return index < 3 ? 1 : super.getSlotStackLimit(index);
	}

	@Override
	public int getContainerSize() {
		return 3 + (this.slabfish.hasBackpack() || this.getItem(1).is(Tags.Items.CHESTS_WOODEN) ? 15 : 0);
	}

	@Override
	public boolean stillValid(Player player) {
		return this.slabfish.isAlive() && player.distanceToSqr(this.slabfish) <= 64.0;
	}
}
