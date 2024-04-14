package com.teamabnormals.environmental.core.data.client;

import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.common.block.CattailStalkBlock;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalBlockFamilies;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;

public class EnvironmentalBlockStateProvider extends BlueprintBlockStateProvider {

	public EnvironmentalBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Environmental.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.blockFamily(EnvironmentalBlockFamilies.DIRT_BRICK_FAMILY);
		this.blockFamily(EnvironmentalBlockFamilies.DIRT_TILE_FAMILY);

		this.blockFamily(EnvironmentalBlockFamilies.PINE_PLANKS_FAMILY);
		this.logBlocks(PINE_LOG, PINE_WOOD);
		this.logBlocks(STRIPPED_PINE_LOG, STRIPPED_PINE_WOOD);
		this.leavesBlocks(PINE_LEAVES, PINE_LEAF_PILE);
		this.crossBlockWithPot(PINE_SAPLING, POTTED_PINE_SAPLING);
		this.woodworksBlocks(PINE_PLANKS, PINE_BOARDS, PINE_LADDER, PINE_BOOKSHELF, PINE_BEEHIVE, PINE_CHEST, TRAPPED_PINE_CHEST);

		this.cubeColumnBlock(PINECONE);
		this.cubeColumnBlock(WAXED_PINECONE, PINECONE);

		this.leavesBlocks(CHEERFUL_CHERRY_LEAVES, CHEERFUL_CHERRY_LEAF_PILE);
		this.crossBlockWithPot(CHEERFUL_CHERRY_SAPLING, POTTED_CHEERFUL_CHERRY_SAPLING);

		this.leavesBlocks(MOODY_CHERRY_LEAVES, MOODY_CHERRY_LEAF_PILE);
		this.crossBlockWithPot(MOODY_CHERRY_SAPLING, POTTED_MOODY_CHERRY_SAPLING);

		this.block(CATTAIL_FLUFF_BLOCK);
		this.cattail(CATTAIL_SPROUT, CATTAIL, CATTAIL_STALK);

		this.directionalBlock(CHERRY_CRATE);

		this.cactusBobble(CACTUS_BOBBLE);
	}

	public void cubeColumnBlock(RegistryObject<Block> block) {
		this.cubeColumnBlock(block, block);
	}

	public void cubeColumnBlock(RegistryObject<Block> block, RegistryObject<Block> parent) {
		this.simpleBlock(block.get(), this.models().cubeColumn(name(block.get()), suffix(blockTexture(parent.get()), "_side"), suffix(blockTexture(parent.get()), "_end")));
		this.blockItem(block);
	}

	public void cattail(RegistryObject<Block> cattailSproutObject, RegistryObject<Block> cattailObject, RegistryObject<Block> cattailStalkObject) {
		Block cattailSprout = cattailSproutObject.get();
		Block cattail = cattailObject.get();
		Block cattailStalk = cattailStalkObject.get();

		this.getVariantBuilder(cattailSprout).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(this.cattailStalkModel(name(cattailSprout), blockTexture(cattailSprout), state.getValue(CattailBlock.CATTAILS), false)).build(), BlockStateProperties.WATERLOGGED);

		MultiPartBlockStateBuilder cattailBuilder = this.getMultipartBuilder(cattail);

		for (int i = 1; i <= 3; i++) {
			cattailBuilder
					.part().modelFile(cattailStalkModel(name(cattail), blockTexture(cattail), i, false)).addModel().condition(CattailBlock.TOP, false).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailStalkModel(name(cattail) + "_top", suffix(blockTexture(cattail), "_stalk_top"), i, true)).addModel().condition(CattailBlock.TOP, true).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(name(cattail) + "_head", suffix(blockTexture(cattail), "_head"), false, i)).addModel().condition(CattailBlock.FLUFFY, false).condition(CattailBlock.TOP, false).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(name(cattail) + "_head", suffix(blockTexture(cattail), "_head"), true, i)).addModel().condition(CattailBlock.FLUFFY, false).condition(CattailBlock.TOP, true).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(name(cattail) + "_head_fluffy", suffix(blockTexture(cattail), "_head_fluffy"), false, i)).addModel().condition(CattailBlock.FLUFFY, true).condition(CattailBlock.TOP, false).condition(CattailBlock.CATTAILS, i).end()
					.part().modelFile(cattailHeadModel(name(cattail) + "_head_fluffy", suffix(blockTexture(cattail), "_head_fluffy"), true, i)).addModel().condition(CattailBlock.FLUFFY, true).condition(CattailBlock.TOP, true).condition(CattailBlock.CATTAILS, i).end();
		}

		this.getVariantBuilder(cattailStalk).forAllStatesExcept(state -> {
			return ConfiguredModel.builder().modelFile(this.cattailStalkModel(name(cattailStalk) + (state.getValue(CattailStalkBlock.BOTTOM) ? "_bottom" : "_middle"), suffix(blockTexture(cattailStalk), (state.getValue(CattailStalkBlock.BOTTOM) ? "_bottom" : "_middle")), state.getValue(CattailBlock.CATTAILS), false)).build();
		}, BlockStateProperties.WATERLOGGED);
	}

	public ModelFile cattailStalkModel(String name, ResourceLocation texture, int stalks, boolean top) {
		String stalkSuffix = getStalkSuffix(stalks);
		ModelFile stalkParent = new UncheckedModelFile(new ResourceLocation(Environmental.MOD_ID, "block/template_cattail_stalk" + stalkSuffix));
		BlockModelBuilder builder = this.models().getBuilder(name + stalkSuffix).parent(stalkParent);
		for (int i = 1; i <= stalks; i++) {
			String suffix = getStalkSuffix(i);
			builder.texture("stalk" + suffix, top ? suffix(texture, suffix) : texture);
		}
		return builder;
	}

	public static String getStalkSuffix(int stalks) {
		return stalks == 3 ? "_three" : stalks == 2 ? "_two" : "_one";
	}

	public ModelFile cattailHeadModel(String name, ResourceLocation texture, boolean top, int stalks) {
		String stalkSuffix = stalks == 3 ? "_three" : stalks == 2 ? "_two" : "_one";
		ModelFile cattailParent = new UncheckedModelFile(new ResourceLocation(Environmental.MOD_ID, "block/template_" + (top ? "cattail_top" : "cattail") + stalkSuffix));
		return this.models().getBuilder(name + (top ? "_top" : "") + stalkSuffix).parent(cattailParent).texture("cattail", texture);
	}

	public void cactusBobble(RegistryObject<Block> cactusBobble) {
		this.simpleBlock(cactusBobble.get(), models().getBuilder(name(cactusBobble.get())).parent(new UncheckedModelFile(new ResourceLocation(Environmental.MOD_ID, "block/template_cactus_bobble"))).texture("all", blockTexture(cactusBobble.get())));
	}
}
