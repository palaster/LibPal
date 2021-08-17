package palaster.libpal.items;

import net.minecraft.util.ResourceLocation;

public class SpecialModItem extends ModItem {
	public SpecialModItem(Properties properties, ResourceLocation resourceLocation, int maxDamage) { super(properties.durability(maxDamage), resourceLocation); }
}
