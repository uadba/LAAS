package silisyum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class Dosyadan_NEC_Oku extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8269370798852929547L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea kutu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Dosyadan_NEC_Oku dialog = new Dosyadan_NEC_Oku();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Dosyadan_NEC_Oku() {
		setBounds(100, 100, 1000, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("Yukle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String butunDosya = "";
				
				File file = new File("Moxon.out");
								
				try {
					
					Scanner sc = new Scanner(file);
					
					butunDosya = sc.useDelimiter("\\Z").next();
					
					/*
					
			        while (sc.hasNextLine()) {
			            String satir = sc.nextLine();
			            butunDosya += satir;
			            butunDosya += "\n";
			        }
			        */
					
			        sc.close();
					
				} catch (FileNotFoundException e) {					
					e.printStackTrace();
				}
				
				
				kutu.setText(butunDosya);
				
			}
		});
		btnNewButton.setBounds(433, 494, 89, 23);
		contentPanel.add(btnNewButton);
		{
			kutu = new JTextArea();
			kutu.setBorder(new LineBorder(new Color(0, 0, 0)));
			kutu.setBounds(10, 11, 964, 431);
			contentPanel.add(kutu);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
}
