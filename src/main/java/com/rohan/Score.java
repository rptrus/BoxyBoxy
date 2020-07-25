package com.rohan;

import com.rohan.enums.Player;

/**
 * Score keeping and determining winner
 * 
 * @author rohan
 *
 */
public class Score {

	private int p1Score;
	private int p2Score;

	public void resetGame() {
		p1Score = 0;
		p2Score = 0;
	}

	public int getP1Score() {
		return p1Score;
	}

	public int getP2Score() {
		return p2Score;
	}

	public void incrementScore(Player player) {
		if (player.equals(Player.PLAYER_ONE))
			p1Score++;
		if (player.equals(Player.PLAYER_TWO))
			p2Score++;
	}

	public void incrementScore(Player player, int capturedSquares) {
		if (player.equals(Player.PLAYER_ONE))
			p1Score += capturedSquares;
		if (player.equals(Player.PLAYER_TWO))
			p2Score += capturedSquares;
	}

	public Player getWinner() {
		return (p1Score > p2Score) ? Player.PLAYER_ONE : Player.PLAYER_TWO;
	}

}
