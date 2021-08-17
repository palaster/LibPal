package palaster.libpal.core.handlers;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigurationHandler {

	public static class Common {
		public final ForgeConfigSpec.BooleanValue enableEntropy;
		public final ForgeConfigSpec.BooleanValue enableUnderworld;
		public final ForgeConfigSpec.ConfigValue<Integer> timeSoulsSpendInUnderworld;
		
		public Common(ForgeConfigSpec.Builder builder) {
			enableEntropy = builder
					.comment("Set this to true to enable entropy")
					.define("entropy", false);
			enableUnderworld = builder
					.comment("Set this to false to disable the underworlds")
					.define("underworld", true);
			timeSoulsSpendInUnderworld = builder
					.comment("Amount of time souls spend in the underworld (in ticks)")
					.define("timeSoulsSpendInUnderworld", 168000);
		}
	}
	
	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}