package com.rohan;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rohan.constants.Constants;

/**
 * Main program
 * 
 * @author rohan
 *
 */
public class MainGame {

	GameState gameState = new GameState();
	final boolean AUTOMATED = false;

	ArrayList<String> feed = new ArrayList<String>();

	public static void main(String[] args) {
		MainGame mainGame = new MainGame();
		mainGame.process();
	}

	public void process() {
		if (AUTOMATED)
			initArray();
		gameState.initCanvas();
		for (;;) {
			gameState.displayScreenCanvas();
			// gameState.displayGameCanvas();
			gameState.displayScore();
			if (gameState.getPlacePiece().getCompletedSquares() == gameState.getMaxSquares()) {
				System.out.println(String.format(Constants.WIN_MESSAGE, gameState.getWinner().getPlayerNumber()));
				System.out.println(Constants.GOODBYE);
				gameState.getScore().resetGame();				
				return;
			}
			ResultStatus resultStatus;
			do {
				if (AUTOMATED) {
					String in = feed.remove(0);
					System.setIn(new ByteArrayInputStream(in.getBytes()));
				}
				Scanner scanner = new Scanner(System.in);
				System.out.print(String.format("Player %s, input a move <column><row> (or 'Q' to quit): ",
						gameState.getCurentTurn().getPlayerNumber()));
				String userCommand = scanner.nextLine();
				resultStatus = parseInput(userCommand);
			} while (!resultStatus.isPlayerMoveOk());
			// turn over
			if (!resultStatus.isPlayerCapturedSquare()) {
				gameState.switchPlayer();
			}

		}

	}

	public ResultStatus parseInput(String input) {

		// boolean goodPlayerMove = false;
		ResultStatus resultStatus = new ResultStatus();
		int playerCompletedSquares;

		if (!syntaxCheck(input)) {
			System.err.println(Constants.INVALID_MOVE);
			resultStatus.setPlayerMoveOk(false);
			// return false
			return resultStatus;
		}

		if (input.equalsIgnoreCase("Q")) {
			System.out.println(Constants.GOODBYE);
			System.exit(0);
		}

		StringTokenizer commandParser = new StringTokenizer(input);

		String gamePlayMove = commandParser.nextToken().toUpperCase();
		try {
			if (gameState.placePiece(gamePlayMove)) {
				resultStatus.setPlayerMoveOk(true);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		playerCompletedSquares = gameState.getPlacePiece().checkCompletedSquare(gameState.getCurentTurn());
		if (playerCompletedSquares > 0) {
			gameState.getScore().incrementScore(gameState.getCurentTurn(), playerCompletedSquares);
			resultStatus.setPlayerCapturedSquare(true);
		}

		// return goodPlayerMove;
		return resultStatus;
	}

	/**
	 * If running automated game
	 */
	public void initArray() {
		feed.addAll(Arrays.asList("B1", "B0", "A1", "B2", "C1", "J5", "6D", "D6", "C5", "D4", "E5", "C3", "D2", "F2",
				"F4", "G3", "E3", "B0", "G1", "G1", "F0", "D0", "E1", "A3", "B4", "A5", "B6", "G5", "F6"));
	}

	public boolean syntaxCheck(String inputString) {
		boolean matchesOK = false;
		if (inputString.toUpperCase().charAt(0) == 'Q')
			matchesOK = true;
		else {
			Pattern pattern = Pattern.compile("^[aAbBcCdDeEfFgG](\\d)$");
			Matcher matcher = pattern.matcher(inputString);
			matchesOK = matcher.matches();
		}
		return matchesOK;
	}

}
