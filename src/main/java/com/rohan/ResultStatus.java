package com.rohan;

/**
 * Checks validity and sqaure captures of a move
 * 
 * @author rohan
 *
 */
public class ResultStatus {

	private boolean playerMoveOk = false;

	private boolean playerCapturedSquare = false;

	public boolean isPlayerMoveOk() {
		return playerMoveOk;
	}

	public void setPlayerMoveOk(boolean playerMoveOk) {
		this.playerMoveOk = playerMoveOk;
	}

	public boolean isPlayerCapturedSquare() {
		return playerCapturedSquare;
	}

	public void setPlayerCapturedSquare(boolean playerCapturedSquare) {
		this.playerCapturedSquare = playerCapturedSquare;
	}

}
