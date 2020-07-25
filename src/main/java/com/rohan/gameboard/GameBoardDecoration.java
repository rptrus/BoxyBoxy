package com.rohan.gameboard;

import com.rohan.constants.Constants;

/**
 * Provide lower level operations to buffers
 * 
 * @author rohan
 *
 */
public class GameBoardDecoration {

	Buffer gameBuffer;

	Buffer canvasBuffer;

	public GameBoardDecoration() {
	};

	public GameBoardDecoration(Buffer gameBuffer, Buffer canvasBuffer) {
		this.gameBuffer = gameBuffer;
		this.canvasBuffer = canvasBuffer;
	}

	public void setBuffers(Buffer gameBuffer, Buffer canvasBuffer) {
		this.gameBuffer = gameBuffer;
		this.canvasBuffer = canvasBuffer;
	}

	public void assemble(int sizeX, int sizeY) {
		// the dimensions including spaces and headers is effectively x*2 and y*2

		int realSizeX = sizeX * 2;
		int realSizeY = sizeY * 2;
		// screen
		canvasBuffer.setSize(realSizeX, realSizeY);
		stampBorder(canvasBuffer);
		populateDots(canvasBuffer);
		//canvasBuffer.display();

		gameBuffer.setSize(realSizeX, realSizeY);
		stampBorder(gameBuffer);
		fillDefault(gameBuffer);
		populateCaptureSquares(gameBuffer);
		populateDots(gameBuffer);
		//gameBuffer.display();

	}

	private void populateDots(Buffer buffer) {
		int xCounter = Constants.INSET;
		int yCounter = Constants.INSET;

		while (yCounter < buffer.getMaxSizeY()) {
			while (xCounter < buffer.getMaxSizeX()) {
				if (xCounter % 2 == 1 && yCounter % 2 == 1) {
					buffer.writeToBuffer(yCounter, xCounter, Constants.STAR);
				} 
				xCounter++;
			}
			yCounter++;
			xCounter = Constants.INSET;
		}

	}

	public void stampBorder(Buffer buffer) {

		char topBorderLabel = 'A';
		char sideBorderLabel = '0';

		int counter = 1;
		// top
		buffer.writeToBuffer(0, 0, Constants.SPACE);
		while (counter < buffer.getMaxSizeX()) {
			buffer.writeToBuffer(0, counter, topBorderLabel++);
			counter++;
		}
		// sides
		int sideCtr = 1;
		while (sideCtr < buffer.getMaxSizeY()) {
			buffer.writeToBuffer(sideCtr, 0, sideBorderLabel++);
			sideCtr++;
		}
		System.out.println();
	}

	public void fillDefault(Buffer buffer) {
		int xCounter = Constants.INSET;
		int yCounter = Constants.INSET;
		while (yCounter < buffer.getMaxSizeY()) {
			while (xCounter < buffer.getMaxSizeX()) {
				buffer.writeToBuffer(yCounter, xCounter, Constants.BLANK);
				xCounter++;
			}
			yCounter++;
			xCounter = 1;
		}
	}

	/**
	 * Represented by @ symbol
	 */
	public void populateCaptureSquares(Buffer buffer) {
		int xCounter = Constants.INSET; // inset by 1 to skip the labels
		int yCounter = Constants.INSET;

		while (yCounter < buffer.getMaxSizeY()) {
			while (xCounter < buffer.getMaxSizeX()) {
				if (xCounter % 2 == 0 && yCounter % 2 == 0) {
					buffer.writeToBuffer(yCounter, xCounter, Constants.CAPTURESQ);
				}
				xCounter++;
			}
			yCounter++;
			xCounter = Constants.INSET;
		}
	}

}
