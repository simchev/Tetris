package chev.tetris.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import chev.tetris.game.Block;
import chev.tetris.game.Ghost;
import chev.tetris.game.I;
import chev.tetris.game.TabBlock;
import chev.tetris.game.Tetriminoes;
import chev.tetris.game.TetriminoesShuffle;
import chev.tetris.main.Game;
import chev.tetris.main.GamePanel;
import chev.tetris.pixels.Background;
import chev.tetris.util.StopWatch;

public class PlayState extends GameState {
	
	// positions
	public static final int GAMEX = 150;
	public static final int GAMEY = 10;
	public static final int HOLDX = 20;
	public static final int NEXTX = 580;
	public static final int MENUY = 200;
	public static final int MENUYY = 310;

	// game objects
	private Background bg;
	private TabBlock blocks;
	private Tetriminoes piece;
	private Tetriminoes ghostPiece;
	private Tetriminoes holdPiece;
	private TetriminoesShuffle possiblePieces;
	private Tetriminoes[] nextPieces;
	
	// game stats
	private String title;
	private String msg;
	private int score;
	private int dropSpeed = 800;
	private StopWatch timer;
	private int minute;
	private int second;
	private int millis;
	private StopWatch gameTimer;
	private boolean quickDrop;
	private boolean holded;
	private boolean win;
	private boolean lost;
	private String doneMsg;
	
	// game starting
	private int startTime = 4000;
	private boolean starting;
	
	// Colors and font
	private Font utsFont;
	private Color redColor;
	private Font maturaFont;
	private Color whiteColor;
	private DecimalFormat df;
	
	public PlayState(StateManager sm) {
		this.sm = sm;
		
		// load backgrond
		bg = new Background("/Backgrounds/GameBackground.png");
		
		// load colors and fonts
		whiteColor = Color.white;
		redColor = new Color(220, 20, 20);
		maturaFont = new Font("Matura MT Script Capitals", Font.PLAIN, 100);
		utsFont = new Font("Sans Bold", Font.PLAIN, 80);
		
		// load stats
		df = new DecimalFormat("00");
		timer = new StopWatch();
		gameTimer = new StopWatch();
		
		init();
	}
	
	public String getTitle() {
		return title;
	}

	@Override
	public void init() {
		
		// load tabs
		blocks = new TabBlock(GamePanel.WIDTH / Block.BLOCKSIZE, GamePanel.HEIGHT / Block.BLOCKSIZE);
		possiblePieces = new TetriminoesShuffle();
		nextPieces = new Tetriminoes[2];
		
		// load pieces
		piece = possiblePieces.randomPick();
		makeDropGhost();
		for (int i = 0; i < 2; i++) {
			nextPieces[i] = possiblePieces.randomPick();
			setWait(nextPieces[i], i);
		}
		holdPiece = null;
		
		// load stats
		title = Game.GAMENAME + " - Starting";
		msg = "";
		quickDrop = false;
		holded = false;
		win = false;
		lost = false;
		score = 0;
		doneMsg = "";
		minute = 0;
		second = 0;
		millis = 0;
		
		// start things
		starting = true;
		timer.start();
	}

	@Override
	public void update() {
		bg.update();
		
		if (!win && !lost) {
			if (!starting) {
				if (quickDrop) {
					pieceEnd();
					quickDrop = false;
					timer.restart();
				}
				else if (timer.getElapsedMilli() > dropSpeed) {
					if (piece.isDone()) {
						pieceEnd();
					}
					else {
						piece.drop(blocks.getTabBlocks());
					}
					timer.restart();
				}
				
				millis = (gameTimer.getElapsedMilli() / 100) % 10;
				second = gameTimer.getElapsedSeconds() % 60;
				minute = gameTimer.getElapsedMinutes() % 60;
			}
			else {
				int elapsed = timer.getElapsedMilli();
				
				if (elapsed >= 0 && elapsed < startTime / 4) {
					msg = "3";
				}
				else if (elapsed >= startTime / 4 && elapsed < startTime / 2) {
					msg = "2";
				}
				else if (elapsed >= startTime / 2 && elapsed < (startTime / 4) * 3) {
					msg = "1";
				}
				else if (elapsed >= (startTime / 4) * 3 && elapsed < startTime) {
					msg = "go";
				}
				else {
					msg = "";
					starting = false;
					timer.restart();
					gameTimer.start();
				}
			}
		}
	}
	
	private void pieceEnd() {
		blocks.addBlocks(piece);
		score += blocks.clearLines();
		newPiece();
		holded = false;
		if (score >= 40) {
			score = 40;
			win();
		}
	}
	
	private void newPiece() {
		piece = nextPieces[0];
		setNormal(piece);
		nextPieces[0] = nextPieces[1];
		nextPieces[1] = possiblePieces.randomPick();
		for (int i = 0; i < nextPieces.length; i++) {
			setWait(nextPieces[i], i);
		}
		makeDropGhost();
		
		lost = piece.checkLoose(blocks.getTabBlocks());
		if (lost) {
			lost();
		}
	}
	
	private void makeDropGhost() {
		ghostPiece = new Ghost(piece);
		ghostPiece.quickDrop(blocks.getTabBlocks());
	}
	
