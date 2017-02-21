import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Factorial extends JApplet implements ActionListener{

	static final long serialVersionUID = 1;
	JButton calculate = new JButton("Calculate Factorial");
	JTextField numberTF = new JTextField(10);
	JTextField factorialTF = new JTextField(10);
	JLabel numIn = new JLabel("Number Input:");
	JLabel numOut = new JLabel("Factorial:");
	int numberIn;
	int numberOut;
	
	public void init()
	{
		super.init();
		setSize(600, 75);
		setLayout(new FlowLayout());
		calculate.addActionListener(this);
		add(numIn);
		add(numberTF);
		add(numOut);
		add(factorialTF);
		add(calculate);
	}
	public void actionPerformed(ActionEvent e)
	{
		try 
		{
			numberIn = Integer.parseInt(numberTF.getText().trim());
		}
		catch(NumberFormatException nfe)
		{
			factorialTF.setText("Invalid Input");
		}
		if(e.getSource() == calculate)
		{
			numberOut = factorial(numberIn);	
			factorialTF.setText(numberOut + "");
		}
	}
	public int factorial(int i)
	{
		if(i == 0) return 1;
		if(i == 1) return 1;
		return (i * factorial(i - 1));		
	}

}
