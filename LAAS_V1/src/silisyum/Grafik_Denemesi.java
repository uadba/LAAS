package silisyum;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Grafik_Denemesi extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 208535889565395799L;
	private JPanel contentPane;
	private JTextField yazi;	
	private boolean add_or_update = false; //false:add and true:update

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Grafik_Denemesi frame = new Grafik_Denemesi();
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
	public Grafik_Denemesi() {
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 734, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		XYSeries seriler = new XYSeries("Burası neresi1");
		XYSeriesCollection veri_seti = new XYSeriesCollection(seriler);
		JFreeChart grafik = ChartFactory.createXYLineChart("Başlık", "Açı", "Patter (dB)", veri_seti);
		contentPane.setLayout(new BorderLayout(0, 0));
		ChartPanel grafikPaneli = new ChartPanel(grafik);
		
		grafik.getXYPlot().getDomainAxis().setRange(0, 180);
		grafik.getXYPlot().getRangeAxis().setRange(-100, 0);
				
		contentPane.add(grafikPaneli);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		yazi = new JTextField();
		panel.add(yazi);
		yazi.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayFactor.createPattern();
				
				for(int x=0; x<ArrayFactor.numberofSamplePoints; x += 1)
				{				
					if(add_or_update) //false:add and true:update
			
						seriler.update((double)x, ArrayFactor.pattern_dB[x]);
					else
						seriler.add(x, ArrayFactor.pattern_dB[x]);			
				}
				
				add_or_update = true;
			}
		});
		panel.add(btnNewButton);		
	}

}
