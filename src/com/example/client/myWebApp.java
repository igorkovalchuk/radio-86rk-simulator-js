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
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
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

		Button loadButton = new Button("Load");
		loadButton.setStyleName("my-button", true);
		RootPanel.get().add(loadButton);

		Button saveButton = new Button("Save");
		saveButton.setStyleName("my-button", true);
		RootPanel.get().add(saveButton);

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
		ta.setText("cls();" + "\n" 
				+ "plot(0, 0, 1);" + "\n" 
				+ "line(49, 49);" + "\n" 
				+ "line(49, 0);" + "\n"
				+ "line(0, 0);" + "\n"
				+ "\n"
				+ "circle(24, 24, 20);"
				+ "\n");

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
		
		ImageElement imageElement = ImageElement.as(DOM.getElementById("rk_font1"));
		ComputerModelIntf computerModel = new ComputerModel(parameters);
		UserInterfaceIntf ui = new UserInterfaceImpl(context, imageElement, computerModel);
		Radio86rkAPI.createInstance(computerModel, ui);

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
				tp.selectTab(0);
				tryToStart(program);
			}
		});

		loadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String key = "program1";
				Storage storage = Storage.getLocalStorageIfSupported();
				if (storage == null) {
					return;
				}
				String program = storage.getItem(key);
				if (program == null || program.trim().isEmpty()) {
					Window.alert("Can't find any saved data.\nPlease consider to backup your program to the Notes from time to time");
					return;
				}
				String current = ta.getText();
				if (!current.trim().isEmpty() && !Window.confirm("Replace the current program?")) {
					return;
				}
				ta.setText(program);
			}
		});

		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String program = ta.getText();
				String key = "program1";
				Storage storage = Storage.getLocalStorageIfSupported();
				if (storage == null) {
					Window.alert("Pardon, but 'save' is not supported in this browser");
					return;
				}
				if (program.trim().isEmpty()) {
					Window.alert("Could you please input some text");
					return;
				}
				storage.setItem(key, program);
			}
		});

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

		//final Timer timer = new Timer() {
			//@Override
			//public void run() {
				// updateClock(JsDate.create());
			//}
		//};
		//timer.scheduleRepeating(REFRESH_RATE);

	}

	//private void updateClock(JsDate date) {
		//context.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
	//}

	public static native void tryToStart(Object obj) /*-{
	  $wnd.tryToStart(obj);
	}-*/;

}
