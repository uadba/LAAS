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
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class Multi_05 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8379382489549610146L;
	private JPanel contentPane;
	private JTextField random_displayer;
	private JTextField min;
	private JTextField max;
	private Panel_05 panel;
	private JPanel panel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multi_05 frame = new Multi_05();
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
	public Multi_05() {
		setTitle("D\u00F6rt");
		AlgorithmExecuter ae = new AlgorithmExecuter();
		ae.execute();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 785, 627);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		random_displayer = new JTextField();
		panel_2.add(random_displayer);
		random_displayer.setColumns(10);
		
		JToggleButton start_stop = new JToggleButton("Start");
		panel_2.add(start_stop);
		
		min = new JTextField();
		panel_2.add(min);
		min.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				ae.st = Integer.parseInt(min.getText());
			}
		});
		min.setColumns(10);
		
		max = new JTextField();
		panel_2.add(max);
		max.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ae.sp = Integer.parseInt(max.getText());
			}
		});
		max.setColumns(10);
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
		
		panel = new Panel_05();
		panel_1.add(panel);
		panel.setBackground(Color.LIGHT_GRAY);
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
			panel.drawPlot((double) number);
		}
	}
}
