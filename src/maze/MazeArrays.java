package maze;

import java.util.List;
import java.util.Random;

enum Spots {
	BORDER('_'),
	GOAL('G'),
	OBS('X'),
	DPOS('D'),
	SIDE('|'),
	SPACE(' ');
	
	private final char Marker;
	
	Spots(char marker){
		Marker = marker;
	}
	
	public char getMarker() {
		return Marker;
	}
}

enum Rows {
	BORDER(Spots.BORDER, Spots.BORDER, Spots.BORDER, Spots.BORDER, Spots.BORDER, Spots.BORDER),
	GOAL1(Spots.SIDE, Spots.GOAL, Spots.SPACE, Spots.SPACE, Spots.SPACE, Spots.SIDE),
	GOAL2(Spots.SIDE, Spots.SPACE, Spots.GOAL, Spots.SPACE, Spots.SPACE, Spots.SIDE),
	GOAL3(Spots.SIDE, Spots.SPACE, Spots.SPACE, Spots.GOAL, Spots.SPACE, Spots.SIDE),
	GOAL4(Spots.SIDE, Spots.SPACE, Spots.SPACE, Spots.SPACE, Spots.GOAL, Spots.SIDE),
	OBS1(Spots.SIDE, Spots.OBS, Spots.SPACE, Spots.SPACE, Spots.SPACE, Spots.SIDE),
	OBS2(Spots.SIDE, Spots.SPACE, Spots.OBS, Spots.SPACE, Spots.SPACE, Spots.SIDE),
	OBS3(Spots.SIDE, Spots.SPACE, Spots.SPACE, Spots.OBS, Spots.SPACE, Spots.SIDE),
	OBS4(Spots.SIDE, Spots.SPACE, Spots.SPACE, Spots.SPACE, Spots.OBS, Spots.SIDE),
	DPOS1(Spots.SIDE, Spots.DPOS, Spots.SPACE, Spots.SPACE, Spots.SPACE, Spots.SIDE),
	DPOS2(Spots.SIDE, Spots.SPACE, Spots.DPOS, Spots.SPACE, Spots.SPACE, Spots.SIDE),
	DPOS3(Spots.SIDE, Spots.SPACE, Spots.SPACE, Spots.DPOS, Spots.SPACE, Spots.SIDE),
	DPOS4(Spots.SIDE, Spots.SPACE, Spots.SPACE, Spots.SPACE, Spots.DPOS, Spots.SIDE);

	private final Spots[] spots;
	
	Rows(Spots... spot) {
		spots = spot;
	}

    public Spots[] getSpots() {
        return spots.clone();
    }
}

public enum MazeArrays {
	A(Rows.BORDER, Rows.GOAL1, Rows.OBS1, Rows.OBS2, Rows.OBS3, Rows.OBS1, Rows.OBS2, Rows.OBS3, Rows.DPOS1, Rows.BORDER),
	B(Rows.BORDER, Rows.GOAL4, Rows.OBS4, Rows.OBS2, Rows.OBS3, Rows.OBS1, Rows.OBS2, Rows.OBS3, Rows.DPOS2, Rows.BORDER),
	C(Rows.BORDER, Rows.GOAL2, Rows.OBS2, Rows.OBS2, Rows.OBS2, Rows.OBS4, Rows.OBS4, Rows.OBS3, Rows.DPOS3, Rows.BORDER),
	D(Rows.BORDER, Rows.GOAL3, Rows.OBS4, Rows.OBS3, Rows.OBS2, Rows.OBS4, Rows.OBS2, Rows.OBS3, Rows.DPOS4, Rows.BORDER),
	E(Rows.BORDER, Rows.GOAL4, Rows.OBS3, Rows.OBS4, Rows.OBS2, Rows.OBS1, Rows.OBS2, Rows.OBS3, Rows.DPOS1, Rows.BORDER),
	F(Rows.BORDER, Rows.GOAL3, Rows.OBS1, Rows.OBS3, Rows.OBS3, Rows.OBS1, Rows.OBS2, Rows.OBS4, Rows.DPOS2, Rows.BORDER),
	G(Rows.BORDER, Rows.GOAL2, Rows.OBS1, Rows.OBS2, Rows.OBS3, Rows.OBS2, Rows.OBS1, Rows.OBS3, Rows.DPOS3, Rows.BORDER),
	H(Rows.BORDER, Rows.GOAL1, Rows.OBS2, Rows.OBS3, Rows.OBS4, Rows.OBS1, Rows.OBS2, Rows.OBS3, Rows.DPOS4, Rows.BORDER);
	
	private Rows[] rows;
	
	MazeArrays(Rows... row){
		rows = row;
	}

    public Rows[] getRows() {
        return rows.clone();
    }
}
