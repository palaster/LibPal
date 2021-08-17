package palaster.libpal.api.capabilities.underworld;

import java.util.List;

import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;
import palaster.libpal.souls.Soul;

public interface IUnderworld extends INBTSerializable<INBT> {

	void addSoul(Soul soulToBeAdded);
	
	List<Soul> getSouls();
}
