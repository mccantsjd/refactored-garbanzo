package solver;

import javax.swing.SortOrder;

import maze.Direction;
import maze.Maze;

public class Solver {
	private Maze maze;
	
	public Solver() {
		maze = Maze.getInstance();
	}
	
	public void solveMaze() {
		int count = 0;
		int totalMoves = 0;
	
		System.out.println(maze.toString());
		//starting on the right wall causes problems sometimes,
		//move left one square
		while(maze.getDroneCurrentCoordinates().getX() > 2){
			maze.turn(Direction.WEST);
			maze.moveFoward();
			System.out.println(maze.toString());
			totalMoves++;
			maze.turn(Direction.NORTH);
		}
		
		while(count<7){
		//nothing in front, go forward
		if(maze.isObjectDetected()==false){
			maze.moveFoward();
			System.out.println(maze.toString());
			totalMoves++;
			count++;
		}
		else{
			//try to go right
			maze.turn(Direction.EAST);
			if(maze.isObjectDetected()==false){
			maze.moveFoward();
			System.out.println(maze.toString());
			totalMoves++;
			}
			//try to go left
			else{
				maze.turn(Direction.WEST);
				if(maze.isObjectDetected()==false){
				maze.moveFoward();
				System.out.println(maze.toString());
				totalMoves++;
				}
				//turn around and go down 2 right 2
				else{
					maze.turn(Direction.SOUTH);
					maze.moveFoward();
					System.out.println(maze.toString());
					totalMoves++;

					maze.turn(Direction.EAST);
					maze.moveFoward();
					System.out.println(maze.toString());
					totalMoves++;

					maze.turn(Direction.SOUTH);
					maze.moveFoward();
					System.out.println(maze.toString());
					totalMoves++;

					maze.turn(Direction.EAST);
					maze.moveFoward();
					System.out.println(maze.toString());
					totalMoves++;

					count--;
					count--;
				}
			}
			maze.turn(Direction.NORTH);
		}
		
		}
		System.out.println(maze.toString());
		
		//Drone is in the last row, find goal
		while(maze.getGoalCoordinates().getX()<
		maze.getDroneCurrentCoordinates().getX()){
			maze.turn(Direction.WEST);
			maze.moveFoward();
			System.out.println(maze.toString());
			totalMoves++;
		}
		while(maze.getGoalCoordinates().getX()>
		maze.getDroneCurrentCoordinates().getX()){
			maze.turn(Direction.EAST);
			maze.moveFoward();
			System.out.println(maze.toString());
			totalMoves++;
		}

		System.out.println("Goal Found!");
		System.out.println("Total Moves: "+ totalMoves);
	}

}
