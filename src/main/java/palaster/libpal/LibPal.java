package palaster.libpal;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import palaster.libpal.api.capabilities.entropy.EntropyCapability.EntropyFactory;
import palaster.libpal.api.capabilities.entropy.EntropyCapability.EntropyStorage;
import palaster.libpal.api.capabilities.entropy.IEntropy;
import palaster.libpal.api.capabilities.underworld.IUnderworld;
import palaster.libpal.api.capabilities.underworld.UnderworldCapability.UnderworldFactory;
import palaster.libpal.api.capabilities.underworld.UnderworldCapability.UnderworldStorage;
import palaster.libpal.core.handlers.ConfigurationHandler;
import palaster.libpal.core.handlers.EventHandler;
import palaster.libpal.items.ModItems;
import palaster.libpal.libs.LibMod;
import palaster.libpal.network.PacketHandler;
import palaster.libpal.recipes.ConfigCondition;

@Mod(LibMod.MODID)
public class LibPal {

	public LibPal() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.COMMON_SPEC);
		
		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		// Register the onCommonSetup method for modloading
        eventBus.addListener(this::onCommonSetup);
        // Register the onClientSetup method for modloading
        eventBus.addListener(this::onClientSetup);
        // Register the onLoadComplete method for modloading
        eventBus.addListener(this::onLoadComplete);
        // Register the onEnqueueIMC method for modloading
        eventBus.addListener(this::onEnqueueIMC);
        // Register the onProcessIMC method for modloading
        eventBus.addListener(this::onProcessIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
	}
	
	private void onCommonSetup(final FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(IEntropy.class, new EntropyStorage(), new EntropyFactory());
		CapabilityManager.INSTANCE.register(IUnderworld.class, new UnderworldStorage(), new UnderworldFactory());
		
		PacketHandler.init();
		
		event.enqueueWork(() -> {
			if(!ConfigurationHandler.COMMON.enableUnderworld.get())
				ConfigCondition.RECIPES_TO_REMOVE.add(ModItems.SOUL_INFUSED_EGG.getRegistryName());
		});
	}

    private void onClientSetup(final FMLClientSetupEvent event) { }
    
    private void onLoadComplete(final FMLLoadCompleteEvent event) { }

    private void onEnqueueIMC(final InterModEnqueueEvent event) { }

    private void onProcessIMC(final InterModProcessEvent event) { }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    	@SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
        	itemRegistryEvent.getRegistry().registerAll(ModItems.STOP_CLOCK,
        			ModItems.SOUL_INFUSED_EGG,
        			ModItems.TEST);
        }
    	
    	@SubscribeEvent
        public static void onRecipeSerializerRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> recipeSerializerRegistryEvent) {
        	CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);
        }
    }
}