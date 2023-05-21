package chev.tetris.main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chev.tetris.states.StateManager;
import chev.tetris.util.StopWatch;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	// dimensions
	public static final int WIDTH = 700;
	public static final int HEIGHT = 820;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();
	}
	
	public void draw() {
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}
	
	public Graphics2D getImageGraphics() {
		return g;
	}
}
