package silisyum;

import java.awt.EventQueue;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class Multi_03 extends JFrame {

	AlgorithmRunner ar;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8379382489549610146L;
	private JPanel contentPane;
	private JTextField rastgele;
	private JTextField baslangic;
	private JTextField bitis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multi_03 frame = new Multi_03();
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
	public Multi_03() {
		ar = new AlgorithmRunner();
		ar.execute();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JToggleButton dugme = new JToggleButton("Baslat");
		dugme.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(ar.run == false)
				{
					ar.run = true;
					dugme.setText("Durdur");
				}
				else
				{
					ar.run = false;
					dugme.setText("Baslat");
				}
			}
		});
		dugme.setBounds(156, 128, 121, 23);
		contentPane.add(dugme);
		
		rastgele = new JTextField();
		rastgele.setHorizontalAlignment(SwingConstants.CENTER);
		rastgele.setBounds(174, 68, 86, 20);
		contentPane.add(rastgele);
		rastgele.setColumns(10);
		
		baslangic = new JTextField();
		baslangic.setBounds(108, 195, 86, 20);
		contentPane.add(baslangic);
		baslangic.setColumns(10);
		
		bitis = new JTextField();
		bitis.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
				//ar.bas = Integer.parseInt(bitis.getText());
				System.out.println("what?");
			}
			public void ancestorMoved(AncestorEvent arg0) {
				//ar.bas = Integer.parseInt(bitis.getText());
				System.out.println("what?");
			}
			public void ancestorRemoved(AncestorEvent arg0) {
				//ar.bas = Integer.parseInt(bitis.getText());
				System.out.println("what?");
			}
		});
		bitis.setBounds(237, 195, 86, 20);
		contentPane.add(bitis);
		bitis.setColumns(10);
	}
	
	class AlgorithmRunner extends SwingWorker<Void, Integer>
	{
		Random r = new Random();
		Boolean run = false;
		int bas = 0;
		int kic = 1000000;

		@Override
		protected Void doInBackground() throws Exception {

			while(!isCancelled())
			{
				if(run)
				{
					int rastgele_sayi = bas + r.nextInt(kic - bas);
					publish(rastgele_sayi);
				}
			}
			
			return null;
		}
		
		@Override
		protected void process(List<Integer> chunks) {
			
			int h = chunks.get(chunks.size()-1);
			String rastgele_yazi = Integer.toString(h);
			if(run == false) rastgele_yazi = "";
			rastgele.setText(rastgele_yazi);
			
		}	
	}
}
