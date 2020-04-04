package maze;

public enum Direction {
	EAST("E"),
	WEST("W"),
	NORTH("N"),
	SOUTH("S");
	
	public final String Value;
	
	Direction(String val) {
		Value = val;
	}

}
