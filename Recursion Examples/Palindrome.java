import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Palindrome extends JApplet implements ActionListener{

	static final long serialVersionUID = 1;
	JButton findPalindrome = new JButton("Is this word a palindrome?");
	JTextField inputPhrase = new JTextField(10);
	JTextField result = new JTextField(10);
	JLabel input = new JLabel("Phrase Input");
	JLabel output = new JLabel("Is Palindrome:");
	String textIn;
	String textOut;
	
	public void init()
	{
		super.init();
		setSize(600, 75);
		setLayout(new FlowLayout());
		findPalindrome.addActionListener(this);
		add(input);
		add(inputPhrase);
		add(output);
		add(result);
		add(findPalindrome);
	}
	public void actionPerformed(ActionEvent e)
	{
		textIn = inputPhrase.getText();
		
		if(e.getSource() == findPalindrome)
		{
			boolean isPalindrome = palindrome(textIn);
			if(isPalindrome)
				{
					result.setText("True");
				}
			else if(!isPalindrome)
				{
					result.setText("False");
				}
		}
	}
	public boolean palindrome(String s)
	{
			if(s.length() == 0) return true;
			if(s.length() == 1) return true;
			return (s.charAt(0) == s.charAt(s.length() -1)) && palindrome(s.substring(1, s.length() -1));	
	}

}
