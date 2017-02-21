import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;


public class Lab2 extends JFrame implements ActionListener {
	JTextArea result = new JTextArea(20,54);
	JLabel errors = new JLabel();
	JScrollPane scroller = new JScrollPane();
	JButton openButton = new JButton("Get File");
	JButton encryptButton = new JButton("Encrypt");
	JButton decryptButton = new JButton("Decrypt");
	JPanel buttonPanel = new JPanel();
	JLabel phraseLabel = new JLabel("Secret Phrase:");
	JTextField phraseTF = new JTextField(40);
	JPanel phrasePanel = new JPanel();
	JPanel southPanel = new JPanel();
	
	public Lab2() {
		setLayout(new java.awt.BorderLayout());
		setSize(700,430);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		scroller.getViewport().add(result);
		result.setLineWrap(true);
		result.setWrapStyleWord(true);
		add(scroller, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new java.awt.GridLayout(3,1));
		southPanel.add(buttonPanel);
		buttonPanel.add(openButton); openButton.addActionListener(this);
		buttonPanel.add(encryptButton); encryptButton.addActionListener(this);
		buttonPanel.add(decryptButton); decryptButton.addActionListener(this);
		southPanel.add(phrasePanel);
		phrasePanel.add(phraseLabel);
		phrasePanel.add(phraseTF);
		southPanel.add(errors);
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == openButton) {
			getFile();
		}
		else if (evt.getSource() == encryptButton) {
			encrypt();
		}
		else if (evt.getSource() == decryptButton) {
			decrypt();
		}
	}
	
	public static void main(String[] args) {
		Lab2 display = new Lab2();
		display.setVisible(true);
	}
	
	//display a file dialog
	String getFileName() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile().getPath();
		else
			return null;
	}
	
	//read the file and display it in the text area
	void getFile() {
		String fileName = getFileName();
		if (fileName == null)
			return;
		result.setText("");
		try{
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = in.readLine()) != null) {
				result.append(line + "\n");
			}
			in.close();
		} catch(IOException ioe) {
			errors.setText("ERROR: " + ioe);
		}
	}
	
