package palaster.libpal.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemMod extends Item {
	
	public ItemMod(ResourceLocation rl) {
		super();
		register(rl);
	}

	public void register(ResourceLocation rl) {
		setRegistryName(rl.getResourceDomain(), rl.getResourcePath());
		GameRegistry.register(this);
		super.setUnlocalizedName(rl.getResourcePath());
	}
}