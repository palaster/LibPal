package palaster.libpal.core.handlers;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.libpal.api.IModObject;
import palaster.libpal.api.ISubType;
import palaster.libpal.api.capabilities.EntropyCapability.EntropyCapabilityProvider;
import palaster.libpal.api.capabilities.IEntropy;
import palaster.libpal.items.LPItemBlock;
import palaster.libpal.items.LPItems;
import palaster.libpal.libs.LibMod;

@Mod.EventBusSubscriber(modid = LibMod.MODID)
public class EventHandler {

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		if(e.getEntityLiving() instanceof EntityLiving)
			if(ConfigurationHandler.enableEntropy) {
				IEntropy entropy = EntropyCapabilityProvider.get((EntityLiving) e.getEntityLiving());
				if(entropy != null)
					entropy.update((EntityLiving) e.getEntityLiving());
			}
	}

	@SubscribeEvent
	public void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if(ConfigurationHandler.enableEntropy)
			if(e.getObject() != null && e.getObject() instanceof EntityLiving && !e.getObject().hasCapability(EntropyCapabilityProvider.ENTROPY_CAP, null))
				e.addCapability(new ResourceLocation(LibMod.MODID, "IEntropy"), new EntropyCapabilityProvider((EntityLiving) e.getObject()));
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(LPItems.STOP_CLOCK);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent e) {
		for(Item item : Item.REGISTRY)
			if(item instanceof IModObject || item instanceof LPItemBlock) {
				if(item instanceof ISubType)
					for(int i = 0; i < ((ISubType) item).getAmountOfSubTypes(); i++)
						ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName().getResourceDomain() + ":" + ((ISubType) item).getTypes()[i], "inventory"));
				else
					ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
	}
}