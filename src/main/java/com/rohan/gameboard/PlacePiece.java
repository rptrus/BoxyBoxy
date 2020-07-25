package com.rohan.gameboard;

import java.util.List;

import com.rohan.constants.Constants;
import com.rohan.enums.PieceStatus;
import com.rohan.enums.Player;
import com.rohan.utils.Utils;

/**
 * Player piece placement logic, which writes to a canvas
 * 
 * @author rohan
 *
 */
public class PlacePiece {

	private Buffer gameBuffer;
	private Buffer screenBuffer;
	private int completedSquares; // running total of ALL completed squares
	private int captureSquareTally; // sequence of captures squares (if any) by current player, usually 0 or 1 but can be more

	public void setBuffers(Buffer gameBuffer, Buffer screenBuffer) {
		this.screenBuffer = screenBuffer;
		this.gameBuffer = gameBuffer;
	}

	/**
	 * Input has already been validated by regex in checkSyntax
	 * 
	 * @param place
	 * @throws Exception
	 */
	public boolean placePiece(Player player, String place) throws Exception {
		boolean goodPlayerMove = false;
		int col = Utils.getCol(place.substring(0, 1));
		int row = Utils.getRow(place.substring(1, 2));
		PieceStatus pcStatus = checkValid(col, row);
		if (pcStatus == PieceStatus.OK) {
			gameBuffer.writeToBuffer(row, col, player.getPlayerNumber()); // mark who owns the square
			if (row % 2 == 1) {
				screenBuffer.writeToBuffer(row, col, Constants.BAR);
			} else {
				screenBuffer.writeToBuffer(row, col, Constants.PIPE);
			}
			goodPlayerMove = true;
		} else {
			switch (pcStatus) {
			case OCCUPIED:
				throw new Exception(Constants.ALREADY_OCCUPIED);
			// fallthru
			case OUT_OF_BOUNDS:
			case FRAMEWORK_PLACEMENT:
			case PLAYER_MARKER:
			default:
				throw new Exception(Constants.INVALID_MOVE);
			}
		}
		return goodPlayerMove;
	}

	/**
	 * Check that the piece is placed validly
	 * @param col
	 * @param row
	 * @return
	 */
	private PieceStatus checkValid(int col, int row) {
		PieceStatus pieceStatus = PieceStatus.OK;
		if (row > gameBuffer.getMaxSizeY() || col > gameBuffer.getMaxSizeX()) {
			return PieceStatus.OUT_OF_BOUNDS;
		}
		boolean placedOK = false;
		char c = gameBuffer.getFromBuffer(row, col);
		if (c == Constants.BLANK) {
			placedOK = true;
			pieceStatus = PieceStatus.OK;
		} else {
			// attempt to place on invalid spot
			if (c == '1' || c == '2') {
				pieceStatus = PieceStatus.OCCUPIED;
			} else if (c == Constants.STAR) {
				pieceStatus = PieceStatus.FRAMEWORK_PLACEMENT;
			} else if (c == Constants.CAPTURESQ) {
				pieceStatus = PieceStatus.PLAYER_MARKER;
			} else {
				System.err.println("Unexpected error (" + c + ") at: row/col" + row + "," + col);
				pieceStatus = PieceStatus.ERROR;
			}
		}
		return pieceStatus;

	}

	/**
	 * Check if the move has closed up a square 
	 * @param player
	 * @return
	 */
	public int checkCompletedSquare(Player player) {
		boolean captured = false;

		for (int y = 1; y < gameBuffer.getMaxSizeY(); y++) {
			for (int x = 1; x < gameBuffer.getMaxSizeX(); x++) {
				if (gameBuffer.getFromBuffer(y, x) == Constants.CAPTURESQ) {
					// check 4 boundaries
					if (checks(y, x)) {
						// captured. mark it.
						captured = true;
						gameBuffer.writeToBuffer(y, x, player.getPlayerCaptureId());
						screenBuffer.writeToBuffer(y, x, player.getPlayerNumber());
						completedSquares++;
					}
				}
			}
		}
		int tallyCaptureForPlayer = captureSquareTally;
		resetCapturedSquareTally();
		return tallyCaptureForPlayer;
	}

	/**
	 * checks the 4 walls to see if occupied
	 * 
	 * @param y
	 * @param x
	 * @return
	 */
	private boolean checks(int y, int x) {
		List<Character> playerMarker = List.of('1', '2');
		boolean captured = false;
		if (playerMarker.contains(gameBuffer.getFromBuffer(y, x - 1))) {
			if (playerMarker.contains(gameBuffer.getFromBuffer(y, x + 1))) {
				if (playerMarker.contains(gameBuffer.getFromBuffer(y - 1, x))) {
					if (playerMarker.contains(gameBuffer.getFromBuffer(y + 1, x))) {
						captureSquareTally++;
						captured = true;
					}
				}
			}
		}
		return captured;
	}

	public int getCompletedSquares() {
		return completedSquares;
	}

	public void resetCapturedSquareTally() {
		captureSquareTally = 0;
	}

}
