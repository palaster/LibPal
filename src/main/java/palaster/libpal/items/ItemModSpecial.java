package palaster.libpal.items;

import net.minecraft.util.ResourceLocation;

public class ItemModSpecial extends ItemMod {

	public ItemModSpecial(ResourceLocation rl) { this(rl, 0, 1); }

	public ItemModSpecial(ResourceLocation rl, int maxDamage) { this(rl, maxDamage, 1); }
	
	public ItemModSpecial(ResourceLocation rl, int maxDamage, int maxStackSize) {
		super(rl);
		setMaxDamage(maxDamage);
		setMaxStackSize(maxStackSize <= 0 ? 1 : maxStackSize);
	}
}
