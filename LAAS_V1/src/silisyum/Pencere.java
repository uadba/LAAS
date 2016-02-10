package silisyum;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.JTextField;

public class Pencere extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1870953358359256864L;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pencere frame = new Pencere();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Pencere() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 502);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		getContentPane().setLayout(new SpringLayout());
		
		JButton btnNewButton = new JButton("New button");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 137, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 137, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, 768, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_1, 23, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_1, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton_1, 441, SpringLayout.NORTH, getContentPane());
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_2, 23, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_2, 542, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton_2, 441, SpringLayout.NORTH, getContentPane());
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_3, 441, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_3, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton_3, 631, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_4, 23, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_4, 89, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton_4, 441, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton_4, 542, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("New button");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_5, 211, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_5, 242, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnNewButton_5);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, -464, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 77, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -444, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, -545, SpringLayout.EAST, getContentPane());
		getContentPane().add(textField);
		textField.setColumns(10);
	}
}