/*******************************************************************************
 * Luke Reddick -- March 1st 2016 -- Techniques in Programming -- Dr. Rinewalt
 * 
 * Lab 2 - Playfair's Cipher
 * 
 * This program takes an input text file or, alternatively, input text and 
 * encrypts it using a 9x9 matrix filled with 81 characters. These characters
 * come from the assigned list and the pass phrase given by the user, without
 * duplicates. This program also decrypts the text using the same methods
 * but obviously in reverse. 
 * 
 * Important Variables:
 * 
 * cipherMatrix - the 9x9 2D array containing the characters to be used 
 * textFromFile - the input text made into a String without invalid chars
 * outputString - the text that contains the encypted/decrypted message
 * char1/char2 - the characters gathered each iteration of the input
 * char1i/char1j - the coordinates of char1 in the cipher matrix
 * char2i/char2j - the coordinates of char2 in the cipher matrix
 * lastChar - the final character in the input if the string length is an odd #
 * 
*******************************************************************************/
	void encrypt() 
	{
		
		// Initialize variables and create a string that will be passed into a 2D array.
		
		char[][] cipherMatrix = new char[9][9];	
		String passPhrase = phraseTF.getText();
		String chars81 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 '()*+,-./:;<=>[]^?";
		String editPassPhrase = "";
		
		for(int i = 0; i < passPhrase.length(); i++)
		{
			char c = passPhrase.charAt(i);
			if(chars81.indexOf(c) == -1)
			{
				editPassPhrase += "";
			} 
			else if(chars81.indexOf(c) >=0)
			{
				editPassPhrase += c;
			}
		}
		
		phraseTF.setText(editPassPhrase);
				
		String cipherChars = editPassPhrase + chars81;
		String textFromFile;
		char char1, char2;
		int char1i = -1, char1j = -1, char2i = -1, char2j = -1;
		String outputString = "";
		
		// Remove duplicates in the string in a linked hash set and then build the string together using StringBuilder.
		
		char[] chars = cipherChars.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
		    charSet.add(c);
		}

		StringBuilder sb = new StringBuilder();
		for (Character character : charSet) {
		    sb.append(character);
		}
		
		// Add elements with the entered pass phrase into the 9x9 cipher matrix.
		
		String newPassPhrase = sb.toString();	
		int counter = 0;
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				
				cipherMatrix[i][j] = newPassPhrase.charAt(counter);
				counter++;
			}
		}
		
		textFromFile = result.getText();
		
		// Iterate through input text and remove all characters that do not exist in the cipher matrix. 
		
		for(int x = 0; (x < textFromFile.length()); x++)
		{
			char1 = textFromFile.charAt(x);

			// Set the char coordinates back to -1 so that values of old chars do not carry through iteration and we start fresh each time. 
			
			char1i = -1;
			char1j = -1;

			for (int i = 0 ; i < 9; i++)
			    for(int j = 0 ; j < 9; j++)
			    {
			    	if(cipherMatrix[i][j] == char1)
			    	{
			    		char1i = i;
			    		char1j = j;
			    	}
			    }
			
			if(char1i == -1 && char1j == -1) // If  character is not in the matrix, remove it from the input.
			{
				String charString = Character.toString(char1);
				textFromFile = textFromFile.replace(charString, "");
			}
		}
	
		// Loop through the revised input text grabbing two chars at a time, then iterate through the 9x9 matrix to find the i,j coordinate of the chars.
		
		for(int x = 0; (x < textFromFile.length() - 1); x += 2)
		{
			char1 = textFromFile.charAt(x);
			char2 = textFromFile.charAt(x+1);

			for (int i = 0 ; i < 9; i++)
			    for(int j = 0 ; j < 9; j++)
			    {
			    	if(cipherMatrix[i][j] == char1)
			    	{
			    		char1i = i;
			    		char1j = j;
			    	}
			    	if(cipherMatrix[i][j] == char2)
			    	{
			    		char2i = i;
			    		char2j = j;
			    	}
			    }				
			// Use if statements to determine the different options of the char placement in the matrix then encrypt accordingly.

			if(char1i != char2i && char1j != char2j) // i.e. The chars make a rectangle in the matrix
				{
					int temp = char1j;
					char1j = char2j;
					char2j = temp;
				
					outputString += (cipherMatrix[char1i][char1j] + "" + cipherMatrix[char2i][char2j]);
				}
			else if(char1i == char2i && char2j != char1j) // Chars found in same row but not same column - move elements to the right.
				{
					char1j = (char1j + 1) % 9;
					char2j = (char2j + 1) % 9;
				
					outputString += (cipherMatrix[char1i][char1j] + "" + cipherMatrix[char2i][char2j]);
				}
			else if(char1j == char2j && char1i != char2i)	// Chars found in the same column but not same row - move elements down.
				{
					char1i = (char1i + 1) % 9;
					char2i = (char2i + 1) % 9;
				
					outputString += (cipherMatrix[char1i][char1j] + "" + cipherMatrix[char2i][char2j]);
				}
			else if(char1i == char2i && char2j == char1j) // Chars found in the same location i.e chars are equal - return the input without change.
				{
					outputString += (cipherMatrix[char1i][char1j] + "" + cipherMatrix[char2i][char2j]);
				}
			}
		
		// Since we iterated in increments of two, we have to include the last char if the length of the text was an odd number. 
		
		if(textFromFile.length() % 2 == 1)
		{
				char lastChar = textFromFile.charAt(textFromFile.length() - 1);		
				outputString += lastChar;		
		}
		
		result.setText(outputString);
	}

	void decrypt() // Code is essentially copy paste from the encrypt method, with minor changes in the input/output 
	{
		
		// Initialize variables and create a string that will be passed into a 2D array.
		
		char[][] cipherMatrix = new char[9][9];	
		String passPhrase = phraseTF.getText();
		String chars81 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 '()*+,-./:;<=>[]^?";
		String editPassPhrase = "";
		
		for(int i = 0; i < passPhrase.length(); i++)
		{
			char c = passPhrase.charAt(i);
			if(chars81.indexOf(c) == -1)
			{
				editPassPhrase += "";
			} 
			else if(chars81.indexOf(c) >=0)
			{
				editPassPhrase += c;
			}
		}
		
		phraseTF.setText(editPassPhrase);
		
		String cipherChars = editPassPhrase + chars81;
		String textFromFile;
		char char1, char2;
		int char1i = -1, char1j = -1, char2i = -1, char2j = -1;
		String outputString = "";
		
		// Remove duplicates in the string in a linked has set and then build the string together using StringBuilder.
		
		char[] chars = cipherChars.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
		    charSet.add(c);
		}

		StringBuilder sb = new StringBuilder();
		for (Character character : charSet) {
		    sb.append(character);
		}
		
		// Add elements with the entered pass phrase into the 9x9 cipher matrix.
		
		String newPassPhrase = sb.toString();	
		int counter = 0;
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				
				cipherMatrix[i][j] = newPassPhrase.charAt(counter);
				counter++;
			}
		}
		
		textFromFile = result.getText();
		
		// Iterate through input text and remove all characters that do not exist in the cipher matrix. 
		
		for(int x = 0; (x < textFromFile.length()); x++)
		{
			char1 = textFromFile.charAt(x);

			// Set the char coordinates back to -1 so that values of old chars do not carry through iteration and we start fresh each time. 
			
			char1i = -1;
			char1j = -1;

			for (int i = 0 ; i < 9; i++)
			    for(int j = 0 ; j < 9; j++)
			    {
			    	if(cipherMatrix[i][j] == char1)
			    	{
			    		char1i = i;
			    		char1j = j;
			    	}
			    }
			
			if(char1i == -1 && char1j == -1) // If  character is not in the matrix, remove it from the input.
			{
				String charString = Character.toString(char1);
				textFromFile = textFromFile.replace(charString, "");
			}
		}
		
		// Loop through the text grabbing two chars at a time, then iterate through the 9x9 matrix to find the i,j coordinate of the chars.
		
		for(int x = 0; (x < textFromFile.length() - 1); x += 2)
		{
			char1 = textFromFile.charAt(x);
			char2 = textFromFile.charAt(x+1);

			for (int i = 0 ; i < 9; i++)
			    for(int j = 0 ; j < 9; j++)
			    {
			    	if(cipherMatrix[i][j] == char1)
			    	{
			    		char1i = i;
			    		char1j = j;
			    	}
			    	if(cipherMatrix[i][j] == char2)
			    	{
			    		char2i = i;
			    		char2j = j;
			    	}
			    }
				
			// Use if statements to determine the different options of the char placement in the matrix then encrypt accordingly.

			if(char1i != char2i && char1j != char2j) // i.e. The chars make a rectangle in the matrix
				{
					int temp = char1j;
					char1j = char2j;
					char2j = temp;
				
					outputString += (cipherMatrix[char1i][char1j] + "" + cipherMatrix[char2i][char2j]);
				}
			else if(char1i == char2i && char2j != char1j) // Chars found in same row but not same column - move elements to the right.
				{
					char1j = (9 + (char1j - 1)) % 9;
					char2j = (9 + (char2j - 1)) % 9;
				
					outputString += (cipherMatrix[char1i][char1j] + "" + cipherMatrix[char2i][char2j]);
				}
			else if(char1j == char2j && char1i != char2i)	// Chars found in the same column but not same row - move elements down.
				{
					char1i = (9 + (char1i - 1)) % 9;
					char2i = (9 + (char2i - 1)) % 9;
				
					outputString += (cipherMatrix[char1i][char1j] + "" + cipherMatrix[char2i][char2j]);
				}
			else if(char1i == char2i && char2j == char1j) // Chars found in the same location i.e chars are equal - return the input without change.
				{
					outputString += (cipherMatrix[char1i][char1j] + "" + cipherMatrix[char2i][char2j]);
				}
			}
		
		// Since we iterated in increments of two, we have to include the last char if the length of the text was an odd number. 
		
			if(textFromFile.length() % 2 == 1)
			{
				char lastChar = textFromFile.charAt(textFromFile.length() - 1);		
				outputString += lastChar;		
			}	
		result.setText(outputString); 
	}
}