import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.prefs.*;

public class Lab3 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JButton addButton;
	private JLabel addressLabel;
	private JPanel addressPanel;
	private JTextField addressTextField;
	private JPanel buttonPanel;
	private JLabel cityLabel;
	private JPanel cityStatePanel;
	private JTextField cityTextField;
	private JButton deleteButton;
	private JButton findButton;
	private JButton firstButton;
	private JLabel givenNameLabel;
	private JPanel givenNamePanel;
	private JTextField givenNameTextField;
	private JButton lastButton;
	private JButton nextButton;
	private JButton previousButton;
	private JLabel stateLabel;
	private JTextField stateTextField;
	private JLabel surnameLabel;
	private JPanel surnamePanel;
	private JTextField surnameTextField;
	private JButton updateButton;
	
	String bookFile = null;
	String indexFile = null;
	
	RandomAccessFile index; 
	RandomAccessFile book;

	public Lab3() {
		setTitle("Address Book");
		setBounds(100, 100, 704, 239);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new java.awt.GridLayout(5, 0));

		surnamePanel = new JPanel();
		surnameLabel = new JLabel();
		surnameTextField = new JTextField();
		givenNamePanel = new JPanel();
		givenNameLabel = new JLabel();
		givenNameTextField = new JTextField();
		addressPanel = new JPanel();
		addressLabel = new JLabel();
		addressTextField = new JTextField();
		cityStatePanel = new JPanel();
		cityLabel = new JLabel();
		cityTextField = new JTextField();
		stateLabel = new JLabel();
		stateTextField = new JTextField();
		buttonPanel = new JPanel();
		firstButton = new JButton();
		nextButton = new JButton();
		previousButton = new JButton();
		lastButton = new JButton();
		findButton = new JButton();
		addButton = new JButton();
		deleteButton = new JButton();
		updateButton = new JButton();

		surnamePanel.setName("surnamePanel");

		surnameLabel.setText("Surname");
		surnameLabel.setName("surnameLabel");
		surnamePanel.add(surnameLabel);

		surnameTextField.setColumns(45);
		surnameTextField.setText("");
		surnameTextField.setName("surnameTextField");
		surnamePanel.add(surnameTextField);

		getContentPane().add(surnamePanel);

		givenNamePanel.setName("givenNamePanel");

		givenNameLabel.setText("Given Names");
		givenNameLabel.setName("givenNameLabel");
		givenNamePanel.add(givenNameLabel);

		givenNameTextField.setColumns(45);
		givenNameTextField.setText("");
		givenNameTextField.setName("givenNameTextField");
		givenNamePanel.add(givenNameTextField);

		getContentPane().add(givenNamePanel);

		addressPanel.setName("addressPanel");

		addressLabel.setText("Street Address");
		addressLabel.setName("addressLabel");
		addressPanel.add(addressLabel);

		addressTextField.setColumns(45);
		addressTextField.setText("");
		addressTextField.setName("addressTextField");
		addressPanel.add(addressTextField);

		getContentPane().add(addressPanel);

		cityStatePanel.setName("cityStatePanel");

		cityLabel.setText("City");
		cityLabel.setName("cityLabel");
		cityStatePanel.add(cityLabel);

		cityTextField.setColumns(30);
		cityTextField.setText("");
		cityTextField.setName("cityTextField");
		cityStatePanel.add(cityTextField);

		stateLabel.setText("State");
		stateLabel.setName("stateLabel");
		cityStatePanel.add(stateLabel);

		stateTextField.setColumns(5);
		stateTextField.setText("");
		stateTextField.setName("stateTextField");
		cityStatePanel.add(stateTextField);

		getContentPane().add(cityStatePanel);

		buttonPanel.setName("buttonPanel");

		firstButton.setText("First");
		firstButton.setName("firstButton");
		firstButton.addActionListener(this);
		buttonPanel.add(firstButton);

		nextButton.setText("Next");
		nextButton.setName("nextButton");
		nextButton.addActionListener(this);
		buttonPanel.add(nextButton);

		previousButton.setText("Previous");
		previousButton.setName("previousButton");
		previousButton.addActionListener(this);
		buttonPanel.add(previousButton);

		lastButton.setText("Last");
		lastButton.setName("lastButton");
		lastButton.addActionListener(this);
		buttonPanel.add(lastButton);

		findButton.setText("Find");
		findButton.setName("findButton");
		findButton.addActionListener(this);
		buttonPanel.add(findButton);

		addButton.setText("Add");
		addButton.setEnabled(false);
		addButton.setName("addButton");
		addButton.addActionListener(this);
		buttonPanel.add(addButton);

		deleteButton.setText("Delete");
		deleteButton.setEnabled(false);
		deleteButton.setName("deleteButton");
		deleteButton.addActionListener(this);
		buttonPanel.add(deleteButton);

		updateButton.setText("Update");
		updateButton.setEnabled(false);
		updateButton.setName("updateButton");
		updateButton.addActionListener(this);
		buttonPanel.add(updateButton);

		getContentPane().add(buttonPanel);

