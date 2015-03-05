package grafic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MyKeys implements KeyListener{
	
	ArrayList<Character> keys = new ArrayList<Character>();
	
	public void keyPressed(KeyEvent e) {
		Character c = new Character(e.getKeyChar());
		if (! keys.contains(c)){
			keys.add(c);
		}
	}

	public void keyReleased(KeyEvent e) {
		Character c = new Character(e.getKeyChar());
		if (keys.contains(c)){
			keys.remove(c);
		}
	}

	public void keyTyped(KeyEvent e) {
	}
	
	public int getpos(int i){		
		int index = 0;
		for (int key :keys){
			if (key == i){
				return index;
			}
			index++;
		}
		return -1;
	}
}
