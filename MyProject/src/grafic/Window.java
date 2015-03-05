package grafic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import editor.MyMouseListener;
import editor.ToolWindow;

public class Window extends JFrame {
	Color[][] pixels;

	private static final long serialVersionUID = 1L;

	JPanel panel;

	JLabel label;

	ImageIcon icon;

	BufferedImage image;

	ColorModel model;

	MyKeys key;

	MyMouseListener mouse;

	private int alpha = 300;

	ToolWindow tw;

	MyWindowListener wl;

	public Window(boolean editor) {
		int height = alpha * 3, width = alpha * 4;
		pixels = new Color[width][height];
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		model = image.getColorModel();
		whitepixels();
		
		icon = new ImageIcon(image);
		label = new JLabel(icon);
		label.setIcon(icon);
		label.setBorder(new LineBorder(Color.DARK_GRAY));

		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.DARK_GRAY);
		panel.add(label, BorderLayout.CENTER);
		
		key = new MyKeys();
		addKeyListener(key);
		mouse = new MyMouseListener();
		addMouseListener(mouse);
		wl = new MyWindowListener();
		addWindowListener(wl);
		getContentPane().add(panel);
		setSize(width, height);
		setLocationRelativeTo(null);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Editor");
		setVisible(true);
		if (editor) {
			tw = new ToolWindow(this);
		}
	}
	
	public java.awt.Point getMousePosition(){
		java.awt.Point p = super.getMousePosition();
		if (p == null){
			return p;
		}
		Dimension d = getSize();
		p.x -= (d.width-1200)/2;
		p.y -= (15+(d.height-900)/2);
		return p;
	}
	
	public void twtick(){
		tw.tick();
	}
	
	public boolean notClosed() {
		return wl.notclosed;
	}

	public String getMode() {
		return tw.getMode();
	}

	public Color getColor() {
		return tw.getFarben();
	}

	public boolean getMouseClick() {
		boolean result = mouse.getClicked();
		mouse.setClicked(false);
		return result;
	}

	public ArrayList<Character> getKeyPressed() {
		return key.keys;
	}

	public Color[][] load(Color[][] piyels, int x, int y) {
		int startx = x;
		int starty = y;
		if (startx < 0) {
			startx = 0;
		}
		if (starty < 0) {
			starty = 0;
		}
		Color[][] deleted = new Color[piyels.length][piyels[0].length];
		for (int i = startx; i < piyels.length + x && i < 1200; i++) {
			for (int j = starty; j < piyels[0].length + y && j < 900; j++) {
				deleted[i - x][j - y] = this.pixels[i][j];
				if (piyels[i - x][j - y] == null) {
					continue;
				}
				this.pixels[i][j] = piyels[i - x][j - y];
			}
		}
		return deleted;
	}

	public ArrayList<Color> load(Funktion f) {
		ArrayList<Color> deleted = new ArrayList<Color>();
		for (Point p : f.getList(0.01)) {
			if (p.x >= 1200 || p.y >= 900 || p.x < 0 || p.y < 0) {
				continue;
			}
			deleted.add(pixels[p.x][p.y]);
			pixels[p.x][p.y] = f.c;
			try {
				deleted.add(pixels[p.x][p.y + 1]);
				pixels[p.x][p.y + 1] = f.c;
			} catch (Exception e) {
			}
			try {
				deleted.add(pixels[p.x][p.y - 1]);
				pixels[p.x][p.y - 1] = f.c;
			} catch (Exception e) {
			}
		}
		return deleted;
	}

	public ArrayList<Color> load(Funktion f, ArrayList<Color> c) {
		ArrayList<Color> deleted = new ArrayList<Color>();
		int i = 0;
		for (Point p : f.getList(0.1)) {
			deleted.add(pixels[p.x][p.y]);
			pixels[p.x][p.y] = c.get(i++);
		}
		return deleted;
	}

	public Color[][] load(Block c) {
		int x = c.p.x;
		int y = c.p.y;
		Color[][] pixels = c.image;
		return load(pixels, x, y);
	}

	public void load(Point p1, Point p2) {
		double j = p1.y;
		double dy = ((double) p2.y - p1.y) / ((double) p2.x - p1.x);
		for (double i = p1.x; i < p2.x; i += 1) {
			pixels[(int) i][(int) j] = Color.RED;
			j += dy;
		}
	}

	// public void loadraster(BufferedImage r){
	// image = r;
	// icon = new ImageIcon(image);
	// label = new JLabel(icon);
	// label.setIcon(icon);
	// label.setBorder(new LineBorder(Color.DARK_GRAY));
	// panel.add(label, BorderLayout.CENTER);
	// repaint();
	// WritableRaster raster = image.getRaster();
	// //for (int i=0; i<raster.getWidth(); i++){
	// double[] d = new double[3];
	// raster.getPixel(0, 0, d);
	// System.out.println(d[0]);
	// }

	// public void load(int x1, int y1, int x2, int y2, Color c) {
	// for (int i = y1; i < y2; i++) {
	// for (int j = x1; j < x2; j++) {
	// this.pixels[i][j] = c;
	// }
	// }
	// }

	public void makeImage() {
		int height = alpha * 3, width = alpha * 4;
		WritableRaster raster = image.getRaster();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (pixels[i][j] == null) {
					continue;
				}
				raster.setDataElements(i, j, getObject(pixels[i][j]));
			}
		}
		repaint();
	}

	public void whitepixels() {
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				pixels[i][j] = Color.WHITE;
			}
		}
	}

	public Object getObject(Color c) {
		int argb = c.getRGB();
		return model.getDataElements(argb, null);
	}


	public void twshowInfo(Block bl) {
		tw.shoInfo(bl);
	}
}