//the Preferences Framework can store file locations between program invocations
		Preferences prefs = Preferences.userNodeForPackage( getClass() );
		bookFile = prefs.get("addressbook file", null);
		indexFile = prefs.get("index file", null);
		if (bookFile == null || indexFile == null ||
				!(new File(bookFile)).exists() || !(new File(indexFile)).exists()){
			getFiles();
		}
		
		try {
			index = new RandomAccessFile(indexFile, "r");
			book = new RandomAccessFile(bookFile, "r");
		} catch(IOException ioe) {
			System.out.println(ioe);
			System.exit(0);
		}

		prefs.put("addressbook file", bookFile);
		prefs.put("index file", indexFile);
	}
	
	void getFiles() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select the Address Book");
		int returnVal = fc.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			System.exit(0);
		bookFile = fc.getSelectedFile().getPath();
//the index file is usually in the same directory as the address book
		fc.setCurrentDirectory(new File(fc.getSelectedFile().getParent()));
		fc.setDialogTitle("Select the Index File");
		returnVal = fc.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			System.exit(0);
		indexFile = fc.getSelectedFile().getPath();
	}


	public static void main(String[] args) {
		Lab3 window = new Lab3();
		window.setVisible(true);
	}

/* COSC 20203 Techniques in Programming - Lab 3 - 04/03/2016
 * Name: Luke Reddick
 * This program takes two input files, an address book, and an alphabetized 
 * list of indices correlating to the address book, and allows the user
 * to sift through each individual in the address book using
 * 4 buttons: Next, Previous, First, and Last. The user it also able
 * to search for a name in the address book by typing a name into the 
 * Surname and Given Name text fields and pressing the Find button.
 */

	// Need to create some instance variables that are used in multiple sections of the program.
	
	long counter = 0;
	long indexLength = 0, bookLength = 0;
	long mid = 0;
	
	public void actionPerformed(ActionEvent evt) // Start by finding the lengths of each file, since we'll need to avoid an out of bounds exception.
	{
		long seeker;
		try
		{
			indexLength = index.length();
			bookLength = book.length();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		if(evt.getSource() == firstButton) // We use our variable "counter" to track where we are in the file at all times thoughout the program.
		{
			counter = 0;
			try
			{
				index.seek(counter);		// Since we select "first" counter is set to 0 and we seek there, read the index, seek to that index, and display the data. 
				seeker = index.readLong();
				book.seek(seeker);
				surnameTextField.setText(book.readUTF());
				givenNameTextField.setText(book.readUTF());
				addressTextField.setText(book.readUTF());
				cityTextField.setText(book.readUTF());
				stateTextField.setText(book.readUTF());
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		else if(evt.getSource() == previousButton) // Remember a long primitive type is 8 bytes long so whenever we sift through the index file, counter needs to change by a margin of 8.
		{
			counter -= 8;							// We chose "previous" thus we decrement counter by 8; however, if this puts us before the first index (i.e. it's negative, cancel it out by incrementing instead).
			if(counter < 0)
			{
				counter += 8;
			}
			else
			{	
				try
				{
					index.seek(counter);		// Rinse and repeat, seek to our tracker variable "counter", read that index, seek to that index in the book file, and display the data. 
					seeker = index.readLong();
					book.seek(seeker);
					surnameTextField.setText(book.readUTF());
					givenNameTextField.setText(book.readUTF());
					addressTextField.setText(book.readUTF());
					cityTextField.setText(book.readUTF());
					stateTextField.setText(book.readUTF());
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
		else if(evt.getSource() == nextButton)	
		{
			counter += 8;					// Increment counter by 8 since we chose "next", if that puts us beyond the file length, then invert the operation by decrementing by 8. 
			if(counter >= indexLength)
			{
				counter -= 8;
			}
			else
			{	
				try
				{
					index.seek(counter);		// Rinse and repeat, seek to our "counter" variable, read in that index, seek to that index in the book file, and display data.
					seeker = index.readLong();
					book.seek(seeker);
					surnameTextField.setText(book.readUTF());
					givenNameTextField.setText(book.readUTF());
					addressTextField.setText(book.readUTF());
					cityTextField.setText(book.readUTF());
					stateTextField.setText(book.readUTF());
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
		else if(evt.getSource() == lastButton)
		{
			counter = indexLength - 8;		// For the last index, we don't seek to the last byte, but rather the last 8 since we actually want to read in the last 'long' value. 
			try
			{
				index.seek(counter);
				seeker = index.readLong();
				book.seek(seeker);
				surnameTextField.setText(book.readUTF());
				givenNameTextField.setText(book.readUTF());
				addressTextField.setText(book.readUTF());
				cityTextField.setText(book.readUTF());
				stateTextField.setText(book.readUTF());
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		else if(evt.getSource() == findButton)		// Build the string we're going to look in the address book for, and run a binary search method to return the index where it should be located at in the address file. 
		{
			String surname = surnameTextField.getText().trim();
			String givenName = givenNameTextField.getText().trim();
			String fullName = surname + givenName;
			long nameIndex = searchIndex(fullName);		// Use the index given from the binary search and seek to that index and display the according information.
			try
			{
				if(nameIndex < 0)
				{
					book.seek(-1 * nameIndex);
				}
				else if(nameIndex >= 0)
				{
					book.seek(nameIndex);
				}
				surnameTextField.setText(book.readUTF());
				givenNameTextField.setText(book.readUTF());
				addressTextField.setText(book.readUTF());
				cityTextField.setText(book.readUTF());
				stateTextField.setText(book.readUTF());
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			counter = mid * 8;			// After running a binary search, we arrive at the middle element and this is the location of our index in the index file, thus we update the counter variable to stay on track.
		}
	}
	public long searchIndex(String n)	// Pretty standard binary search algorithm but in this case we have to binary search through the index file by converting to its address book component, comparing, and then adjusting the remainder of the index file to search through.
	{
		long nameSeeker = 0;
		try
		{
			long numLongs = index.length() / 8;	// We don't search though every individual byte, we are interested in the indices (longs) thus we are talking about elements of length 8.
			long start = 0;
			long end = numLongs;
			mid = (end + start) / 2;			// Standard binary search variables start, mid, and end, where end is the amount of longs in the index file. 
			while(start <= end)
			{
				index.seek(mid * 8);
				nameSeeker = index.readLong();			// nameSeeker is the variable that will hold the index we want to seek to after the binary search is completed. 
				book.seek(nameSeeker);
				String checkString = (book.readUTF() + book.readUTF()).trim();			// Create a variable that holds the value of the names at that seek location so that the value is not changed across if statements. 
				if(n.compareToIgnoreCase(checkString) < 0)
				{
					end = mid - 1;	
				}
				if(n.compareToIgnoreCase(checkString) > 0)
				{
					start = mid + 1;					
				}
				if(n.compareToIgnoreCase(checkString) == 0) 		// If we found the exact string, break out of the loop. Return the latest value of nameSeeker and we're done here. 
				{
					break;	
				}
				mid = (start + end) / 2;
			}
			if(start > end)
			{
				return -1 * nameSeeker;						// If the string does not exist, this case will likely happen. Return a negative value of the nameSeeker index to indicate this. 
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		return nameSeeker;
	}

}
