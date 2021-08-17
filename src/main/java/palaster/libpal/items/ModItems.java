package palaster.libpal.items;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;
import palaster.libpal.libs.LibMod;

@ObjectHolder(LibMod.MODID)
public final class ModItems {
    public static final Item STOP_CLOCK = new StopClockItem(new Properties(), new ResourceLocation(LibMod.MODID, "stop_clock")),
    		SOUL_INFUSED_EGG = new SoulInfusedEggItem(new Properties(), new ResourceLocation(LibMod.MODID, "soul_infused_egg")),
    		TEST = new TestItem(new Properties(), new ResourceLocation(LibMod.MODID, "test"));
}
