package com.rohan.enums;

public enum Player {
	PLAYER_ONE('1','O'), PLAYER_TWO('2','T');

	private char playerNum;
	
	private char captureId; // 'sides' are represented by player number and captured square represented by player letter ([o]ne/[t]wo)

	Player(char number, char captureId) {
		this.playerNum = number;
		this.captureId = captureId; 
	}

	public char getPlayerNumber() {
		return playerNum;
	}
	
	public char getPlayerCaptureId() {
		return captureId;
	}

}
