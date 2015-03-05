package grafic;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Buchstabe {
	ArrayList<Funktion> f;
	
	Point anfang;
	
	Point ende;
	
	public Buchstabe(char c, Point a, Point e){
		anfang = a;
		ende = e;
		f = new ArrayList<Funktion>();
		switch(c){
		case 'x': loadx();break;
		}
	}
	
	public void loadx(){
		f.add(new Funktion(anfang.x,ende.x,Color.BLACK){
			public double getY(double x){
				double gr = ende.y-anfang.y;
				double we = ende.x-anfang.x;
				return 2*gr*x/we  +anfang.y;
			}
		});
		f.add(new Funktion(anfang.x,ende.x,Color.BLACK){
			public double getY(double x){
				double gr = ende.y-anfang.y;
				double we = ende.x-anfang.x;
				return -2*gr*x/we +ende.y;
			}
		});
	}
}
