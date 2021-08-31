package palaster.libpal.core.handlers;

import java.util.List;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import palaster.libpal.api.capabilities.entropy.EntropyCapability.EntropyProvider;
import palaster.libpal.api.capabilities.entropy.IEntropy;
import palaster.libpal.api.capabilities.underworld.IUnderworld;
import palaster.libpal.api.capabilities.underworld.UnderworldCapability.UnderworldProvider;
import palaster.libpal.libs.LibMod;
import palaster.libpal.souls.Soul;

public class EventHandler {
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onLivingDeath(LivingDeathEvent e) {
		if(!e.isCanceled() && e.getEntityLiving().getMobType() != CreatureAttribute.UNDEAD && e.getEntityLiving() instanceof MobEntity) {
			MobEntity mob = (MobEntity) e.getEntityLiving();
			// TODO: if(mob.isPersistenceRequired() || (mob.requiresCustomPersistence() && mob.hasCustomName()) || (!mob.removeWhenFarAway(0) && mob.hasCustomName())) {
			if(mob.hasCustomName()) {
				LazyOptional<IUnderworld> lazy_optional_underworld =  e.getEntityLiving().level.getCapability(UnderworldProvider.UNDERWORLD_CAPABILITY, null);
				IUnderworld underworld = lazy_optional_underworld.orElse(null);
				if(underworld != null) {
					Soul soul = new Soul();
					e.getEntityLiving().save(soul.entityData);
					underworld.addSoul(soul);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onWorldTick(WorldTickEvent e) {
		if(e.side.isServer() && e.phase == Phase.END && e.world != null) {
			LazyOptional<IUnderworld> lazy_optional_underworld =  e.world.getCapability(UnderworldProvider.UNDERWORLD_CAPABILITY, null);
			IUnderworld underworld = lazy_optional_underworld.orElse(null);
			if(underworld != null) {
				List<Soul> souls = underworld.getSouls();
				for(int i = souls.size() - 1; i >= 0; i--) {
					Soul soul = souls.get(i);
					if(soul.timeLeftInUnderworld <= 0) {
						souls.remove(i);
						continue;
					}
					soul.timeLeftInUnderworld--;
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent e) {
		if(e.getEntityLiving() instanceof AgeableEntity)
			if(ConfigurationHandler.COMMON.enableEntropy.get()) {
				LazyOptional<IEntropy> lazy_optional_entropy = e.getEntity().getCapability(EntropyProvider.ENTROPY_CAPABILITY, null);
				IEntropy entropy = lazy_optional_entropy.orElse(null);
				if(entropy != null)
					entropy.update();
			}
	}

	@SubscribeEvent
	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if(e.getObject() != null && e.getObject() instanceof AgeableEntity /* LivingEntity */)
			if(ConfigurationHandler.COMMON.enableEntropy.get()) {
				LazyOptional<IEntropy> lazy_optional_entropy =  e.getObject().getCapability(EntropyProvider.ENTROPY_CAPABILITY, null);
				if(lazy_optional_entropy == null || !lazy_optional_entropy.isPresent())
					e.addCapability(new ResourceLocation(LibMod.MODID, "entropy"), new EntropyProvider());
			}
	}
	
	@SubscribeEvent
	public static void attachWorldCapability(AttachCapabilitiesEvent<World> e) {
		if(ConfigurationHandler.COMMON.enableUnderworld.get() && e.getObject() != null) {
			LazyOptional<IUnderworld> lazy_optional_underworld =  e.getObject().getCapability(UnderworldProvider.UNDERWORLD_CAPABILITY, null);
			if(lazy_optional_underworld == null || !lazy_optional_underworld.isPresent())
				e.addCapability(new ResourceLocation(LibMod.MODID, "underworld"), new UnderworldProvider());
		}
	}
}
