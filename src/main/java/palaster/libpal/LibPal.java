package palaster.libpal;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import palaster.libpal.core.proxy.CommonProxy;
import palaster.libpal.libs.LibMod;

@Mod(modid = LibMod.MODID, name = LibMod.NAME, version = LibMod.VERSION)
public class LibPal {

	@Instance(LibMod.MODID)
	public static LibPal instance;
	
	@SidedProxy(clientSide = LibMod.CLIENT, serverSide = LibMod.SERVER)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) { proxy.preInit(); }
	
	@EventHandler
	public void init(FMLInitializationEvent e) { proxy.init(); }
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) { proxy.postInit(); }
}
