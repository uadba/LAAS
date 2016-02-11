package silisyum;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Kendin_yap {
	
	private JFrame frame;
	
	public Kendin_yap ()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Kendin_yap window = new Kendin_yap();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
