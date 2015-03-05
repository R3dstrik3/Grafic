package grafic;

import java.awt.Color;
import java.awt.Point;

public class Block {
	public Color[][] image;

	public Point p;

	public void load(Color c, int x, int y) {
		image = new Color[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				image[i][j] = c;
			}
		}
	}

	public void load(Color c, Point p1, Point p2) {
		int xmin = 1000000;
		int xmax = -1;
		int ymin = 1000000;
		int ymax = -1;

		if (p1.x < p2.x) {
			xmin = p1.x;
			xmax = p2.x;
		} else {
			xmin = p2.x;
			xmax = p1.x;
		}
		if (p1.y < p2.y) {
			ymin = p1.y;
			ymax = p2.y;
		} else {
			ymin = p2.y;
			ymax = p1.y;
		}

		p = new Point(xmin, ymin);

		load(c, xmax - xmin, ymax - ymin);
	}

	public void load(String path) {

	}

	public boolean collide(Point p, Movement m) {
		Point c = new Point(p);
		m.move(c);
		Movement.sub(c, this.p);
		if (c.x > 0 && c.y > 0 && c.x < image.length && c.y < image[0].length) {
			return true;
		}
		return false;
	}

	public int getFlaeche() {
		return image.length * image[0].length;
	}

	public void collide(Spieler sp) {
		int width = sp.image.length;
		int height = sp.image[0].length;
		Point as = sp.p;
		Point bs = new Point(as.x + width, as.y);
		Point cs = new Point(as.x, as.y + height);
		Point ds = new Point(bs.x, cs.y);
		Point ab = p;
		Point bb = new Point(ab.x + image.length, ab.y);
		Point cb = new Point(ab.x, ab.y + image[0].length);
		Point db = new Point(bb.x, cb.y);
		Movement mx = new Movement(sp.m.dx, 0);
		Movement my = new Movement(0, sp.m.dy);

		if (mx.dx < 0) {
			if (collide(as, mx) || collide(cs, mx)) {
				sp.m.dx = 0;
				sp.p.x = p.x + image.length;
			}
		} else if (mx.dx > 0) {
			if (collide(bs, mx) || collide(ds, mx)) {
				sp.m.dx = 0;
				sp.p.x = p.x - sp.image.length;
			}
		}

		if (my.dy < 0) {
			if (collide(as, my) || collide(bs, my)) {
				sp.m.dy = 0;
				sp.p.y = p.y + image[0].length;
			}
		} else if (my.dy > 0) {
			if (collide(cs, my) || collide(ds, my)) {
				sp.m.dy = 0;
				sp.p.y = p.y - sp.image[0].length;
			}
		}
	}

	public boolean inhabitate(int x, int y) {
		if (x >= p.x && y >= p.y && x <= image.length + p.x
				&& y <= image[0].length + p.y) {
			return true;
		}
		return false;
	}

	public String getSaveString() {
		String str = "";
		Color c = image[0][0];
		str += p.x + ";" + p.y + ";" + c.getRed() + "," + c.getGreen() + ","
				+ c.getBlue() + ";" + image.length + ";" + image[0].length;
		return str;
	}

	public String toString() {
		String str = "";
		str += "block: p1(" + p.x + "/" + p.y + ") + p2("
				+ (p.x + image.length) + "/" + (p.y + image[0].length) + ")";
		;
		return str;
	}
}
