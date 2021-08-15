package palaster.libpal.items;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.registries.ObjectHolder;
import palaster.libpal.libs.LibMod;

@ObjectHolder(LibMod.MODID)
public final class ModItems {
    public static final Item STOP_CLOCK = new StopClockItem(new Properties());
}
