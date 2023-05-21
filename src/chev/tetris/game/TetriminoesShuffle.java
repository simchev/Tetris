package chev.tetris.game;

public class TetriminoesShuffle {

	private Tetriminoes[] possibleTetriminoes;
	private int pieceCount;
	private int totalPieces;
	
	public TetriminoesShuffle() {
		possibleTetriminoes = new Tetriminoes[7];
		totalPieces = 7;
		respawn();
	}
	
	private void respawn() {
		pieceCount = 7;
		possibleTetriminoes[0] = new I();
		possibleTetriminoes[1] = new L();
		possibleTetriminoes[2] = new RL();
		possibleTetriminoes[3] = new SQ();
		possibleTetriminoes[4] = new S();
		possibleTetriminoes[5] = new Z();
		possibleTetriminoes[6] = new T();
	}
	
	public Tetriminoes randomPick() {
		Tetriminoes randomTetriminoes;
		int choice = 0;
		
		// if no piece, spawn news
		if (pieceCount == 0) {
			respawn();
		}
		
		// if 1 piece, just get it
		if (pieceCount == 1) {
			for (int i = 0; i < totalPieces; i++) {
				if (possibleTetriminoes[i] != null) {
					randomTetriminoes = possibleTetriminoes[i];
					choice = i;
				}
			}
		}
		else { // if more, just find one
			boolean found = false;
			
			while (!found) {
				choice = (int)((Math.random() * 100) % totalPieces);
				if (possibleTetriminoes[choice] != null)
					found = true;
			}
		}
		
		randomTetriminoes = possibleTetriminoes[choice];
		possibleTetriminoes[choice] = null;
		pieceCount--;
		
		return randomTetriminoes;
	}
}
