package chev.tetris.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import chev.tetris.main.Game;
import chev.tetris.main.GamePanel;
import chev.tetris.pixels.Background;

public class MenuState extends GameState {
	
	// Background 
	private Background bg;
	private float bgSpeedX = 2;
	private float bgSpeedY = 0;
	
	// Menu options
	private String[] options = { "Start", "Quit" };
	private int currentChoice;
	private String title;
	
	// Title
	private Color color;
	private Font font;
	
	public MenuState(StateManager sm) {
		this.sm = sm;
		
		title = Game.GAMENAME + " - Main Menu";
		
		// Background
		bg = new Background("/Backgrounds/MenuBackground.png");
		
		// Title
		color = new Color(170, 20, 20);
		font = new Font("Matura MT Script Capitals", Font.PLAIN, 200);
	}
	
	public String getTitle() {
		return title;
	}

	@Override
	public void init() {
		currentChoice = 0;
		bg.setVector(bgSpeedX, bgSpeedY);
	}

	@Override
	public void update() {
		bg.update();
	}

	@Override
	public void draw(Graphics2D g) {
		// draw background
		bg.draw(g);
		
		// draw title
		g.setColor(color);
		g.setFont(font);
		g.drawString("Tetris", 60, 150);
		
		// draw menu options
		g.setFont(font.deriveFont(100F));
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(color);
			}
			else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 75 + i * 300, 720);
		}
	}
	
	private void select() {
		if (currentChoice == 0) {
			sm.setState(StateManager.PLAYSTATE);
		}
		else if (currentChoice == 1) {
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
		if (k == KeyEvent.VK_LEFT) {
			currentChoice--;
			if (currentChoice == -1) 
				currentChoice = options.length - 1;
		}
		if (k == KeyEvent.VK_RIGHT) {
			currentChoice++;
			if (currentChoice == options.length)
				currentChoice = 0;
		}
	}

	@Override
	public void keyReleased(int k) {
		
	}

}
