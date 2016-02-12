package silisyum;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JTextField;
import java.awt.FlowLayout;

public class Multi_01 extends JFrame {
	private static final long serialVersionUID = -395867493898287111L;
	private JPanel contentPane;
	private JTextField yazi;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multi_01 frame = new Multi_01();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Multi_01() {
		SecondThread secondT = new SecondThread();
		secondT.execute();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
							
				if (secondT.calis == true) 
				{
					secondT.calis = false; 
				}
				else
				{
					secondT.calis = true;					
				}				
				
			}
		});
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.add(btnNewButton);	
		
		yazi = new JTextField();
		contentPane.add(yazi);
		yazi.setColumns(10);
		
	}
	
	class SecondThread extends SwingWorker<Void, Integer>
	{
		Random r = new Random();
		boolean calis = false;
		
		@Override
		protected Void doInBackground() throws Exception {
			
			while(!isCancelled())
			{					
				if (calis)
				{
					publish(r.nextInt());					
				}
				//System.out.println(Boolean.toString(calis));
			}
						
			return null;
		}
		
		@Override
		protected void process(List<Integer> toplu_birlik) {
			Integer son_gelen = toplu_birlik.get(toplu_birlik.size()-1);
			String yayinla = Integer.toString(son_gelen);
			yazi.setText(yayinla);			
		}
		
	}

}
