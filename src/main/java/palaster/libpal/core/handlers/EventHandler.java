package palaster.libpal.core.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.libpal.api.IModObject;
import palaster.libpal.api.ISubType;
import palaster.libpal.libs.LibMod;

@Mod.EventBusSubscriber(modid = LibMod.MODID)
public class EventHandler {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent e) {
		for(Block block : Block.REGISTRY)
			if(block instanceof IModObject) {
				Item item = Item.getItemFromBlock(block);
				if(item instanceof ISubType)
					for(int i = 0; i < ((ISubType) item).getAmountOfSubTypes(); i++)
						ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(block.getRegistryName().getResourceDomain() + ":" + ((ISubType) item).getTypes()[i], "inventory"));
				else
					ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.getRegistryName(), "inventory" ));
			}
		for(Item item : Item.REGISTRY)
			if(item instanceof IModObject) {
				if(item instanceof ISubType)
					for(int i = 0; i < ((ISubType) item).getAmountOfSubTypes(); i++)
						ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName().getResourceDomain() + ":" + ((ISubType) item).getTypes()[i], "inventory"));
				else
					ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
	}
}