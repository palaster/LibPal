package palaster.libpal.core.handlers;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import palaster.libpal.items.ModItems;
import palaster.libpal.libs.LibMod;

@Mod.EventBusSubscriber(modid = LibMod.MODID)
public class RecipeRemoverHandler {
	
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Field RECIPES = ObfuscationReflectionHelper.findField(RecipeManager.class,  /* recipes */ "field_199522_d");
	
	@SubscribeEvent
	public static void removeRecipes(final FMLServerStartedEvent event) {
		final RecipeManager recipeManager = event.getServer().getRecipeManager();
		if(!ConfigurationHandler.COMMON.enableUnderworld.get())
			removeRecipes(recipeManager, ModItems.SOUL_INFUSED_EGG);
	}
	
	/**
	 * Removes all crafting recipes with an output item contained in the specified tag.
	 *
	 * @param recipeManager The recipe manager
	 * @param tag           The tag
	 */
	private static void removeRecipes(final RecipeManager recipeManager, final Item item) {
		final int recipesRemoved = removeRecipes(recipeManager, recipe -> {
			final ItemStack resultItem = recipe.getResultItem();
			return !resultItem.isEmpty() && resultItem.getItem().equals(item);
		});

		LOGGER.info("Removed {} recipe(s) for tag {}", recipesRemoved, item.getRegistryName().toString());
	}

	/**
	 * Remove all crafting recipes that match the specified predicate.
	 *
	 * @param recipeManager The recipe manager
	 * @param predicate     The predicate
	 * @return The number of recipes removed
	 */
	private static int removeRecipes(final RecipeManager recipeManager, final Predicate<IRecipe<?>> predicate) {
		final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> existingRecipes;
		try {
			@SuppressWarnings("unchecked")
			final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesMap = (Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>>) RECIPES.get(recipeManager);
			existingRecipes = recipesMap;
		} catch (final IllegalAccessException e) {
			throw new RuntimeException("Couldn't get recipes map while removing recipes", e);
		}

		final Object2IntMap<IRecipeType<?>> removedCounts = new Object2IntOpenHashMap<>();
		final ImmutableMap.Builder<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> newRecipes = ImmutableMap.builder();

		// For each recipe type, create a new map that doesn't contain the recipes to be removed
		existingRecipes.forEach((recipeType, existingRecipesForType) -> {
			//noinspection UnstableApiUsage
			final ImmutableMap<ResourceLocation, IRecipe<?>> newRecipesForType = existingRecipesForType.entrySet()
					.stream()
					.filter(entry -> !predicate.test(entry.getValue()))
					.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));

			removedCounts.put(recipeType, existingRecipesForType.size() - newRecipesForType.size());
			newRecipes.put(recipeType, newRecipesForType);
		});

		final int removedCount = removedCounts.values().stream().reduce(0, Integer::sum);

		try {
			RECIPES.set(recipeManager, newRecipes.build());
		} catch (final IllegalAccessException e) {
			throw new RuntimeException("Couldn't replace recipes map while removing recipes", e);
		}

		return removedCount;
	}
}
