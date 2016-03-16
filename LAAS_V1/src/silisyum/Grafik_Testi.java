package silisyum;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;


import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.awt.event.ActionEvent;

public class Grafik_Testi extends JFrame implements ChartMouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 208535889565395799L;
	private JPanel contentPane;
	private JTextField yazi;
	
	private XYSeries seriler;	
	private boolean add_or_update = false; //false:add and true:update
	
	private ChartPanel chartPanel;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;    
    
    private int initialNumberofElements = 20;
    private int problemDimension = 20;
    private AntennaArray aA = new AntennaArray(initialNumberofElements, 181);
    private AntennaArray aAforPresentation = new AntennaArray(initialNumberofElements, 181);    
    DifferentialEvolution mA;
    private JButton btnDoIt;
    private BestValues bV;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Grafik_Testi frame = new Grafik_Testi();
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
	public Grafik_Testi() {		
		
		mA = new DifferentialEvolution(aA.numberofElements, 70, 1000, 0.7, 0.95, 0, 1, aA);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 734, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		seriler = new XYSeries("Burası neresi1");
		XYSeriesCollection veri_seti = new XYSeriesCollection(seriler);
		JFreeChart grafik = ChartFactory.createXYLineChart("Başlık", "Açı", "Patter (dB)", veri_seti);
		contentPane.setLayout(new BorderLayout(0, 0));
		
        this.chartPanel = new ChartPanel(grafik);
        this.chartPanel.addChartMouseListener(this);
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        chartPanel.addOverlay(crosshairOverlay);
		
		grafik.getXYPlot().getDomainAxis().setRange(0, 180); // x axis
		grafik.getXYPlot().getRangeAxis().setRange(-100, 0); // y axis
				
		contentPane.add(chartPanel);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		yazi = new JTextField();
		panel.add(yazi);
		yazi.setColumns(10);
		yazi.setText(Integer.toString(initialNumberofElements));

				
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int numberofElements=initialNumberofElements ;
				
			    try{
			    	numberofElements = Integer.parseInt(yazi.getText());
			    }catch(NumberFormatException e){
			        numberofElements = 2;
			        yazi.setText("2");
			    }

				if (numberofElements<2) { numberofElements = 2; yazi.setText("2"); } 
				aA.numberofElements = numberofElements;
				aA.createArrays();
				aA.initializeArrays();
				
				aAforPresentation.numberofElements = numberofElements;
				aAforPresentation.createArrays();
				aAforPresentation.initializeArrays();

			}
		});
		panel.add(btnNewButton);
		
		btnDoIt = new JButton("Do it");
		btnDoIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		panel.add(btnDoIt);

		AlgorithmExecuter ae = new AlgorithmExecuter();
		ae.execute();
	}

	protected void drawPlot() {

		// it should not be a new implementation of AntennaArray class
		// We don't want to use the same instance which the other thread uses
		// This new instance can be another member function of this class
		// Its name may be aAforPresentation;
		// CONSIDER THIS!
		
				
		for (int d = 0; d < problemDimension; d++) {
			aAforPresentation.a[d] = bV.bestAmplitudes[d];
		}
		
		aAforPresentation.createPattern();
		
		for(int x=0; x<aAforPresentation.numberofSamplePoints; x++)
		{				
			if(add_or_update) //false:add and true:update
	
				seriler.update(aAforPresentation.angle[x], aAforPresentation.pattern_dB[x]);
			else
				seriler.add(aAforPresentation.angle[x], aAforPresentation.pattern_dB[x]);			
		}

		add_or_update = true;

	}

	@Override
	public void chartMouseClicked(ChartMouseEvent arg0) {
		
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent event) {
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, 
                RectangleEdge.BOTTOM);
        double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
        this.xCrosshair.setValue(x);
        this.yCrosshair.setValue(y);
		
	}
	
	class AlgorithmExecuter extends SwingWorker<Void, BestValues>
	{
		
		@Override
		protected Void doInBackground() throws Exception {
			while(!isCancelled())
			{
				while (mA.iterate()) {
					System.out.println(mA.fitnessOfBestMember);
					// You can create a new BestValue class object
					// with the best values of mA
					// and then it can be published
					double[] bestAmplitudes = new double[problemDimension];
					for (int d = 0; d < problemDimension; d++) {
						bestAmplitudes[d] = mA.members[d][mA.bestMember];
					}
					publish(new BestValues(mA.bestMember, mA.fitnessOfBestMember, bestAmplitudes));
				}					
			}			
			return null;
		}
		
		@Override
		protected void process(List<BestValues> chunks) {
			bV = chunks.get(chunks.size()-1);

			drawPlot();
			
		}
	}
}
