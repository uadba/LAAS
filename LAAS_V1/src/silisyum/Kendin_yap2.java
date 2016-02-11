package silisyum;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Kendin_yap2 extends JFrame{

	public Kendin_yap2 ()
	{		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Bu mu?");		
	}

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Kendin_yap2 window = new Kendin_yap2();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
