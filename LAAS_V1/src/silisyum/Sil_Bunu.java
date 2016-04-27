package silisyum;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Sil_Bunu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7566353305243379014L;
	private JPanel contentPane;
	private JTextField textField;
	private JPanel panel;
	private JPanel panel_up;
	private JButton btnNewButton;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;
	private JButton button_6;
	private JButton button_7;
	private JLabel lblNewLabel_1;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sil_Bunu frame = new Sil_Bunu();
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
	public Sil_Bunu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel_up = new JPanel();
		contentPane.add(panel_up, BorderLayout.NORTH);
		
		btnNewButton = new JButton("New button");
		panel_up.add(btnNewButton);
		
		button = new JButton("New button");
		panel_up.add(button);
		
		button_1 = new JButton("New button");
		panel_up.add(button_1);
		
		button_2 = new JButton("New button");
		panel_up.add(button_2);
		
		button_5 = new JButton("New button");
		panel_up.add(button_5);
		
		button_6 = new JButton("New button");
		panel_up.add(button_6);
		
		button_7 = new JButton("New button");
		panel_up.add(button_7);
		
		button_3 = new JButton("New button");
		panel_up.add(button_3);
		
		button_4 = new JButton("New button");
		panel_up.add(button_4);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel lblNewLabel = new JLabel("New label");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel, 8, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel, 23, SpringLayout.WEST, panel);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.EAST, textField, 120, SpringLayout.EAST, lblNewLabel);
		panel.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("New label");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 18, SpringLayout.SOUTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel_1, 0, SpringLayout.WEST, lblNewLabel);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, textField_1, 9, SpringLayout.SOUTH, textField);
		sl_panel.putConstraint(SpringLayout.WEST, textField_1, -114, SpringLayout.EAST, textField);
		sl_panel.putConstraint(SpringLayout.EAST, textField_1, 0, SpringLayout.EAST, textField);
		panel.add(textField_1);
		textField_1.setColumns(10);
	}
}
