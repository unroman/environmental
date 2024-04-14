package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.api.BlueprintRabbitVariants;
import com.teamabnormals.blueprint.core.api.BlueprintRabbitVariants.BlueprintRabbitVariant;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalRabbitTypes {
	private static final int UNIQUE_OFFSET = 9415;

	public static final BlueprintRabbitVariant MUDDY = BlueprintRabbitVariants.register(UNIQUE_OFFSET, new ResourceLocation(Environmental.MOD_ID, "muddy"));
	public static final BlueprintRabbitVariant GRAY = BlueprintRabbitVariants.register(UNIQUE_OFFSET + 1, new ResourceLocation(Environmental.MOD_ID, "gray"));
}