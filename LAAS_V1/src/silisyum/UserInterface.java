package silisyum;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;


import javax.swing.SwingWorker;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class UserInterface extends JFrame implements ChartMouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 208535889565395799L;
	private JPanel contentPane;
	
	private XYSeries seriler;
	private XYSeries maskOuter;
	private XYSeries maskInner;
	private XYSeries convergenceSeries;
	private boolean add_or_update = false; //false:add and true:update
	
	private ChartPanel chartPanelPattern;
	private ChartPanel chartPanelConvergence;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;    
    
    private int numberofElements = 20;
    private int problemDimension = 0;
    private double[] L = {0, 0, -0.05}; // initial values of amplitude, phase, and position minimum limits
    private double[] H = {1, 10, 0.05}; // initial values of amplitude, phase, and position maximum limits
    private boolean amplitudeIsUsed = true;
    private boolean phaseIsUsed = true;
    private boolean positionIsUsed = true;
    private Mask mask = new Mask();
    private int patterGraphResolution = 721; //721;
    private AntennaArray aA = new AntennaArray(numberofElements, patterGraphResolution, mask);
    private AntennaArray aAforPresentation = new AntennaArray(numberofElements, patterGraphResolution, mask);
    private DifferentialEvolution mA = new DifferentialEvolution(numberofElements, 70, 5000, 0.7, 0.95, L, H, aA, mask, amplitudeIsUsed, phaseIsUsed, positionIsUsed);
    private BestValues bV;
    private JTextArea bigBoxForAmplitude;
    private JTextArea bigBoxForPhase;
    private JTextArea bigBoxForPostion;
    private JTabbedPane tabbedPaneForSettings;
    private JPanel arrayParameters;
    private JPanel differentialEvolution;
    private JPanel panelConvergence;
    private JPanel panelPattern;
    private JTextField costText;
    private JTextField iterationText;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JPanel mainPanel;
    private JButton startStopButton;
    private AlgorithmExecuter ae;
    private JTabbedPane tabbedPaneForPlots;
    private JPanel panelPatternGraphProperties;
    private JLabel lblNewLabel_2;
    private JButton btnNewButton;
    private JTextField textField;
    private JPanel panelPatternGraph;
    private JPanel panelConvergenceGraph;
    private JPanel panelConvergenceGraphProperties;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface frame = new UserInterface();
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
	public UserInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1429, 991);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		seriler = new XYSeries("Pattern");
		maskOuter = new XYSeries("Outer Mask");
		maskInner = new XYSeries("Inner Mask");
		convergenceSeries = new XYSeries("Convergence Curve");
		XYSeriesCollection veri_seti = new XYSeriesCollection(seriler);
		XYSeriesCollection veri_seti2 = new XYSeriesCollection(convergenceSeries);
		veri_seti.addSeries(maskOuter);
		veri_seti.addSeries(maskInner);
		JFreeChart grafik = ChartFactory.createXYLineChart("Array Pattern", "Angle", "Array Factor (in dB)", veri_seti);
		JFreeChart grafik2 = ChartFactory.createXYLineChart("Convergence", "Iteration", "Cost", veri_seti2);
				
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) grafik.getXYPlot().getRenderer();
		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesStroke(0, new BasicStroke(0.7f));
		renderer.setSeriesPaint(1, Color.blue);
		renderer.setSeriesStroke(1, new BasicStroke(0.5f));
		renderer.setSeriesPaint(2, new Color(0, 100, 0));
		renderer.setSeriesStroke(2, new BasicStroke(0.5f));
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
		
		grafik.getXYPlot().getDomainAxis().setRange(0, 180); // x axis
		grafik.getXYPlot().getRangeAxis().setRange(-100, 0);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		tabbedPaneForSettings = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPaneForSettings, BorderLayout.EAST);
		
		mainPanel = new JPanel();
		tabbedPaneForSettings.addTab("Main Controls", null, mainPanel, null);
		
		startStopButton = new JButton("Start Optimization");
		startStopButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(ae.keepIterating == false) {
					ae.keepIterating = true;
					startStopButton.setText("Stop Optimization");
				} else {
					ae.keepIterating = false;
					startStopButton.setText("Start Optimization");
				}
			}
		});
		mainPanel.add(startStopButton);
		
		arrayParameters = new JPanel();
		tabbedPaneForSettings.addTab("Array Parameters", null, arrayParameters, null);
		
		bigBoxForAmplitude = new JTextArea();
		arrayParameters.add(bigBoxForAmplitude);
		bigBoxForAmplitude.setLineWrap(true);
		bigBoxForAmplitude.setRows(30);
		bigBoxForAmplitude.setColumns(17);
		
		bigBoxForPhase = new JTextArea();
		arrayParameters.add(bigBoxForPhase);
		bigBoxForPhase.setRows(30);
		bigBoxForPhase.setLineWrap(true);
		bigBoxForPhase.setColumns(17);
		
		bigBoxForPostion = new JTextArea();
		arrayParameters.add(bigBoxForPostion);
		bigBoxForPostion.setRows(30);
		bigBoxForPostion.setLineWrap(true);
		bigBoxForPostion.setColumns(17);
		
		differentialEvolution = new JPanel();
		tabbedPaneForSettings.addTab("Differential Evolution", null, differentialEvolution, null);
		
		tabbedPaneForPlots = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPaneForPlots, BorderLayout.CENTER);
		
		panelPattern = new JPanel();
		tabbedPaneForPlots.addTab("Antenna Array Pattern", null, panelPattern, null);
		panelPattern.setLayout(new BorderLayout(0, 0));
		
		panelPatternGraph = new JPanel(new GridBagLayout());
		panelPattern.add(panelPatternGraph, BorderLayout.NORTH);
		
		this.chartPanelPattern = new ChartPanel(grafik);
		GridBagConstraints gbc_chartPanelPattern = new GridBagConstraints();
		gbc_chartPanelPattern.anchor = GridBagConstraints.NORTHWEST;
		gbc_chartPanelPattern.gridx = 1;
		gbc_chartPanelPattern.gridy = 0;
		panelPatternGraph.add(chartPanelPattern, gbc_chartPanelPattern);
		panelPatternGraph.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                preserveAspectRatio(chartPanelPattern, panelPatternGraph);
            }
		});
		
		
		this.chartPanelPattern.addChartMouseListener(this);
		chartPanelPattern.addOverlay(crosshairOverlay);
		
		panelPatternGraphProperties = new JPanel();
		panelPattern.add(panelPatternGraphProperties, BorderLayout.CENTER);
		
		lblNewLabel_2 = new JLabel("New label");
		panelPatternGraphProperties.add(lblNewLabel_2);
		
		textField = new JTextField();
		panelPatternGraphProperties.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("New button");
		panelPatternGraphProperties.add(btnNewButton);
		
		panelConvergence = new JPanel();
		tabbedPaneForPlots.addTab("Convergence Curve of Optimization Process", null, panelConvergence, null);
		panelConvergence.setLayout(new BorderLayout(0, 0));
		
		panelConvergenceGraph = new JPanel(new GridBagLayout());
		panelConvergence.add(panelConvergenceGraph, BorderLayout.NORTH);
		
		chartPanelConvergence = new ChartPanel(grafik2);
		GridBagConstraints gbc_chartPanelConvergence = new GridBagConstraints();
		gbc_chartPanelConvergence.anchor = GridBagConstraints.NORTHWEST;
		gbc_chartPanelConvergence.gridx = 1;
		gbc_chartPanelConvergence.gridy = 0;
		panelConvergenceGraph.add(chartPanelConvergence, gbc_chartPanelConvergence);
		panelConvergenceGraph.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                preserveAspectRatio(chartPanelConvergence, panelConvergenceGraph);
            }
		});
		
		panelConvergenceGraphProperties = new JPanel();
		panelConvergence.add(panelConvergenceGraphProperties);
		
		iterationText = new JTextField();
		panelConvergenceGraphProperties.add(iterationText);
		iterationText.setEditable(false);
		iterationText.setColumns(20);
		
		lblNewLabel_1 = new JLabel("Cost Value:");
		panelConvergenceGraphProperties.add(lblNewLabel_1);
		
		costText = new JTextField();
		panelConvergenceGraphProperties.add(costText);
		costText.setEditable(false);
		costText.setColumns(20);
		
		lblNewLabel = new JLabel("Iteration Number:");
		panelConvergenceGraphProperties.add(lblNewLabel);
		
		if (amplitudeIsUsed) problemDimension = numberofElements;		
		if (phaseIsUsed) problemDimension += numberofElements;		
		if (positionIsUsed) problemDimension += numberofElements;

		ae = new AlgorithmExecuter();
		ae.execute();
	}
	
	private void preserveAspectRatio(JPanel innerPanel, JPanel container) {
        int w = container.getWidth();
        //int h = container.getHeight();
        //int size =  Math.min(w, h);
        //innerPanel.setPreferredSize(new Dimension(size, size));
        innerPanel.setPreferredSize(new Dimension(w, w*440/680));
        container.revalidate();
    }

	protected void drawPlot() {

		// it should not be a new implementation of AntennaArray class
		// We don't want to use the same instance which the other thread uses
		// This new instance can be another member function of this class
		// Its name may be aAforPresentation;
		// CONSIDER THIS!
		
		bigBoxForAmplitude.setText("");
		
		String s = "";
		int delta = 0;
		if (amplitudeIsUsed) {
			// this is for amplitudes	
			for (int index = 0; index < numberofElements; index++) {
				aAforPresentation.a[index] = bV.valuesOfBestMember[index];
				s += "" + aAforPresentation.a[index] + "\n";
			}
			delta = numberofElements;
			bigBoxForAmplitude.setText(s);
		}
		
		s = "";
		if (phaseIsUsed) {
			// this is for phases
			for (int index = 0; index < numberofElements; index++) {
				aAforPresentation.alpha[index] = bV.valuesOfBestMember[index + delta];
				s += "" + aAforPresentation.alpha[index] + "\n";
			}
			delta += numberofElements;
			bigBoxForPhase.setText(s);
		}
		
		s = "";
		if (positionIsUsed) {
			// this is for positions. It starts with 1 instead of 0
			aAforPresentation.d[0] = 0;
			s += "" + aAforPresentation.d[0] + "\n";
			for (int index = 1; index < numberofElements; index++) {
				aAforPresentation.d[index] = aAforPresentation.d[index - 1] + 0.5 + bV.valuesOfBestMember[index + delta];
				s += "" + aAforPresentation.d[index] + "\n";
			}
			bigBoxForPostion.setText(s);
		}
		
		aAforPresentation.createPattern();
		
		for(int x=0; x<aAforPresentation.numberofSamplePoints; x++)
		{				
			if(add_or_update) //false:add and true:update
			{
				seriler.update(aAforPresentation.angle[x], aAforPresentation.pattern_dB[x]);
			}
			else
			{
				seriler.add(aAforPresentation.angle[x], aAforPresentation.pattern_dB[x]);
			}
		}
		
		convergenceSeries.add(mA.iterationIndex, mA.fitnessOfBestMember);
		iterationText.setText(Integer.toString(mA.iterationIndex));
		costText.setText(Double.toString(mA.fitnessOfBestMember));		

		// ------------- Outer Mask --------------------
		int numberOfSLLOuters = mask.SLL_outers.size(); 
		Mask.SidelobeLevel SLL_outer;
		
		for (int n = 0; n < numberOfSLLOuters; n++) {
			SLL_outer = mask.SLL_outers.get(n);
			if(add_or_update) //false:add and true:update
			{
				for (int i = 0; i < SLL_outer.angles.length; i++) {
					if(i==0)
						maskOuter.update(SLL_outer.angles[i]+0.0000000001, SLL_outer.levels[i]);
					else
						maskOuter.update(SLL_outer.angles[i], SLL_outer.levels[i]);	
				}
			}
			else
			{
				for (int i = 0; i < SLL_outer.angles.length; i++) {
					if(i==0)
						maskOuter.add(SLL_outer.angles[i]+0.0000000001, SLL_outer.levels[i]);
					else
						maskOuter.add(SLL_outer.angles[i], SLL_outer.levels[i]);		
				}			
			}		
		}
		
		// ------------- Inner Mask --------------------
		int numberOfSLLInners = mask.SLL_inners.size(); 
		Mask.SidelobeLevel SLL_inner;
		
		for (int n = 0; n < numberOfSLLInners; n++) {
			SLL_inner = mask.SLL_inners.get(n);
			if(add_or_update) //false:add and true:update
			{
				for (int i = 0; i < SLL_inner.angles.length; i++) {
					if(i==0)
						maskInner.update(SLL_inner.angles[i]+0.0000000001, SLL_inner.levels[i]);
					else
						maskInner.update(SLL_inner.angles[i], SLL_inner.levels[i]);	
				}
			}
			else
			{
				for (int i = 0; i < SLL_inner.angles.length; i++) {
					if(i==0)
						maskInner.add(SLL_inner.angles[i]+0.0000000001, SLL_inner.levels[i]);
					else
						maskInner.add(SLL_inner.angles[i], SLL_inner.levels[i]);		
				}			
			}		
		}
		
		add_or_update = true;	

	}

	@Override
	public void chartMouseClicked(ChartMouseEvent arg0) {
		
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent event) {
        Rectangle2D dataArea = this.chartPanelPattern.getScreenDataArea();
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
		private boolean keepIterating;
		
		public AlgorithmExecuter() {
			keepIterating = false;
		}

		@Override
		protected Void doInBackground() throws Exception {
			while(!isCancelled())
			{
				boolean iterationState = true;
				while (keepIterating && iterationState) {
					iterationState = mA.iterate();
					//System.out.println("it:" + mA.iterationIndex + "\t best:" + mA.fitnessOfBestMember);					
					// You can create a new BestValue class object
					// with the best values of mA
					// and then it can be published
					double[] valuesOfBestMember = new double[problemDimension];
					for (int d = 0; d < problemDimension; d++) {
						valuesOfBestMember[d] = mA.members[d][mA.bestMember];
					}
					publish(new BestValues(mA.bestMember, mA.fitnessOfBestMember, valuesOfBestMember));
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