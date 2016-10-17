package palaster.libpal.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.libpal.libs.LibMod;

public class LibPalItems {

	@SideOnly(Side.CLIENT)
	public static void registerCustomModelResourceLocation() {
		for(Item item : Item.REGISTRY)
			if(item instanceof ItemMod && item.getRegistryName().getResourceDomain().equalsIgnoreCase(LibMod.MODID))
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
