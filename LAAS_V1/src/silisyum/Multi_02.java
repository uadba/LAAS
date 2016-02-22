package silisyum;

import java.awt.EventQueue;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Multi_02 extends JFrame {

	caliskanCocuk thr = new caliskanCocuk();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7455298491038699242L;
	private JPanel contentPane;
	private JTextField kutu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multi_02 frame = new Multi_02();
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
	public Multi_02() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		kutu = new JTextField();
		kutu.setBounds(259, 118, 86, 20);
		contentPane.add(kutu);
		kutu.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(thr.calis == true)
					thr.calis = false;
				else
					thr.calis = true;
			}
		});
		btnNewButton.setBounds(90, 117, 89, 23);
		contentPane.add(btnNewButton);
		
		thr.execute();
	}
	
	class caliskanCocuk extends SwingWorker<Void, Integer>
	{
		Random r = new Random();
		boolean calis = true;
		
		@Override
		protected Void doInBackground() throws Exception {
			
			while(!isCancelled())
			{
				if(calis)
				{
					publish(r.nextInt(100));
				}
			}		
						
			return null;
		}

		@Override
		protected void process(List<Integer> hepberaber) {
			int sayi = hepberaber.get(hepberaber.size()-1);
			String yazi = Integer.toString(sayi);
			kutu.setText(yazi);
		}	
				
	}
}
