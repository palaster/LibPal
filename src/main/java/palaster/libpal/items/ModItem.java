package palaster.libpal.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ModItem extends Item {
	public ModItem(Properties properties, ResourceLocation resourceLocation) {
		super(properties);
		setRegistryName(resourceLocation);
	}
}