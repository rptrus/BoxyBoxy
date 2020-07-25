package com.rohan.gameboard;

public interface Buffer {
	
	public void writeToBuffer(int y, int x, char c);
	
	public char getFromBuffer(int y, int x);
	
	public void setSize(int x, int y);
	
	public void initCanvas();
	
	public int getMaxSizeX(); // width
	
	public int getMaxSizeY(); // height

	public void display();		

}
