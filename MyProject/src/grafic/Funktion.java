package grafic;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Funktion {
	
	double start;
	
	double end;
	
	Color c;
	
	public Funktion(){}
	
	public Funktion(double s, double e, Color c){
		start = s;
		end = e;
		this.c = c;
	}
	
	public double getY(double x){
		return x;
	}
	
	public ArrayList<Point> getList(double dx){
		ArrayList<Point>points = new ArrayList<Point>();
		for (double i=start; i<end; i+=dx){
			points.add(new Point((int)i,(int) getY(i)));
		}
		return points;
	}
}
