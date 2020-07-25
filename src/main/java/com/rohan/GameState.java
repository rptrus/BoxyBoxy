package com.rohan;

import com.rohan.constants.Constants;
import com.rohan.enums.Player;
import com.rohan.gameboard.Buffer;
import com.rohan.gameboard.CanvasBuffer;
import com.rohan.gameboard.GameBoardDecoration;
import com.rohan.gameboard.GameBuffer;
import com.rohan.gameboard.PlacePiece;

/**
 * Class to hold game state
 * 
 * @author rohan
 *
 */
public class GameState {

	private Buffer gameBuffer = new GameBuffer(); // memory map for game elements
	private Buffer screenBuffer = new CanvasBuffer(); // buffer for screen output
	private Score score = new Score();
	private Player currentPlayer = Player.PLAYER_ONE;
	private final int maxCapturedSquares = (Constants.GAME_SIZE_X - 1) * (Constants.GAME_SIZE_Y - 1);
	private GameBoardDecoration gameBoardDecoration = new GameBoardDecoration();
	PlacePiece placePiece = new PlacePiece();

	public GameState() {
	}

	public GameState(Buffer gameBuffer, Buffer screenBuffer) {
		this.gameBuffer = gameBuffer;
		this.screenBuffer = screenBuffer;
	}

	public void initCanvas() {
		placePiece.setBuffers(gameBuffer, screenBuffer);
		gameBoardDecoration.setBuffers(gameBuffer, screenBuffer);
		gameBoardDecoration.assemble(Constants.GAME_SIZE_X, Constants.GAME_SIZE_Y);
	}

	public void displayGameCanvas() {
		gameBuffer.display();
	}

	public void displayScreenCanvas() {
		screenBuffer.display();
	}

	public void displayScore() {
		System.out.println("SCORE Player 1: " + score.getP1Score() + " Player 2: " + score.getP2Score());
	}

	public Player getCurentTurn() {
		return currentPlayer;
	}

	public Player switchPlayer() {
		currentPlayer = currentPlayer == Player.PLAYER_ONE ? Player.PLAYER_TWO : Player.PLAYER_ONE;
		return currentPlayer;
	}

	public boolean placePiece(String gamePlayMove) throws Exception {
		return placePiece.placePiece(currentPlayer, gamePlayMove);

	}

	public int getMaxSquares() {
		return maxCapturedSquares;
	}

	public PlacePiece getPlacePiece() {
		return placePiece;
	}

	public Score getScore() {
		return score;
	}

	public Player getWinner() {
		return score.getWinner();
	}

}
