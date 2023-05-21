package chev.tetris.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import chev.tetris.states.StateManager;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements KeyListener{
	
	private GamePanel panel;
	private StateManager sm;
	
	public GameFrame() {
		// Set panel stats
		panel = new GamePanel();
		setContentPane(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		
		// Add focus
		setFocusable(true);
		addKeyListener(this);
		
		// Create a state manager
		sm = new StateManager();
	}
	
	public void update() {
		sm.update();
		sm.draw(panel.getImageGraphics());
		panel.draw();
		setTitle(sm.getTitle());
	}

	public void keyPressed(KeyEvent e) {
		sm.keyPressed(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		sm.keyReleased(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {
		
	}
}
