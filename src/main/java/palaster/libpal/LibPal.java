package palaster.libpal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import palaster.libpal.api.capabilities.EntropyCapability.EntropyFactory;
import palaster.libpal.api.capabilities.EntropyCapability.EntropyProvider;
import palaster.libpal.api.capabilities.EntropyCapability.EntropyStorage;
import palaster.libpal.api.capabilities.IEntropy;
import palaster.libpal.core.handlers.ConfigurationHandler;
import palaster.libpal.items.ModItems;
import palaster.libpal.libs.LibMod;
import palaster.libpal.network.PacketHandler;

@Mod(LibMod.MODID)
public class LibPal {

	private static final Logger LOGGER = LogManager.getLogger();
	
	public LibPal() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.COMMON_SPEC);
		
		// Register the onCommonSetup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        // Register the onClientSetup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        // Register the onEnqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEnqueueIMC);
        // Register the onProcessIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onProcessIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void onCommonSetup(final FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(IEntropy.class, new EntropyStorage(), new EntropyFactory());
		
		PacketHandler.init();
	}

    private void onClientSetup(final FMLClientSetupEvent event) { }

    private void onEnqueueIMC(final InterModEnqueueEvent event) { }

    private void onProcessIMC(final InterModProcessEvent event) { }

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		if(e.getEntityLiving() instanceof AgeableEntity)
			if(ConfigurationHandler.COMMON.enableEntropy.get()) {
				LazyOptional<IEntropy> lazy_optional_entropy = e.getEntity().getCapability(EntropyProvider.ENTROPY_CAPABILITY, null);
				IEntropy entropy = lazy_optional_entropy.orElse(null);
				if(entropy != null)
					entropy.update();
			}
	}

	@SubscribeEvent
	public void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if(e.getObject() instanceof AgeableEntity)
			if(ConfigurationHandler.COMMON.enableEntropy.get()) {
				LazyOptional<IEntropy> lazy_optional_entropy =  e.getObject().getCapability(EntropyProvider.ENTROPY_CAPABILITY, null);
				if(e.getObject() != null && e.getObject() instanceof LivingEntity && (lazy_optional_entropy == null || !lazy_optional_entropy.isPresent()))
					e.addCapability(new ResourceLocation(LibMod.MODID, "IEntropy"), new EntropyProvider());
			}
	}
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    	@SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
        	itemRegistryEvent.getRegistry().registerAll(ModItems.STOP_CLOCK);
        }
    }
}