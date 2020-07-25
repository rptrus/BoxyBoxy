package com.rohan;

import com.rohan.enums.Player;
import com.rohan.gameboard.Buffer;
import com.rohan.gameboard.CanvasBuffer;
import com.rohan.gameboard.GameBoardDecoration;
import com.rohan.gameboard.GameBuffer;
import com.rohan.gameboard.PlacePiece;

import junit.framework.TestCase;

public class BoxGameAppTest extends TestCase {
	
	Buffer gameBuffer = new GameBuffer();
	Buffer canvasBuffer = new CanvasBuffer();


	public void testPlacePieceValid() {
		PlacePiece placePiece = new PlacePiece();
		GameState gameState = new GameState(gameBuffer, canvasBuffer);
		gameState.initCanvas();
		placePiece.setBuffers(gameBuffer, canvasBuffer);
		Player player = Player.PLAYER_ONE;
		boolean b = false;
		try {
			b = placePiece.placePiece(player, "B0");
		} catch (Exception e) {
			b = false;
		}
		assertTrue(b);
	}
	
	public void testPlacePieceInvalidOnDot() {
		PlacePiece placePiece = new PlacePiece();
		Buffer gameBuffer = new GameBuffer();
		Buffer canvasBuffer = new CanvasBuffer();
		GameState gameState = new GameState(gameBuffer, canvasBuffer);
		gameState.initCanvas();
		placePiece.setBuffers(gameBuffer, canvasBuffer);
		Player player = Player.PLAYER_ONE;
		boolean b = false;
		try {
			b = placePiece.placePiece(player, "A0");
		} catch (Exception e) {
			b = false;
		}
		assertFalse(b);
	}
	
	public void testPlacePieceInvalidOnPlayerOwnerSquare() {
		PlacePiece placePiece = new PlacePiece();
		Buffer gameBuffer = new GameBuffer();
		Buffer canvasBuffer = new CanvasBuffer();
		GameState gameState = new GameState(gameBuffer, canvasBuffer);
		gameState.initCanvas();
		placePiece.setBuffers(gameBuffer, canvasBuffer);
		Player player = Player.PLAYER_ONE;
		boolean b = false;
		try {
			b = placePiece.placePiece(player, "B1");
		} catch (Exception e) {
			b = false;
		}
		assertFalse(b);
	}
	
	public void testBadInputOutOfRange() {
		MainGame mainGame = new MainGame();
		ResultStatus rs = mainGame.parseInput("Z9");
		assertFalse(rs.isPlayerMoveOk());
	}
	
	public void testBadInputWrongFormat() {
		MainGame mainGame = new MainGame();
		ResultStatus rs = mainGame.parseInput("2C");
		assertFalse(rs.isPlayerMoveOk());
	}
	
	public void testSwitchPlayer() {
		GameState gameState = new GameState();
		Player p = gameState.switchPlayer();
		assertEquals(p, Player.PLAYER_TWO);
	}
	
	public void testCanvasReadWrite() {
		GameBuffer gameBuffer = new GameBuffer();
		gameBuffer.setSize(8, 8);
		char input = 'x';
		gameBuffer.writeToBuffer(2, 3, input);
		char output = gameBuffer.getFromBuffer(2, 3);
		assertEquals(output, input);
	}
	
	public void testGameBoardDecoration() {
		gameBuffer.setSize(8, 8);
		GameBoardDecoration gameboardDecoration = new GameBoardDecoration(gameBuffer, canvasBuffer);
		gameboardDecoration.populateCaptureSquares(gameBuffer);
		assertEquals(gameBuffer.getFromBuffer(2, 2), '@'); // @ is internal marker symbol for a player square to claim
	}

}