	private void holdPiece() {
		if (!holded) {
			if (holdPiece == null) {
				holdPiece = piece;
				setWait(holdPiece, 2);
				newPiece();
			}
			else {
				Tetriminoes t = piece;
				piece = holdPiece;
				setNormal(holdPiece);
				holdPiece = t;
				setWait(holdPiece, 2);
				makeDropGhost();
			}
			holded = true;
		}
	}
	
	private void setNormal(Tetriminoes t) {
		t.setBlockSize(Block.BLOCKSIZE);
		t.startPosition();
	}
	
	private void setWait(Tetriminoes t, int pos) {
		int width = t.getWidth();
		int height = t.getHeight();
		
		t.setBlockSize(Block.WAITSIZE);
		t.setPosition(((pos == 2) ? HOLDX : NEXTX) - GAMEX + ((width == 2) ? 25 : (width == 3) ? 12 : 0), 
				((pos == 0 || pos == 2) ? MENUY : MENUYY) - GAMEY + ((height == 2) ? 25 : 12));
		t.resetPosition();
	}
	
	private void pauseGame() {
		if (timer.isRunning()) {
			timer.suspend();
		}
		else {
			timer.resume();
		}
		
		if (gameTimer.isRunning()) {
			gameTimer.suspend();
		}
		else {
			gameTimer.resume();
		}
	}
	
	private void stopGame() {
		timer.suspend();
		gameTimer.suspend();
	}
	
	private void win() {
		stopGame();
		win = true;
		doneMsg = "You won!";
	}
	
	private void lost() {
		stopGame();
		lost = true;
		doneMsg = "You lost!";
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		if (!starting) {
			blocks.draw(g);
			ghostPiece.draw(g);
			piece.draw(g);
			for (int i = 0; i < nextPieces.length; i++) {
				nextPieces[i].draw(g);
			}
			if (holdPiece != null) {
				holdPiece.draw(g);
			}
		}
		else {
			g.setFont(maturaFont);
			if (timer.getElapsedMilli() < (startTime / 4) * 3) {
				g.setColor(whiteColor);
				g.drawString(msg, 180 + PlayState.GAMEX, 380);
			}
			else {
				g.setColor(redColor);
				g.drawString(msg, 155 + PlayState.GAMEX, 380);
			}
		}
	
		g.setColor(whiteColor);
		g.setFont(utsFont.deriveFont(70F));
		g.drawString(df.format(score), 32, 555);
		
		g.setFont(utsFont.deriveFont(30F));
		g.drawString(minute + ":" + df.format(second) + ":" + df.format(millis), 20, 680);
		
		g.setFont(utsFont.deriveFont(35F));
		if (!timer.isRunning()) {
			g.drawString("paused", 576, 800);
			g.setFont(utsFont.deriveFont(20F));
			g.drawString("Escape/P -", 578, 540);
			g.drawString("Resume", 578, 560);
			drawOptions(g);
			title = Game.GAMENAME + " - Paused";
		}
		else if (starting) {
			g.drawString("starting", 575, 800);
			title = Game.GAMENAME + " - Starting";
		}
		else if (win) {
			g.drawString("won", 575, 800);
			title = Game.GAMENAME + " - Won";
		}
		else if (lost) {
			g.drawString("lost", 575, 800);
			title = Game.GAMENAME + " - Lost";
		}
		else {
			g.drawString("playing", 578, 800);
			title = Game.GAMENAME + " - Playing";
		}
		
		if (win || lost) {
			g.setFont(maturaFont.deriveFont(70F));
			
			if (win) {
				g.setColor(Color.green);
			}
			else {
				g.setColor(redColor);
			}
			
			g.drawString(doneMsg, 185, 400);
			drawOptions(g);
		}
	}
	
	private void drawOptions(Graphics2D g) {
		g.setFont(utsFont.deriveFont(20F));
		g.setColor(whiteColor);
		g.drawString("R - Restart", 578, 600);
		g.drawString("Q - Back to", 578, 640);
		g.drawString("Main Menu", 578, 660);
	}

	@Override
	public void keyPressed(int k) {
		if (!win && !lost) {
			if (k == KeyEvent.VK_P) {
				pauseGame();
			}
			if (k == KeyEvent.VK_ESCAPE) {
				pauseGame();
			}
			if (!starting & timer.isRunning()) {
				if (k == KeyEvent.VK_DOWN) {
					piece.drop(blocks.getTabBlocks());
				}
				if (k == KeyEvent.VK_UP) {
					piece.rotateRight(blocks.getTabBlocks());
					makeDropGhost();
				}
				if (k == KeyEvent.VK_Z) {
					piece.rotateLeft(blocks.getTabBlocks());
					makeDropGhost();
				}
				if (k == KeyEvent.VK_LEFT) {
					piece.moveLeft(blocks.getTabBlocks());
					makeDropGhost();
				}
				if (k == KeyEvent.VK_RIGHT) {
					piece.moveRight(blocks.getTabBlocks());
					makeDropGhost();
				}
				if (k == KeyEvent.VK_SPACE) {
					piece.quickDrop(blocks.getTabBlocks());
					quickDrop = true;
				}
				if (k == KeyEvent.VK_SHIFT) {
					holdPiece();
				}
			}
		}
		if (!timer.isRunning() || win || lost) {
			if (k == KeyEvent.VK_Q) {
				sm.setState(StateManager.MENUSTATE);
			}
			if (k == KeyEvent.VK_R) {
				init();
			}
		}
	}

	@Override
	public void keyReleased(int k) {
		
	}
}
