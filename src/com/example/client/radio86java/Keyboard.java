package com.example.client.radio86java;

import java.util.LinkedList;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL)
public class Keyboard {

	public static final String ENTER = "\n";
	public static final String UP = "UP";
	public static final String DOWN = "DOWN";
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";

	private static String[] k1 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
	private static String[] k2 = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
	private static String[] k3 = new String[]{"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};
	private static String[] k4 = new String[]{"U", "V", "W", "X", "Y", "Z", "ENTER"};
	private static String[] k5 = new String[]{UP, DOWN, LEFT, RIGHT};
	private static String[] k6 = new String[]{"SPACE", "SPACE", "SPACE", "SPACE"};

	public final static Object[] keyboard = new Object[] {k1, k2, k3, k4, k5, k6};

	public static final LinkedList<String> queue = new LinkedList<>();

	public static void reset() {
		queue.clear();		
	}

	public static void press(String key) {
		while (queue.size() > 0) {
			queue.removeFirst();
		}

		if (key.equals("ENTER")) {
			queue.add("\n");
		}
		else if (key.equals("SPACE")) {
			queue.add(" ");
		} 
		else {
			queue.add(key);
		}
	}

	public static String get() {
		if (queue.size() == 0) {
			return "";
		}
		return queue.removeFirst();
	}
}
