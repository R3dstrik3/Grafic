package editor;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import grafic.Block;
import grafic.Window;

public class ToolWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel;

	MyMouseListener mouse;

	JColorChooser jch;

	ArrayList<JButton> buttons = new ArrayList<JButton>();

	String mode = "Block";

	JLabel current = new JLabel("Current Moder: Block");

	JLabel mcurrent = new JLabel("");
	
	JLabel ocurrent = new JLabel("");
	
	Block oc;
	
	Window w;
	
	public Color getFarben() {
		return jch.getColor();
	}

	public String getMode() {
		return mode;
	}

	public ToolWindow(Window w) {
		this.w = w;
		jch = new JColorChooser();
		jch.setBounds(100, 100, 500, 500);
		jch.setVisible(true);

		buttons.add(new JButton("Eimer"));
		buttons.get(0).addActionListener(this);
		buttons.add(new JButton("Block"));
		buttons.get(1).addActionListener(this);
		buttons.add(new JButton("Hand"));
		buttons.get(2).addActionListener(this);
		buttons.add(new JButton("Zeiger"));
		buttons.get(3).addActionListener(this);

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);

		JPanel knoepfe = new JPanel();
		knoepfe.setLayout(new BoxLayout(knoepfe, BoxLayout.LINE_AXIS));
		for (JButton but : buttons) {
			knoepfe.add(but);
		}

		current.setOpaque(true);
		current.setForeground(Color.BLACK);
		current.setBackground(Color.WHITE);
		current.setHorizontalAlignment(JLabel.CENTER);
		
		mcurrent.setOpaque(true);
		mcurrent.setForeground(Color.BLACK);
		mcurrent.setBackground(Color.WHITE);
		mcurrent.setHorizontalAlignment(JLabel.LEFT);
		
		ocurrent.setOpaque(true);
		ocurrent.setForeground(Color.BLACK);
		ocurrent.setBackground(Color.WHITE);
		ocurrent.setHorizontalAlignment(JLabel.LEFT);

		panel.add(knoepfe);
		panel.add(jch);
		panel.add(current);
		panel.add(ocurrent);
		panel.add(mcurrent);
		mouse = new MyMouseListener();
		addMouseListener(mouse);
		getContentPane().add(panel);
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ToolBox");
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		mode = e.getActionCommand();
		current.setText("Current Mode: "+mode);
	}
	
	public void tick(){
		Point p = w.getMousePosition();
		if (p != null){
			mcurrent.setText("("+p.x+"/"+p.y+")"+" MousePoint");
		}
		if (oc != null){
			ocurrent.setText(oc.toString());
		}
	}

	public void shoInfo(Block bl) {
		oc = bl;
	}
}
