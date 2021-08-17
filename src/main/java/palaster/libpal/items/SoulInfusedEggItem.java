package palaster.libpal.items;

import java.util.Optional;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import palaster.libpal.api.capabilities.underworld.IUnderworld;
import palaster.libpal.api.capabilities.underworld.UnderworldCapability.UnderworldProvider;

public class SoulInfusedEggItem extends SpecialModItem {
	public SoulInfusedEggItem(Properties properties, ResourceLocation resourceLocation) { super(properties.tab(ItemGroup.TAB_MISC), resourceLocation, 0); }
	
	@Override
	public ActionResultType useOn(ItemUseContext itemUseContext) {
		if(!itemUseContext.getLevel().isClientSide) {
			LazyOptional<IUnderworld> lazy_optional_underworld = itemUseContext.getLevel().getCapability(UnderworldProvider.UNDERWORLD_CAPABILITY, null);
			IUnderworld underworld = lazy_optional_underworld.orElse(null);
			if(underworld != null) {
				CompoundNBT compoundNBT = itemUseContext.getItemInHand().getTagElement("display");
				if(compoundNBT != null && compoundNBT.contains("Name", 8)) {
					UUID soulUUID = null;
					try {
						soulUUID = UUID.fromString(ITextComponent.Serializer.fromJson(compoundNBT.getString("Name")).getString());
					} catch(Exception e) {}
					for(int i = underworld.getSouls().size() - 1; i >= 0; i--) {
						if(underworld.getSouls().get(i) != null) {
							CompoundNBT entityData = underworld.getSouls().get(i).entityData;
							if(soulUUID != null && entityData.hasUUID("UUID") && entityData.getUUID("UUID").equals(soulUUID) || entityData.getString("CustomName").equals(compoundNBT.getString("Name"))) {
								Optional<Entity> optional_resurrected = EntityType.create(entityData, itemUseContext.getLevel());
								Entity resurrected = optional_resurrected.orElse(null);
								if(resurrected != null) {
									resurrected.moveTo(itemUseContext.getClickLocation());
									if(resurrected instanceof MobEntity) {
										MobEntity mob_resurrected = (MobEntity) resurrected;
										mob_resurrected.setHealth(1.0f);
									}
									itemUseContext.getLevel().addFreshEntity(resurrected);
								}
								itemUseContext.getItemInHand().shrink(1);
								underworld.getSouls().remove(i);
								return ActionResultType.SUCCESS;
							}
						}
					}
				}
				itemUseContext.getPlayer().sendMessage(new StringTextComponent("This soul has passed or is invalid"), itemUseContext.getPlayer().getUUID());
			} else
				itemUseContext.getPlayer().sendMessage(new StringTextComponent("Sorry the underworld doesn't exist"), itemUseContext.getPlayer().getUUID());
		}
		return super.useOn(itemUseContext);
	}
}
