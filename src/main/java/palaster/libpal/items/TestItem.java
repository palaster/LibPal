package palaster.libpal.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import palaster.libpal.api.capabilities.underworld.IUnderworld;
import palaster.libpal.api.capabilities.underworld.UnderworldCapability.UnderworldProvider;
import palaster.libpal.souls.Soul;

public class TestItem extends SpecialModItem {
	public TestItem(Properties properties, ResourceLocation resourceLocation) { super(properties.tab(ItemGroup.TAB_MISC), resourceLocation, 0); }

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack stack = playerEntity.getItemInHand(hand);
		if(!world.isClientSide) {
			LazyOptional<IUnderworld> lazy_optional_underworld = world.getCapability(UnderworldProvider.UNDERWORLD_CAPABILITY, null);
			IUnderworld underworld = lazy_optional_underworld.orElse(null);
			if(underworld != null) {
				if(underworld.getSouls().size() > 0) {
					for(Soul soul : underworld.getSouls())
						if(soul != null)
							playerEntity.sendMessage(new StringTextComponent(soul.toString()), playerEntity.getUUID());
				} else
					playerEntity.sendMessage(new StringTextComponent("Underworld is empty"), playerEntity.getUUID());
			} else
				playerEntity.sendMessage(new StringTextComponent("Sorry the underworld doesn't exist"), playerEntity.getUUID());
		}
		return ActionResult.success(stack);
	}
}
