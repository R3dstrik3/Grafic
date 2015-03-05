package editor;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.awt.Point;
import grafic.Block;
//import grafic.Point;
import grafic.Window;

public class Editor {
	Window w;

	Block cur = new Curs();

	ArrayList<Block> bloecke = new ArrayList<Block>();

	public static String savestate = "/opt/dridder_local/Documents/MyProject/src/editor/savestate";
	
	public Editor() {
		w = new Window(true);
		w.whitepixels();
		int n = JOptionPane.showConfirmDialog(null, "Moechten sie Laden?",
				"Laden", JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			load();
		}
		makeImage();
		while (w.notClosed()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (w.getMouseClick()) {
				switch (w.getMode()) {
				case "Block":
					block();
					break;
				case "Eimer":
					eimer();
					break;
				case "Hand":
					hand();
					break;
				case "Zeiger":
					zeiger();
					break;
				default:
					block();
					break;
				}
			}
			w.twtick();
		}
		n = JOptionPane.showConfirmDialog(null, "Moechten sie Speichern?",
				"Speichern?", JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			save();
		}
		System.exit(1);
	}

	private void load() {
		try (Scanner sc = new Scanner(
				new FileReader(savestate))) {
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
						Integer.parseInt(str[3]), Integer.parseInt(str[4]));
				w.load(b);
				bloecke.add(b);
			}
		} catch (IOException e) {
		}
	}

	private void save() {
		try (PrintWriter pWriter = new PrintWriter(
				new FileWriter(savestate))) {
			for (Block bl : bloecke) {
				pWriter.print(bl.getSaveString() + "|");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void makeImage() {
		try {
			w.twtick();
			cur.p.y = getMouseY();
			cur.p.x = getMouseX();
			cur.load("");
			Color[][] c = w.load(cur);
			w.makeImage();
			w.load(c, cur.p.x, cur.p.y);
		} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			w.makeImage();
		}
	}

	private void zeiger() {
		int x = getMouseX();
		int y = getMouseY();

		for (int i = bloecke.size() - 1; i >= 0; i--) {
			Block bl = bloecke.get(i);
			if (bl.inhabitate(x, y)) {
				w.twshowInfo(bl);
				i=-1;
			}
		}
	}

	private void hand() {
		int x = getMouseX();
		int y = getMouseY();

		for (int i = bloecke.size() - 1; i >= 0; i--) {
			Block bl = bloecke.get(i);
			if (bl.inhabitate(x, y)) {
				w.whitepixels();
				for (Block cr : bloecke) {
					if (!bl.equals(cr)) {
						w.load(cr);
					}
				}
				int difx = x - bl.p.x;
				int dify = y - bl.p.y;
				while (true) {
					try {
						x = getMouseX();
						y = getMouseY();
						bl.p.x = x - difx;
						bl.p.y = y - dify;
						Color[][] c = w.load(bl);
						makeImage();
						if (w.getKeyPressed().contains('')) {
							bloecke.remove(bl);
							w.whitepixels();
							for (Block t : bloecke) {
								w.load(t);
							}
							break;
						}
						if (w.getMouseClick()) {
							bloecke.add(bl);
							bloecke.remove(bl);
							break;
						}
						w.load(c, bl.p.x, bl.p.y);
					} catch (NullPointerException e) {
					}
				}
				break;
			}
		}
		makeImage();
	}

	private void eimer() {
		int x = getMouseX();
		int y = getMouseY();

		for (Block bl : bloecke) {
			if (bl.inhabitate(x, y)) {
				bl.load(w.getColor(), bl.image[0].length, bl.image.length);
			}
			w.load(bl);
		}
		makeImage();
	}

	private int getMouseY() {
		return w.getMousePosition().y;
	}

	private int getMouseX() {
		return w.getMousePosition().x;
	}

	private void block() {
		Point p1 = w.getMousePosition();
		Block b = new Block();
		while (true) {
			try {
				Color f = w.getColor();
				b.load(f, p1, w.getMousePosition());
				Color c[][] = null;
				try {
					c = w.load(b);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println();
				}
				makeImage();
				if (w.getMouseClick()) {
					bloecke.add(b);
					break;
				}
				if (c != null) {
					w.load(c, b.p.x, b.p.y);
				}
			} catch (NullPointerException e) {
				System.out.println();
			}
		}

	}

}
