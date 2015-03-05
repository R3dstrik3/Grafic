package grafic;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Spieler extends Block {

	Movement m;

	public Spieler() {
		p = new Point(100, 500);
		load(Color.blue, 100, 100);
		m = new Movement(0, 1);
	}

	public void tick(ArrayList<Character> keys) {
		move();
		
		if (keys.contains('w')){
			if (m.dy == 0){
				m.dy = -20;
			}
		}
		if (keys.contains('d')){
			m.dx = 5;
		}else if(keys.contains('a')){
			m.dx = -5;
		}else{
			m.dx = 0;
		}
		m.dy++;
	}

	public void move() {
		p.x += m.dx;
		p.y += m.dy;
	}
	
	public String toString(){
		String str = "";
		str += "Spieler: p("+p.x+"/"+p.y+")"+" m("+m.dx+"/"+m.dy+")";
		return str;
	}
}
