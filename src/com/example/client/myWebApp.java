package com.example.client;

import com.example.client.radio86java.ComputerModel;
import com.example.client.radio86java.ComputerModelIntf;
import com.example.client.radio86java.Keyboard;
import com.example.client.radio86java.Radio86rkAPI;
import com.example.client.radio86java.TerminalParameters;
import com.example.client.radio86java.UserInterfaceImpl;
import com.example.client.radio86java.UserInterfaceIntf;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class myWebApp implements EntryPoint {

	/**
	 * update canvas based on interval
	 */
	private static final int REFRESH_RATE = 1000;

	private static final int CANVAS_HEIGHT = 25 * (8 * 1 + 1);
	private static final int CANVAS_WIDTH = 64 * (8 * 1 + 1);

	private Canvas canvas;
	private Context2d context;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		Button rButton = new Button("Run the written code");
		rButton.setStyleName("my-button", true);
		RootPanel.get().add(rButton);

		VerticalPanel vp1 = new VerticalPanel();
		VerticalPanel vp2 = new VerticalPanel();
		
		TabPanel tp = new TabPanel();
	    tp.add(vp1, " ... Display ... ");
	    tp.add(vp2, "... ... ... Code ... ... ...");
	    tp.selectTab(0);
	    RootPanel.get().add(tp);
		
	    final TextArea ta = new TextArea();
	    ta.setCharacterWidth(80);
	    ta.setVisibleLines(50);
	    vp2.add(ta);
	    ta.setText("cls();\n" + 
	    		"\n" + 
	    		"plot(19, 19 ,1); \n" + 
	    		"plot(21, 19, 1); \n" + 
	    		"plot(23, 19, 1);\n" + 
	    		"");

		//RootPanel.get().add(new Label("HTML5 Canvas element"));
		Canvas canvas = Canvas.createIfSupported();
		if (canvas == null) {
			RootPanel.get().add(new Label("Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}

		TerminalParameters parameters = new TerminalParameters(64, 25, 8, 8, 1, 1, 2, 2);

		canvas.setWidth(parameters.canvasWidth + Unit.PX.getType());
		canvas.setCoordinateSpaceWidth(parameters.canvasWidth);
		canvas.setHeight(parameters.canvasHeight + Unit.PX.getType());
		canvas.setCoordinateSpaceHeight(parameters.canvasHeight);

		context = canvas.getContext2d();
		vp1.add(canvas);

		/////////////////////////////////////////////
		
		ImageElement imageElement = ImageElement.as(DOM.getElementById("rk_font1"));
		ComputerModelIntf computerModel = new ComputerModel(parameters);
		UserInterfaceIntf ui = new UserInterfaceImpl(context, imageElement, computerModel);
		Radio86rkAPI api = Radio86rkAPI.createInstance(computerModel, ui);

		final ClickHandler bHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String text = ((Button)event.getSource()).getText();
				Keyboard.press(text);
			}
		};

		RootPanel.get().add(new HTML("<br>"));
		for(Object rowObj : Keyboard.keyboard) {
			String[] row = (String[])rowObj;
			for(String key : row) {
				Button button = new Button(key);
				button.setStyleName("my-button", true);
				button.addClickHandler(bHandler);
				RootPanel.get().add(button);
			}
			RootPanel.get().add(new HTML("<br>"));
		}

		rButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String program = ta.getText();
				Keyboard.reset();
				tryToStart(program);
				//createDialog();
			}
		});

		/////////////////////////////////////////////

		class MyHandler implements KeyUpHandler {

			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					Keyboard.press(Keyboard.ENTER);
				}
				else if(event.isUpArrow()) {
					Keyboard.press(Keyboard.UP);
				} 
				else if(event.isDownArrow()) {
					Keyboard.press(Keyboard.DOWN);
				}
				else if(event.isLeftArrow()) {
					Keyboard.press(Keyboard.LEFT);
				}
				else if(event.isRightArrow()) {
					Keyboard.press(Keyboard.RIGHT);
				}
				else {
					int code = event.getNativeKeyCode();
					if (code <= 127) {
						char ch = (char)code;
						Keyboard.press(String.valueOf(ch));
					}
				}
			}

		}

		MyHandler handler = new MyHandler();
		canvas.addKeyUpHandler(handler);

		////////////////////////////////////////////
		
		final Timer timer = new Timer() {
			@Override
			public void run() {
				// updateClock(JsDate.create());
			}
		};
		timer.scheduleRepeating(REFRESH_RATE);

	}

	

	//private void updateClock(JsDate date) {
		//context.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
	//}

	public static native void tryToStart(Object obj) /*-{
	  $wnd.tryToStart(obj);
	}-*/;

	public static native void consoleLog(Object msg) /*-{
	  console.log(msg);
	}-*/;

	public static void createDialog(String message) {
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Keyboard input");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");

		dialogVPanel.add(new HTML("<b>" + message + ":</b>"));

		final TextBox inputField = new TextBox();
		dialogVPanel.add(inputField);

		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});

		dialogBox.setPopupPosition(100, 100);
		dialogBox.setModal(true);
		dialogBox.show();
	}
}
