package palaster.libpal.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import palaster.libpal.libs.LibMod;

@ObjectHolder(LibMod.MODID)
public class LPItems {

    public static final Item STOP_CLOCK = new ItemStopClock();
}
