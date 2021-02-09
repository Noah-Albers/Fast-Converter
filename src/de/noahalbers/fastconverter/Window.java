package de.noahalbers.fastconverter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	//Array with all textfields
	private JTextField[] textfields;
	//Array with all buttons to the textfield
	private JButton[] buttons;
	//Global font for all objects
	private Font globalfont;

	public Window(Font globalfont) {
		this.globalfont = globalfont;
	}
	
	/*
	 * Creates the default Window
	 * */
	public void init() {
		//The Programm stopps, when the windows is closed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Sets a static size for the window
		this.setSize(450,50+EnumSystems.values().length*65+120);
		//The Window should open in the middle of the screen
		this.setLocationRelativeTo(null);
		//Make the user unable to resize the window
		this.setResizable(false);
		//Sets the window title
		this.setTitle("Fast Converter by Noah Albers");
		//Creates the pane
		JPanel pane = new JPanel();
		//Adds the layout to nothing
		pane.setLayout(null);
		//Sets some cosmetic assets
		pane.setBackground(new Color(80, 82, 85));
		//Sets the pane as the pane of the frame
		this.setContentPane(pane);
		
		//Creates the objects array
		this.textfields = new JTextField[EnumSystems.values().length];
		this.buttons = new JButton[EnumSystems.values().length];
	}
	
	/*
	 * Finished the window
	 * */
	public void finishInit() {
		//Makes the window appeare on the screen
		this.setVisible(true);
	}
	
	/*
	 * Creates all textfields
	 * */
	public void initTextFields() {
		//Creates the textfield on the array
		for(int i = 0; i < EnumSystems.values().length; i++) {
			
			//Gets the current system
			EnumSystems system = EnumSystems.values()[i];
			
			//Creates a new field
			JTextField field = new JTextField();
			//Sets the position of that field on the window
			field.setBounds(15, 15+i*65, 300, 45);
			//Sets some cosmetic assets
			field.setToolTipText(system.getName());
			field.setFont(this.globalfont);
			//Creates the filter
			TextfieldFilter filter = this.createFilter(system, field);
			//Sets the filter to the document
			((PlainDocument) field.getDocument()).setDocumentFilter(filter);
			//Adds the field to the window
			this.getContentPane().add(field);
			//Adds the field to the array of textfields
			this.textfields[i] = field;
		}
		
	}
	
	/*
	 * Creates all buttons
	 * */
	public void initButtons() {
		//Creates the buttons on the array
		for(int i = 0; i < EnumSystems.values().length; i++) {
			//Creates the button
			JButton btn = new JButton();
			//Gets the field to the button
			JTextField field = this.textfields[i];
			//Gets the system
			EnumSystems system = EnumSystems.values()[i];
			//Sets the buttons position and size on the window
			btn.setBounds(330, 15+(i)*65, 95, 45);
			//Sets some cosmetic assets
			btn.setText(system.getName());
			btn.setFont(this.globalfont);
			btn.setOpaque(true);
			btn.setBorderPainted(false);
			btn.setBackground(Color.green);
			//Sets the buttons function
			btn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					copyString(field.getText());
				}
			});
			//Adds the button to the pain
			this.getContentPane().add(btn);
			//Adds the button to the array
			this.buttons[i]=btn;
		}
	}

	/*
	 * Creates some settings
	 * */
	public void initSettings() {
		/*
		 * Top is a checkbox that askes if the window should be always on top
		 * */
		JCheckBox top = new JCheckBox("Top");
		//Sets some cosmetic assets
		top.setFont(this.globalfont);
		top.setBounds(15,15+EnumSystems.values().length*65,90,29);
		//Adds the changelistener
		top.addChangeListener(evt->{
			//Sets the frame alwaysontop if the box is selected
			this.setAlwaysOnTop(top.isSelected());
		});
		//Adds top to the contant pane
		this.getContentPane().add(top);
	}
	
	/*
	 * Copys strings to the clipboard
	 */
	public void copyString(String value) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
	}
	
	
	/*
	 * Used by initTextfields
	 * 
	 * Creates a filter for the textfield
	 * */
	private TextfieldFilter createFilter(EnumSystems system,JTextField field) {
		
		//Returns the creation of the field
		return new TextfieldFilter(system) {
			//Fires on every valid key event
			@Override
			public void onKey() {
				
				Long[] decimals;
				
				try {
					//Gets all decimals from the given input
					decimals = field.getText().isEmpty()?new Long[0]:system.getOutconverter().apply(field.getText().split(" "));
				} catch (Exception e) {
					//If any error occures it returns
					//For example the number is to big to fit into an long
					System.out.println("An error occured: "+e.getLocalizedMessage());
					return;
				}
				
				//Loops through all systems and sets the field values
				for(int x = 0; x < EnumSystems.values().length; x++) {
					//Gets the current system
					EnumSystems loopSystem = EnumSystems.values()[x];
					//Gets the current field
					JTextField loopfield = textfields[x];
					//Checks if the current system is the same as the fields system
					if(loopSystem.equals(system))
						continue;
					
					TextfieldFilter loopdocument = (TextfieldFilter)((PlainDocument) loopfield.getDocument()).getDocumentFilter();
					
					//Sets the text
					loopdocument.stop();//Stops the filter
					loopfield.setText(loopSystem.getInconverter().apply(decimals));
					loopdocument.start();//Starts the filter
				}
			}
		};
	}
}
