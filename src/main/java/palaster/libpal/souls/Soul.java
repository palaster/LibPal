package palaster.libpal.souls;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.INBTSerializable;
import palaster.libpal.core.handlers.ConfigurationHandler;

public class Soul implements INBTSerializable<INBT> {
	private static final String NBT_TIME_LEFT_IN_UNDERWORLD = "libpal:soul:timeLeftInUnderworld",
			NBT_ENTITY_DATA = "libpal:soul:entityData"; 
	
	public int timeLeftInUnderworld = ConfigurationHandler.COMMON.timeSoulsSpendInUnderworld.get();
	public final CompoundNBT entityData = new CompoundNBT();
	
	@Override
	public INBT serializeNBT() {
		CompoundNBT compoundNBT = new CompoundNBT();
		compoundNBT.putInt(NBT_TIME_LEFT_IN_UNDERWORLD, timeLeftInUnderworld);
		compoundNBT.put(NBT_ENTITY_DATA, entityData);
		return compoundNBT;
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		if(nbt instanceof CompoundNBT) {
			CompoundNBT cNBT = (CompoundNBT) nbt;
			timeLeftInUnderworld = cNBT.getInt(NBT_TIME_LEFT_IN_UNDERWORLD);
			entityData.merge(cNBT.getCompound(NBT_ENTITY_DATA));
		}
	}
	
	@Override
	public String toString() { return "Soul: { TimeLeftInUnderWorld:" + timeLeftInUnderworld + ", CustomName:" + ITextComponent.Serializer.fromJson(entityData.getString("CustomName")).getString() + "}"; }
}
