package palaster.libpal.client.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.libpal.core.handlers.ConfigurationHandler;
import palaster.libpal.libs.LibMod;

@SideOnly(Side.CLIENT)
public class ModGuiFactory implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraftInstance) {}

	@Override
	public boolean hasConfigGui() { return true; }

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) { return new ModGuiConfig(parentScreen); }

	@Override
	@Deprecated
	public Class<? extends GuiScreen> mainConfigGuiClass() { return ModGuiConfig.class; }

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() { return null; }

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) { return null; }
	
	public static class ModGuiConfig extends GuiConfig {

		public ModGuiConfig(GuiScreen guiScreen) {
			super(guiScreen, new ConfigElement(ConfigurationHandler.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), LibMod.MODID, true, true, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configFile.toString()));
		}
	}
}
