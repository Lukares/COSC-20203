import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class LAb1 extends JFrame implements ActionListener {
	private JTextField assemblerInstruction;
	private JTextField binaryInstruction;
	private JTextField hexInstruction;
	private JLabel errorLabel;
	
	public LAb1() {
		setTitle("IBM System/360");
		setBounds(100, 100, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		assemblerInstruction = new JTextField();
		assemblerInstruction.setBounds(25, 24, 134, 28);
		getContentPane().add(assemblerInstruction);

		JLabel lblAssemblyLanguage = new JLabel("Assembly Language");
		lblAssemblyLanguage.setBounds(30, 64, 160, 16);
		getContentPane().add(lblAssemblyLanguage);

		JButton btnEncode = new JButton("Encode");
		btnEncode.setBounds(200, 25, 117, 29);
		getContentPane().add(btnEncode);
		btnEncode.addActionListener(this);
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		binaryInstruction = new JTextField();
		binaryInstruction.setBounds(25, 115, 330, 28);
		getContentPane().add(binaryInstruction);

		JLabel lblBinary = new JLabel("Binary Instruction");
		lblBinary.setBounds(30, 155, 190, 16);
		getContentPane().add(lblBinary);

		JButton btnDecode = new JButton("Decode Binary");
		btnDecode.setBounds(200, 150, 150, 29);
		getContentPane().add(btnDecode);
		btnDecode.addActionListener(this);
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		hexInstruction = new JTextField();
		hexInstruction.setBounds(25, 220, 134, 28);
		getContentPane().add(hexInstruction);

		JLabel lblHexEquivalent = new JLabel("Hex Instruction");
		lblHexEquivalent.setBounds(30, 260, 131, 16);
		getContentPane().add(lblHexEquivalent);

		JButton btnDecodeHex = new JButton("Decode Hex");
		btnDecodeHex.setBounds(200, 220, 150, 29);
		getContentPane().add(btnDecodeHex);
		btnDecodeHex.addActionListener(this);		
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		errorLabel = new JLabel("");
		errorLabel.setBounds(25, 320, 280, 16);
		getContentPane().add(errorLabel);
	}

	public void actionPerformed(ActionEvent evt) {
		errorLabel.setText("");
		if (evt.getActionCommand().equals("Encode")) {
			encode();
		} else if (evt.getActionCommand().equals("Decode Binary")) {
			decodeBin();
		} else if (evt.getActionCommand().equals("Decode Hex")) {
			decodeHex();
		}
	}

	public static void main(String[] args) {
		LAb1 window = new LAb1();
		window.setVisible(true);
	}

	String displayShortAsHex(short x) {
		String ans="";
		for (int i=0; i<4; i++) {
			int hex = x & 15;
			char hexChar = "0123456789ABCDEF".charAt(hex);
			ans = hexChar + ans;
			x = (short)(x >> 4);
		}
		return ans;
	}

	String displayShortAsBinary(short x) {
		String ans="";
		for(int i=0; i<16; i++) {
			ans = (x & 1) + ans;
			x = (short)(x >> 1);
		}
		return ans;
	}
	
	String displayIntAsHex(int x) {
		String ans="";
		for (int i=0; i<8; i++) {
			int hex = x & 15;
			char hexChar = "0123456789ABCDEF".charAt(hex);
			ans = hexChar + ans;
			x = (x >> 4);
		}
		return ans;
	}

	String displayIntAsBinary(int x) {
		String ans="";
		for(int i=0; i<32; i++) {
			ans = (x & 1) + ans;
			x = (x >> 1);
		}
		return ans;
	}
	
/************************************************************************/
/* Put your implementation of the encode, decodeBin, and decodeHex      */
/* methods here. You may add any other methods that you think are       */
/* appropriate. However, you should not change anything in the code     */
/* that I have written.                                                 */
/************************************************************************/
/* Techniques in Programming - Dr. Rinewalt - Lab 1 - 02/05/2016 
 * Program written by: Luke Reddick
 * 
 * This program opens up a simple GUI that allows a user to input an assembly
 * language instruction or a binary/hexadecimal code for that assembly
 * language instruction. The user can choose to encode the assembly text into 
 * binary and hexadecimal and likewise decode the binary and hexadecimal
 * values into the appropriate assembly language instruction. */
	
	void encode() 
	{
		int r = 0,b = 0, d = 0, s = 0, x = 0, biImp = 0;
		short sho = 0;
		boolean booleanErr = false;
		StringTokenizer tok = new StringTokenizer(assemblerInstruction.getText(), " ,()");
		int tokElements = tok.countTokens();
		if (tokElements == 5) 
		{
			String assemblyText = tok.nextToken();
			switch (assemblyText) 	// Grab the assembly language command, add its value to the integer to be converted, bit shift the integer to make room for more data storage. 
			{
				case "L": 
					biImp = biImp + 88;
					biImp = biImp << 4;	
				break;
				case "ST":
					biImp = biImp + 80;
					biImp = biImp << 4;
				break;
				case "AR":
					biImp = biImp + 26;
					biImp = biImp << 4;
				break;
				case "A":
					biImp = biImp + 90;
					biImp = biImp << 4;
				break;
			}
			
			if (!(assemblyText.equals("L")) && !(assemblyText.equals("AR")) && !(assemblyText.equals("ST")) && !(assemblyText.equals("A")))
			{
				booleanErr = true;
			}
			
			assemblyText = tok.nextToken();  // Grab number by number and put is binary value into the integer and bit shift according to the storage for the next value. 
			
			try 
			{
				r = Integer.parseInt(assemblyText);
				biImp = biImp | r;
				biImp = biImp << 4;
		
				assemblyText = tok.nextToken();
				d = Integer.parseInt(assemblyText);
		
				assemblyText = tok.nextToken();
				x = Integer.parseInt(assemblyText);
				biImp = biImp | x;
				biImp = biImp << 4;
		
				assemblyText = tok.nextToken();
				b = Integer.parseInt(assemblyText);
				biImp = biImp | b;
				biImp = biImp << 12;
		
				biImp = biImp | d;
			}
			
			catch(NumberFormatException e)
			{
				booleanErr = true;
			}
			
			if (r < 0 || r > 15) // Check to make sure all numbers are within their assembly instruction boundaries. 
			{
				booleanErr = true;
			}
			else if (x < 0 || x > 15)
			{
				booleanErr = true;
			}
			else if (b < 0 || b > 15)
			{
				booleanErr = true;
			}
			else if (d < 0 || d > 4095)
			{
				booleanErr = true;
			}
			
			if (booleanErr)
			{
				binaryInstruction.setText("ERROR");
				hexInstruction.setText("ERROR");
			}
			else
			{
				binaryInstruction.setText(displayIntAsBinary(biImp));
				hexInstruction.setText(displayIntAsHex(biImp));
			}
		}
		else if (tokElements == 3) // Essentially copy previous code but use the short data type for the smaller assembly language instruction. 
		{
			String assemblyText = tok.nextToken();
			switch (assemblyText) 
			{
				case "L": 
					sho = (short)(sho + 88);
					sho = (short)(sho << 4);	
				break;
				case "ST":
					sho = (short)(sho + 80);
					sho = (short)(sho << 4);
				break;
				case "AR":
					sho = (short)(sho + 26);
					sho = (short)(sho << 4);
				break;
				case "A":
					sho = (short)(sho + 90);
					sho = (short)(sho << 4);
				break;
			}

			if (!(assemblyText.equals("L")) && !(assemblyText.equals("AR")) && !(assemblyText.equals("ST")) && !(assemblyText.equals("A")))
			{
				booleanErr = true;
			}
			
			try
			{
				assemblyText = tok.nextToken();
				r = Short.parseShort(assemblyText);
				sho = (short)(sho | r);
				sho = (short)(sho << 4);
			
				assemblyText = tok.nextToken();
				s = Short.parseShort(assemblyText);
				sho = (short)(sho | s);		
			}
			
			catch(NumberFormatException e)
			{
				booleanErr = true;
			}
			
			if (s < 0 || s > 15)	// Check to make sure all numbers are within their assembly instruction boundaries.
			{
				booleanErr = true;
			}
			else if (r < 0 || r > 15)
			{
				booleanErr = true;
			}
			
			if (booleanErr)	// If any exceptions were caught through the if statements then this segment will run and display the ERROR messages. 
			{
				binaryInstruction.setText("ERROR");
				hexInstruction.setText("ERROR");
			}
			else
			{
				binaryInstruction.setText(displayShortAsBinary(sho));
				hexInstruction.setText(displayShortAsHex(sho));
			}
		}
		else 
		{
			binaryInstruction.setText("ERROR");
			hexInstruction.setText("ERROR");
		}
	}

	void decodeBin() 
	{
		String binNum = binaryInstruction.getText();
		String assemblyChar = "";
		String decodedInst = "";
		boolean booleanErr = false;
		int deBin = 0, assemblyInt = 0;
		int D = 0, B = 0, X = 0, S = 0, R = 0;
		if (binNum.length() == 32)
		{
			for(int i = 0; i<32; i++) // Convert the binary string into an integer
			{
				if (binNum.charAt(31 - i) == '1')
				{
					deBin += Math.pow(2,  i);
				}
				else if (binNum.charAt(31 - i) == '0')
				{
					deBin += 0;
				}
				else	// If any of the binary string digits are something other than a 1 or a 0, display an ERROR message. 
				{
					booleanErr = true;
				}
			}
			for(int i = 0; i<12;i++) // Decode value for D
			{		
				if((deBin & 1) == 1)
				{
					D += Math.pow(2, i);
				}
				deBin = (deBin >>> 1);
			}
			for(int i = 0; i<4;i++) // Decode value for B
			{		
				if((deBin & 1) == 1)
				{
					B += Math.pow(2, i);
				}
				deBin = (deBin >>> 1);
			}
			for(int i = 0; i<4;i++) // Decode value for X
			{		
				if((deBin & 1) == 1)
				{
					X += Math.pow(2, i);
				}
				deBin = (deBin >>> 1);
			}
			for(int i = 0; i<4;i++) // Decode value for R
			{		
				if((deBin & 1) == 1)
				{
					R += Math.pow(2, i);
				}
				deBin = (deBin >>> 1);
			}
			for(int i = 0; i<8;i++) // Decode value for the assembly instruction, compare to decimal value.
			{		
				if((deBin & 1) == 1)
				{
					assemblyInt += Math.pow(2, i);
				}
				deBin = (deBin >>> 1);
			}
			switch (assemblyInt) // Take the decoded decimal and find its corresponding assembler instruction. 
			{
			case 88: assemblyChar = "L";
				break;
			case 90: assemblyChar = "A";
				break;
			case 80: assemblyChar = "ST";
				break;
			case 26: assemblyChar = "AR";
				break;
			}
			
			if (assemblyChar == "")	// Now apply all exception handling here. 
			{
				decodedInst = "ERROR";
			}
			else if (booleanErr)
			{
				decodedInst = "ERROR";
			}
			else
			{
				decodedInst = assemblyChar + " " + R + "," + D + "(" + X + "," + B + ")";
			}
		}
		else if (binNum.length() == 16)
		{
			for(int i = 0; i<16; i++) // Convert the binary string into an integer
			{
				if (binNum.charAt(15 - i) == '1')
				{
					deBin += Math.pow(2,  i);
				}
				else if (binNum.charAt(15 - i) == '0')
				{
					deBin += 0;
				}
				else // If any of the binary string digits are something other than a 1 or a 0, display an ERROR message.
				{
					booleanErr = true;
				}
			}
			for(int i = 0; i<4;i++) // Decode value for S
			{		
				if((deBin & 1) == 1)
				{
					S += Math.pow(2, i);
				}
				deBin = (deBin >>> 1);
			}
			for(int i = 0; i<4;i++) // Decode value for R
			{		
				if((deBin & 1) == 1)
				{
					R += Math.pow(2, i);
				}
				deBin = (deBin >>> 1);
			}
			for(int i = 0; i<8;i++) // Decode value for the assembly instruction, compare to decimal value.
			{		
				if((deBin & 1) == 1)
				{
					assemblyInt += Math.pow(2, i);
				}
				deBin = (deBin >>> 1);
			}
			switch (assemblyInt) // Take the decoded decimal and find its corresponding assembler instruction. 
			{
			case 88: assemblyChar = "L";
				break;
			case 90: assemblyChar = "A";
				break;
			case 80: assemblyChar = "ST";
				break;
			case 26: assemblyChar = "AR";
				break;
			}
			
			if (assemblyChar == "") // Apply all exception handling here. 
			{
				decodedInst = "ERROR";
			}
			else if (booleanErr)
			{
				decodedInst = "ERROR";
			}
			else
			{			
				decodedInst = assemblyChar + " " + R + "," + S;
			}
		}
		else
		{
			decodedInst = "ERROR: Must be 16 or 32 bit";
		}
		assemblerInstruction.setText(decodedInst);
	}

	void decodeHex() 
	{
		String hexNum = hexInstruction.getText();
		String hexDigits = "0123456789ABCDEF";
		String assemblyChar = "";
		String decodedInst = "";
		boolean booleanErr = false;
		int hexPow = 0;
		int deHex = 0, assemblyInt = 0;
		int D = 0, B = 0, X = 0, S = 0, R = 0;
		
		if (hexNum.length() == 8)
		{
			for(int i = 0; i<8; i++) // Convert the hexadecimal string into an integer
			{
				hexPow = hexDigits.indexOf(hexNum.charAt(7 - i));	
				if (hexPow == -1) // If the string character is not found in the set of hex digits, set the output to an ERROR message.
				{
					booleanErr = true;
				}
				deHex += hexPow * (Math.pow(16,  i));
			}
			for(int i = 0; i<12;i++) // Decode value for D
			{		
				if((deHex & 1) == 1)
				{
					D += Math.pow(2, i);
				}
				deHex = (deHex >>> 1);
			}
			for(int i = 0; i<4;i++) // Decode value for B
			{		
				if((deHex & 1) == 1)
				{
					B += Math.pow(2, i);
				}
				deHex = (deHex >>> 1);
			}
			for(int i = 0; i<4;i++) // Decode value for X
			{		
				if((deHex & 1) == 1)
				{
					X += Math.pow(2, i);
				}
				deHex = (deHex >>> 1);
			}
			for(int i = 0; i<4;i++) // Decode value for R
			{		
				if((deHex & 1) == 1)
				{
					R += Math.pow(2, i);
				}
				deHex = (deHex >>> 1);
			}
			for(int i = 0; i<8;i++) // Decode value for the assembly instruction, compare to decimal value.
			{		
				if((deHex & 1) == 1)
				{
					assemblyInt += Math.pow(2, i);
				}
				deHex = (deHex >>> 1);
			}
			switch (assemblyInt) // Take the decoded decimal and find its corresponding assembler instruction. 
			{
			case 88: assemblyChar = "L";
				break;
			case 90: assemblyChar = "A";
				break;
			case 80: assemblyChar = "ST";
				break;
			case 26: assemblyChar = "AR";
				break;
			}
			
			if (assemblyChar == "") // Apply all exception handling here. 
			{
				decodedInst = "ERROR";
			}
			else if (booleanErr)
			{
				decodedInst = "ERROR";
			}
			else
			{	
				decodedInst = assemblyChar + " " + R + "," + D + "(" + X + "," + B + ")";
			}
		}
		else if (hexNum.length() == 4)
		{
			for(int i = 0; i<4; i++) // Convert the hexadecimal string into an integer
			{
				hexPow = hexDigits.indexOf(hexNum.charAt(3 - i));
				if (hexPow == -1) // If the string character is not found in the set of hex digits, set the output to an ERROR message.
				{
					booleanErr = true;
				}
				deHex += hexPow * (Math.pow(16,  i));
			}
			for(int i = 0; i<4;i++) // Decode value for S
			{		
				if((deHex & 1) == 1)
				{
					S += Math.pow(2, i);
				}
				deHex = (deHex >>> 1);
			}
			for(int i = 0; i<4;i++) // Decode value for R
			{		
				if((deHex & 1) == 1)
				{
					R += Math.pow(2, i);
				}
				deHex = (deHex >>> 1);
			}
			for(int i = 0; i<8;i++) // Decode value for the assembly instruction, compare to decimal value.
			{		
				if((deHex & 1) == 1)
				{
					assemblyInt += Math.pow(2, i);
				}
				deHex = (deHex >>> 1);
			}
			switch (assemblyInt) // Take the decoded decimal and find its corresponding assembler instruction. 
			{
			case 88: assemblyChar = "L";
				break;
			case 90: assemblyChar = "A";
				break;
			case 80: assemblyChar = "ST";
				break;
			case 26: assemblyChar = "AR";
				break;
			}
				
			if (assemblyChar == "") // Apply all exception handling here.
			{
				decodedInst = "ERROR";
			}
			else if (booleanErr)
			{
				decodedInst = "ERROR";
			}
			else
			{
				decodedInst = assemblyChar + " " + R + "," + S;
			}
		}
		else
		{
			decodedInst = "ERROR: Must be 4 or 8 hex digits";
		}
		assemblerInstruction.setText(decodedInst);
	}
}
