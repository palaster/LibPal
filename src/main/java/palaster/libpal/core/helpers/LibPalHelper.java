package palaster.libpal.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class LibPalHelper {

	public static void setCreativeTab(String modid, CreativeTabs ct) {
		for(Block block : Block.REGISTRY)
			if(block.getRegistryName().getResourceDomain().equalsIgnoreCase(modid))
				block.setCreativeTab(ct);
		for(Item item : Item.REGISTRY)
			if(item.getRegistryName().getResourceDomain().equalsIgnoreCase(modid))
				item.setCreativeTab(ct);
	}
}