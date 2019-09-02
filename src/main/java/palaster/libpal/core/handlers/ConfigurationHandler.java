package palaster.libpal.core.handlers;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import palaster.libpal.libs.LibMod;

public class ConfigurationHandler {

	public static Configuration configFile = null;
	
	public static boolean enableEntropy = true;
	
	public ConfigurationHandler(Configuration config) {
		MinecraftForge.EVENT_BUS.register(this);
		configFile = config;
		sync();
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equals(LibMod.MODID))
			sync();
	}
	
	public static void sync() {
		enableEntropy = configFile.getBoolean("Enable Mob Entropy", Configuration.CATEGORY_GENERAL, false, "");

		if(configFile.hasChanged())
			configFile.save();
	}
}