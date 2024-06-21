package com.example.client.radio86java;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;

/**
 * An adapter to Context2d
 */
public class UserInterfaceImpl implements UserInterfaceIntf {

	private final ComputerModelIntf computerModel;
	private final TerminalParameters parameters;

	private final Context2d context;
	private final ImageElement charsetImage;

	private boolean freeze = false;

	private final CssColor BLACK = getColor(0, 0, 0);
	private final CssColor YELLOW = getColor(255, 255, 0);
	//private final CssColor GRAY = getColor(127, 127, 127);
	
	/**
	 * @param computerModel
	 * @param multiplier    1 for 8*8 pixels or 2 for 16*16 pixels;
	 * @param space         some space around chars
	 */
	public UserInterfaceImpl(Context2d context,
			ImageElement charsetImage,
			ComputerModelIntf computerModel) {

		this.context = context;
		this.charsetImage = charsetImage;

		this.computerModel = computerModel;
		this.parameters = computerModel.getTerminalModel().getParameters();

		initialPaintComponent();
	}

	private void initialPaintComponent() {
		context.save();
		context.setFillStyle(BLACK);
		context.fillRect(0, 0, parameters.canvasWidth, parameters.canvasHeight);
		context.restore();
	}
	
	private TerminalModelIntf getTerminalModel() {
		return computerModel.getTerminalModel();
	}

	// private boolean partial = false;

	// TODO: implement partial repaint
	private void setPartialRepaint(boolean value) {
		// this.partial = value;
	}

	private void paintComponent() {

		if (freeze) {
			return;
		}

		fullRepaint();

		drawCursor();
	}

	private void drawCursor() {
		
		TerminalModelIntf console = getTerminalModel();

		int x = console.getCursorX();
		int y = console.getCursorY();

		if (console.getDirectionUp() > 0) {
			y = (parameters.terminalHeight - 1 - console.getCursorY());
		}
		if (x >= 0 && x < parameters.terminalWidth && y >= 0 && y < parameters.terminalHeight) {
			x = x * parameters.deltaX;
			y = y * parameters.deltaY + parameters.deltaY;

			context.save();
			context.setStrokeStyle(YELLOW);
			context.moveTo(x, y);
			context.lineTo(x + parameters.deltaX, y);
			context.restore();
		}
	}

	private void fullRepaint() {

		initialPaintComponent();

		context.save();

		int screenY = 0;
		int screenX = 0;

		TerminalModelIntf console = getTerminalModel();

		if (console.getDirectionUp() > 0) {
			for (int y = (parameters.terminalHeight - 1); y >= 0; y--) {
				for (int x = 0; x < parameters.terminalWidth; x++) {
					char c = console.get(x, y);
					render(screenX, screenY, c, console);
					screenX++;
				}
				screenY++;
				screenX = 0;
			}
		} else {
			for (int y = 0; y < parameters.terminalHeight; y++) {
				for (int x = 0; x < parameters.terminalWidth; x++) {
					char c = console.get(x, y);
					render(screenX, screenY, c, console);
					screenX++;
				}
				screenY++;
				screenX = 0;
			}
		}

		context.restore();
	}

	private void render(int screenX, int screenY, char c, TerminalModelIntf console) {

		if (((int) c == 32) || ((int) c == 0)) {
			return;
		}

		// GWT.log("render, x = " + screenX + ", y = " + screenY + " char = " + (int)c);

		context.drawImage(charsetImage, 
				0, c * parameters.charsetHeight, 
				parameters.charsetWidth, parameters.charsetHeight, 
				screenX * parameters.deltaX, screenY * parameters.deltaY,
				parameters.scaledCharsetWidth, parameters.scaledCharsetHeight);
	}

	private CssColor getColor(int red, int green, int blue) {
		return CssColor.make(red, green, blue);
	}

	@Override
	public void updateScreen() {
		if (!freeze) {
			//GWT.log("updateScreen");
			setPartialRepaint(true);
			paintComponent();
			setPartialRepaint(false);
			//GWT.log("updateScreen - done");
		}
	}

	@Override
	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
		if (freeze) {
			// does nothing
		} else {
			updateScreen();
		}
	}

	@Override
	public String showInputDialog(String message) {
		GWT.log("Not implemented: showInputDialog(" + message + ")");
		return null;
	}

}
