package palaster.libpal.items;

public class ItemModSpecial extends ItemMod {

	public ItemModSpecial() { this(0, 1); }

	public ItemModSpecial(int maxDamage) { this(maxDamage, 1); }
	
	public ItemModSpecial(int maxDamage, int maxStackSize) {
		super();
		setMaxDamage(maxDamage);
		setMaxStackSize(maxStackSize <= 0 ? 1 : maxStackSize);
	}
}
