import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fibonacci extends JApplet implements ActionListener {

	static final long serialVersionUID = 1;
	JButton findFibonacci = new JButton("Locate Fibonacci Number");
	JTextField fiboIndex = new JTextField(10);
	JTextField result = new JTextField(10);
	JLabel input = new JLabel("Index:");
	JLabel output = new JLabel("Fibonacci Number:");
	int indexIn;
	int fiboOut;
	
	public void init()
	{
		super.init();
		setSize(600, 75);
		setLayout(new FlowLayout());
		findFibonacci.addActionListener(this);
		add(input);
		add(fiboIndex);
		add(output);
		add(result);
		add(findFibonacci);
	}
	public void actionPerformed(ActionEvent e)
	{
		try 
		{
			indexIn = Integer.parseInt(fiboIndex.getText().trim());
		}
		catch(NumberFormatException nfe)
		{
			result.setText("Invalid Input");
		}
		if(e.getSource() == findFibonacci)
		{
			fiboOut = fibonacci(indexIn);	
			result.setText(fiboOut + "");
		}
	}
	public int fibonacci(int i)
	{
		if(i == 0) return 1;
		if(i == 1) return 1;
		return fibonacci(i-1) + fibonacci(i - 2);
	}

}


