package grafic;

import java.awt.Point;

public class Movement {
	public Movement(int i, int j) {
		dx = i;
		dy = j;
	}

	int dx;
	
	int dy;
	
	public void move(Point p){
		p.x += dx;
		p.y += dy;
	}

	public static void sub(Point p, Point p2) {
		p.x -= p2.x;
		p.y -= p2.y;
	}
}
