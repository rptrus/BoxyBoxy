package com.rohan.gameboard;

public class CanvasBuffer implements Buffer {
	
	char[][] buf;
	
	private int maxSizeX;
	private int maxSizeY;
	
	public int getMaxSizeX() {
		return maxSizeX;
	}
	
	public void setMaxSizeX(int maxSizeX) {
		this.maxSizeX = maxSizeX;
	}
	
	public int getMaxSizeY() {
		return maxSizeY;
	}
	
	public void setMaxSizeY(int maxSizeY) {
		this.maxSizeY = maxSizeY;
	}
	
	@Override
	public void writeToBuffer(int y, int x, char c) {
		buf[y][x] = c;
	}

	@Override
	public void setSize(int x, int y) {
		buf = new char[y][x];
		maxSizeX = x;
		maxSizeY = y;
	}

	@Override
	public void initCanvas() {
		// TODO Auto-generated method stub
	}

	@Override
	public void display() {
		for (int i = 0; i < getMaxSizeY(); i++) {
			for (int j = 0; j < getMaxSizeX(); j++) {
				System.out.printf("%c", buf[i][j]);
			}
			System.out.println();
		}

	}

	@Override
	public char getFromBuffer(int y, int x) {		
		return buf[y][x];
	}

}
