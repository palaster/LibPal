package palaster.libpal.recipes;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ConfigCondition implements ICondition {

	public static final ArrayList<ResourceLocation> RECIPES_TO_REMOVE = new ArrayList<ResourceLocation>();
	
	private static final ResourceLocation NAME = new ResourceLocation("libpal", "config");
	private final ResourceLocation item;
	
    public ConfigCondition(String location) { this(new ResourceLocation(location)); }

    public ConfigCondition(String namespace, String path) { this(new ResourceLocation(namespace, path)); }

    public ConfigCondition(ResourceLocation item) { this.item = item; }
	
	@Override
	public ResourceLocation getID() { return NAME; }

	@Override
	public boolean test() { return !RECIPES_TO_REMOVE.contains(item); }
	
	public static class Serializer implements IConditionSerializer<ConfigCondition> {

		public static final Serializer INSTANCE = new Serializer();
		
		@Override
		public void write(JsonObject json, ConfigCondition value) { json.addProperty("result", value.item.toString()); }

		@Override
		public ConfigCondition read(JsonObject json) { return new ConfigCondition(new ResourceLocation(JSONUtils.getAsString(json, "result"))); }

		@Override
		public ResourceLocation getID() { return ConfigCondition.NAME; }
	}
}
