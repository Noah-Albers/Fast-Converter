package de.noahalbers.fastconverter;

import java.awt.Font;

import de.noahalbers.fastconverter.utils.NumberUtils;

public class Main {

	public static void main(String[] args) {
		//Creates all utils
		new NumberUtils();
		
		//Creates the window and sets all needed propertys
		Window win = new Window(new Font("Franklin Gothic Demi", Font.PLAIN, 17));
		win.init(); //Inits the global window
		win.initTextFields(); //Adds the textfields
		win.initButtons(); //Adds the buttons
		win.initSettings();//Adds some settings to the window
		win.finishInit(); //Finishes the init
	
	}
	
}
