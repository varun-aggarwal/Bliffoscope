package com.varun.bliffoscope;
/*
 * represent each co-ordinate and its confidence percentage
 */
public class MatchCoorindates {
	String target_type;
	int x;
	int y;
	double confidencepercentage;
	
	public MatchCoorindates(String target_type, int x, int y,
			double confidencepercentage) {
		super();
		this.target_type = target_type;
		this.x = x;
		this.y = y;
		this.confidencepercentage = confidencepercentage;
	}
}
