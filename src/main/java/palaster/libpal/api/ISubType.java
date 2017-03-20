package palaster.libpal.api;

public interface ISubType {
	
	default int getAmountOfSubTypes() { return getTypes().length; }
	
	String[] getTypes();
}