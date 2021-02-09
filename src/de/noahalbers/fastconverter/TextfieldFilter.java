package de.noahalbers.fastconverter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public abstract class TextfieldFilter extends DocumentFilter{

	private EnumSystems system;
	private boolean running;
	
	public TextfieldFilter(EnumSystems system) {
		this.system = system;
		this.running=true;
	}

	/*
	 * Fires whenever a valid key is pressed
	 * */
	public abstract void onKey();
	
	/*
	 * Validates if the input can be used by the system
	 * */
	private boolean validate(String text) {
		return text.chars().allMatch(i->String.valueOf((char)i).matches(this.system.getValidRegex())||i==' ');
	}
	
	/*
	 * Stops the filter to insert text from the programm
	 * */
	public void stop() {
		this.running=false;
	}
	/*
	 * Starts the filter again
	 * */
	public void start() {
		this.running=true;
	}
	
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		//Converts the inputs to a string
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		//Checks if the string is valid for this system
		if (!this.validate(sb.toString()))
			return;
		
		//Executes the default instructions
		super.insertString(fb, offset, string, attr);
		//Fire the key event
		this.onKey();
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

		//Check if the filter should be running to prevent a inf loop
		if(!this.running) {
			super.replace(fb, offset, length, text, attrs);
			return;
		}
		
		//Converts the inputs to a string   
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);
	      
		//Checks if the string is valid for this system
		if (!this.validate(sb.toString()))
			return;
		
		//Executes the default instructions
		super.replace(fb, offset, length, text, attrs);
		//Fire the key event
		this.onKey();

	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		//Executes the default instructions
		 super.remove(fb, offset, length);
		//Fire the key event
		this.onKey();
	}
	
}
