package maze;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Maze {
	private static Maze Instance = null;
	private char[][] MazeArray;
	private Coordinate droneCurrentPosition;
	private Coordinate goalPosition;
	private Direction facedDirection;
	
	public static Maze getInstance() {
		if(Instance == null) {
			Instance = new Maze();
		}
		
		return Instance;
	}
	
	private Maze() {
		MazeArray = getMazeArray();
		droneCurrentPosition = findCharInMazeArray('D');
		goalPosition = findCharInMazeArray('G');
		facedDirection = Direction.NORTH;
	}


	private char[][] getMazeArray(){
		MazeArrays array = randomMaze();
		char[][] outer = new char[array.getRows().length][];
		for (int i = 0; i < array.getRows().length; i++) {
			Rows row = array.getRows()[i];
			char[] inner = new char[row.getSpots().length];
			for (int j = 0; j < row.getSpots().length; j++) {
				inner[j] = row.getSpots()[j].getMarker();
			}
			outer[i] = inner;
		}
		return outer;
	}
	
	private static final List<MazeArrays> VALUES = Collections.unmodifiableList(Arrays.asList(MazeArrays.values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	private static MazeArrays randomMaze()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	public Coordinate getDroneCurrentCoordinates() {
		return droneCurrentPosition;
	}
	
	public Direction getDroneDirection() {
		return facedDirection;
	}
	
	public Coordinate getGoalCoordinates() {
		return goalPosition;
	}
	
	public boolean isObjectDetected() {
		Coordinate infrontSpot = getCoordinateInfrontOfDrone();
		char infrontChar = MazeArray[infrontSpot.getY()][infrontSpot.getX()];
		return (infrontChar == 'X' || infrontChar == '|' || infrontChar == '_');
	}
	
	public void moveFoward(){
		if (isObjectDetected()) {
			System.err.println("The drone ran full speed into an impassible object and exploded! Failed solving the maze!");
			System.exit(0);
		}
		
		Coordinate infront = getCoordinateInfrontOfDrone();
		MazeArray[infront.getY()][infront.getX()] = 'D';
		MazeArray[droneCurrentPosition.getY()][droneCurrentPosition.getX()] = ' ';
		droneCurrentPosition = infront;
	}
	
	public void turn(Direction directionToTurn) {
		facedDirection = directionToTurn;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < MazeArray.length; i++) {
			for (int j = 0; j < MazeArray[i].length; j++) {
				char curChar = MazeArray[i][j];
				sb.append(" " + curChar + " ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	private Coordinate findCharInMazeArray(char findMe) {
		for(int i = 0; i < MazeArray.length; i++) {
			for(int j = 0; j < MazeArray[i].length; j++) {
				char value = MazeArray[i][j];
				
				if(value == findMe) {
					return new Coordinate(j, i);
				}
			}
		}
		
		System.err.println("Unable to find char: " + findMe + ", in MazeArray");
		System.exit(0);
		return null;
	}
	
	private Coordinate getCoordinateInfrontOfDrone() {
		int currentX = droneCurrentPosition.getX();
		int currentY = droneCurrentPosition.getY();
		int infrontX = currentX;
		int infrontY = currentY;
		
		switch(facedDirection) {
			case EAST:
				infrontX++;
				break;
			case WEST:
				infrontX--;
				break;
			case NORTH:
				infrontY--;
				break;
			case SOUTH:
				infrontY++;
				break;
		}
		
		return new Coordinate(infrontX, infrontY);
	}
	
}
