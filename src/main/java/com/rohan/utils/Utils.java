package com.rohan.utils;

import com.rohan.constants.Constants;

/**
 * Utility methods
 * 
 * @author rohan
 *
 */
public class Utils {

	public static int getCol(String col) {
		char A = 'A';
		char upperCol = col.toUpperCase().charAt(0);
		int plc = upperCol - A;
		return plc + Constants.INSET;
	}

	public static int getRow(String row) {
		return Integer.parseInt(row) + Constants.INSET;
	}

}
