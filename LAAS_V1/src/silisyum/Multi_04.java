package silisyum;

import java.awt.EventQueue;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Multi_04 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8379382489549610146L;
	private JPanel contentPane;
	private JTextField random_displayer;
	private JTextField min;
	private JTextField max;
	private Panel_04 panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multi_04 frame = new Multi_04();
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
	public Multi_04() {
		AlgorithmExecuter ae = new AlgorithmExecuter();
		ae.execute();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		random_displayer = new JTextField();
		random_displayer.setBounds(244, 346, 86, 20);
		contentPane.add(random_displayer);
		random_displayer.setColumns(10);
		
		min = new JTextField();
		min.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				ae.st = Integer.parseInt(min.getText());
			}
		});
		min.setBounds(146, 415, 86, 20);
		contentPane.add(min);
		min.setColumns(10);
		
		max = new JTextField();
		max.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ae.sp = Integer.parseInt(max.getText());
			}
		});
		max.setBounds(342, 415, 86, 20);
		contentPane.add(max);
		max.setColumns(10);
		
		JToggleButton start_stop = new JToggleButton("Start");
		start_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(start_stop.isSelected())
					{
						ae.generate = true;
						start_stop.setText("Stop");
					}
				else
					{
						ae.generate = false;
						start_stop.setText("Start");
					}
			}
		});
		start_stop.setBounds(229, 379, 121, 23);
		contentPane.add(start_stop);
		
		panel = new Panel_04();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 11, 554, 285);
		contentPane.add(panel);
	}
	
	class AlgorithmExecuter extends SwingWorker<Void, Integer>
	{
		Random r = new Random();
		int st=0, sp=1000;
		boolean generate=false;
		
		@Override
		protected Void doInBackground() throws Exception {
			while(!isCancelled())
			{
				if (generate) {
					int random = st + r.nextInt(sp - st);
					publish(random);
				}
			}			
			return null;
		}
		
		@Override
		protected void process(List<Integer> chunks) {
			int number = chunks.get(chunks.size()-1);
			random_displayer.setText(Integer.toString(number));
			panel.ciz();
		}
	}
}
