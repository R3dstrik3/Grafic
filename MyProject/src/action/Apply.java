package action;

import editor.Editor;
import grafic.Block;
import grafic.Spieler;
import grafic.Window;

import java.awt.Color;
import java.awt.Point;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Apply {

	Spieler sp;

	ArrayList<Block> bloecke = new ArrayList<Block>();

	Window w;
	
	double lasttime;
	
	public Apply() {
		w = new Window(false);
		w.whitepixels();
		int n = JOptionPane.showConfirmDialog(null, "Moechten sie Laden?",
				"Laden", JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			load();
		}
		sp = new Spieler();
		Color[][] c = w.load(sp);
		w.makeImage();
		w.load(c, sp.p.x, sp.p.y);
		while (w.notClosed()) {
			lasttime = (double)System.currentTimeMillis();
			sp.tick(w.getKeyPressed());
			for (Block bl : bloecke) {
				bl.collide(sp);
			}
			c = w.load(sp);
			w.makeImage();
			w.load(c, sp.p.x, sp.p.y);
			fps();
		}
		System.exit(1);
	}

	private void fps(){
		double now = (double)System.currentTimeMillis();
		while (now-lasttime<33.3){
			now = System.currentTimeMillis();
		}
	}
	
	private void load() {
		try (Scanner sc = new Scanner(
				new FileReader(Editor.savestate))) {
			String[] objekte = sc.next().split("\\|");
			for (int j = 0; j < objekte.length; j++) {
				String obj = objekte[j];
				String[] str = obj.split(";");
				Block b = new Block();
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				b.p = new Point(x, y);
				String RGB[] = str[2].split(",");
				int rgb[] = new int[3];
				for (int i = 0; i < 3; i++) {
					rgb[i] = Integer.parseInt(RGB[i]);
				}
				b.load(new Color(rgb[0], rgb[1], rgb[2]),
						Integer.parseInt(str[4]), Integer.parseInt(str[3]));
				w.load(b);
				w.makeImage();
				bloecke.add(b);
			}
		} catch (IOException e) {
		}
	}
}
