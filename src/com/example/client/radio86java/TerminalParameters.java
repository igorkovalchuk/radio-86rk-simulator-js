package com.example.client.radio86java;

public class TerminalParameters {

	public final int terminalWidth;
	public final int terminalHeight;
	
	public final int charsetWidth;
	public final int charsetHeight;
	
	public final int xGap;
	public final int yGap;
	
	public final double xMultiplier;
	public final double yMultiplier;

	public final int deltaX;
	public final int deltaY;
	
	public final int canvasWidth;
	public final int canvasHeight;

	public final int scaledCharsetWidth;
	public final int scaledCharsetHeight;
	
	public TerminalParameters(int terminalWidth, int terminalHeight,
			int charsetWidth, int charsetHeight,
			int xGap, int yGap,
			double xMultiplier, double yMultiplier) {

		this.terminalWidth = terminalWidth;
		this.terminalHeight = terminalHeight;

		this.charsetWidth = charsetWidth;
		this.charsetHeight = charsetHeight;

		this.xGap = xGap;
		this.yGap = yGap;

		this.xMultiplier = xMultiplier;
		this.yMultiplier = yMultiplier;

		this.deltaX = (int)(charsetWidth * xMultiplier + xGap);
		this.deltaY = (int)(charsetHeight * yMultiplier + yGap);

		this.canvasWidth = terminalWidth * deltaX;
		this.canvasHeight = terminalHeight * deltaY;
		
		this.scaledCharsetWidth = (int)(charsetWidth * xMultiplier);
		this.scaledCharsetHeight = (int)(charsetHeight * yMultiplier);
	}
}
