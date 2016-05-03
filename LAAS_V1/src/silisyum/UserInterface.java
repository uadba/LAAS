package silisyum;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

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
	private boolean updateOrAdd = false; //false:add and true:update
	
	private ChartPanel chartPanelPattern;
	private ChartPanel chartPanelConvergence;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;    
    
    private int numberofElements = 20;
    private int problemDimension;
    private double[] L = {0, 0, -0.05}; // initial values of amplitude, phase, and position minimum limits
    private double[] H = {1, 10, 0.05}; // initial values of amplitude, phase, and position maximum limits    
    private boolean amplitudeIsUsed = false;
    private boolean phaseIsUsed = false;
    private boolean positionIsUsed = false;
    private Mask mask;
    private int patterGraphResolution = 721; //721;
    private int populationNumber = 70;
    private int maximumIterationNumber = 2000;
    private double F = 0.7;
    private double Cr = 0.95;
    private AntennaArray antennaArray;
    private AntennaArray antennaArrayForPresentation;
    private DifferentialEvolution differentialEvolution;
    private BestValues bestValues;
    private JTabbedPane tabbedPaneForSettings;
    private JPanel arrayParametersPanel;
    private JPanel differentialEvolutionPanel;
    private JPanel panelConvergence;
    private JPanel panelPattern;
    private JTextField costText;
    private JTextField iterationText;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private AlgorithmExecuter ae;
    private JTabbedPane tabbedPaneForPlots;
    private JPanel panelPatternGraphProperties;
    private JLabel lblNewLabel_2;
    private JButton btnNewButton;
    private JTextField textField;
    private JPanel panelPatternGraph;
    private JPanel panelConvergenceGraph;
    private JPanel panelConvergenceGraphProperties;
    private JPanel startStopPanel;
    private JButton startStopButton;
    private JLabel numberOfElements_Label;
    private JTextField numberOfElements_Field;
    private JButton terminateOptimizationButton;
    private JLabel lblPopulationNumber;
    private JTextField populationNumber_textField;
    private JLabel lblMaximumIterationNumber;
    private JTextField maximumIterationNumber_textField;
    private JLabel lblF;
    private JLabel lblCr;
    private JTextField F_textField;
    private JTextField Cr_textField;
    private JPanel rightPannel;
    private JCheckBox chckbxAmplitude;
    private JLabel lblMaximumValueAmplitude;
    private JLabel lblMinimumValueAmplitude;
    private JTextField textField_maximumValueAmplitude;
    private JTextField textField_minimumValueAmplitude;
    private JCheckBox chckbxPhase;
    private JLabel lblMaximumValuePhase;
    private JLabel lblMinimumValuePhase;
    private JTextField textField_maximumValuePhase;
    private JTextField textField_minimumValuePhase;
    private JCheckBox chckbxPosition;
    private JLabel lblMaximumValuePosition;
    private JLabel lblMinimumValuePosition;
    private JTextField textField_maximumValuePosition;
    private JTextField textField_minimumValuePosition;
    private JPanel mainControlsPanel;
    private JLabel lblMessages;
    private JTextPane messages_txtpn;

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
		
		rightPannel = new JPanel();
		contentPane.add(rightPannel, BorderLayout.EAST);
		rightPannel.setLayout(new BorderLayout(0, 0));
		
		startStopPanel = new JPanel();
		rightPannel.add(startStopPanel, BorderLayout.NORTH);
		startStopPanel.setLayout(new MigLayout("", "[170px][170px][170px]", "[][23px]"));
		
		startStopButton = new JButton("Start Optimization");
		startStopButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(ae.keepIterating == false) {
					if(ae.newStart)
					{
						getParametersFromUserInterface();
						calculateProblemDimension();
						createMainObjects();
						seriler.clear();
						maskOuter.clear();
						maskInner.clear();
						convergenceSeries.clear();
						ae.newStart = false;
						updateOrAdd = false; //false:add and true:update
						ae.iterationState = true;
					}
					ae.keepIterating = true;
					startStopButton.setText("Stop Optimization");
					terminateOptimizationButton.setVisible(false);
				} else {
					ae.keepIterating = false;
					startStopButton.setText("Continue Optimization");
					terminateOptimizationButton.setVisible(true);
				}			
			}
		});
		startStopPanel.add(startStopButton, "cell 1 1,alignx center,aligny top");
		
		terminateOptimizationButton = new JButton("Terminate Optimization");
		terminateOptimizationButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ae.keepIterating = false;
				ae.newStart = true;
				terminateOptimizationButton.setVisible(false);
				startStopButton.setText("Start Optimization");
			}
		});
		terminateOptimizationButton.setVisible(false);
		terminateOptimizationButton.setForeground(new Color(255, 255, 255));
		terminateOptimizationButton.setBackground(new Color(255, 69, 0));
		startStopPanel.add(terminateOptimizationButton, "cell 2 1,alignx center");
		
		tabbedPaneForSettings = new JTabbedPane(JTabbedPane.TOP);
		rightPannel.add(tabbedPaneForSettings, BorderLayout.CENTER);
		
		mainControlsPanel = new JPanel();
		tabbedPaneForSettings.addTab("Main Controls", null, mainControlsPanel, null);
		mainControlsPanel.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		lblMessages = new JLabel("Messages");
		mainControlsPanel.add(lblMessages, "cell 0 0");
		
		messages_txtpn = new JTextPane();
		messages_txtpn.setContentType("text/html");
		JScrollPane jsp = new JScrollPane(messages_txtpn);
		mainControlsPanel.add(jsp, "cell 0 1,grow");		
		
		arrayParametersPanel = new JPanel();
		tabbedPaneForSettings.addTab("Array Parameters", null, arrayParametersPanel, null);
		arrayParametersPanel.setLayout(new MigLayout("", "[170px][86px,grow]", "[20px][][][][][][][][][]"));
		
		numberOfElements_Label = new JLabel("Number of Antenna Array Elements");
		arrayParametersPanel.add(numberOfElements_Label, "cell 0 0,alignx right,aligny center");
		
		numberOfElements_Field = new JTextField();
		numberOfElements_Field.setText(Integer.toString(numberofElements));
		arrayParametersPanel.add(numberOfElements_Field, "cell 1 0,growx,aligny center");
		numberOfElements_Field.setColumns(10);
		
		chckbxAmplitude = new JCheckBox("Amplitude");
		chckbxAmplitude.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				amplitudeIsUsed = chckbxAmplitude.isSelected();
			}
		});
		chckbxAmplitude.setSelected(amplitudeIsUsed);
		arrayParametersPanel.add(chckbxAmplitude, "cell 0 1");
		
		lblMaximumValueAmplitude = new JLabel("Maximum Value :");
		lblMaximumValueAmplitude.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMaximumValueAmplitude, "cell 0 2,alignx trailing");
		
		textField_maximumValueAmplitude = new JTextField();
		arrayParametersPanel.add(textField_maximumValueAmplitude, "cell 1 2,growx");
		textField_maximumValueAmplitude.setColumns(10);
		
		lblMinimumValueAmplitude = new JLabel("Minimum Value :");
		lblMinimumValueAmplitude.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMinimumValueAmplitude, "cell 0 3,alignx trailing");
		
		textField_minimumValueAmplitude = new JTextField();
		arrayParametersPanel.add(textField_minimumValueAmplitude, "cell 1 3,growx");
		textField_minimumValueAmplitude.setColumns(10);
		
		chckbxPhase = new JCheckBox("Phase");
		chckbxPhase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				phaseIsUsed = chckbxPhase.isSelected();
			}
		});
		chckbxPhase.setSelected(phaseIsUsed);
		arrayParametersPanel.add(chckbxPhase, "cell 0 4");
		
		lblMaximumValuePhase = new JLabel("Maximum Value :");
		lblMaximumValuePhase.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMaximumValuePhase, "cell 0 5,alignx trailing");
		
		textField_maximumValuePhase = new JTextField();
		arrayParametersPanel.add(textField_maximumValuePhase, "cell 1 5,growx");
		textField_maximumValuePhase.setColumns(10);
		
		lblMinimumValuePhase = new JLabel("Minimum Value :");
		lblMinimumValuePhase.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMinimumValuePhase, "cell 0 6,alignx trailing");
		
		textField_minimumValuePhase = new JTextField();
		arrayParametersPanel.add(textField_minimumValuePhase, "cell 1 6,growx");
		textField_minimumValuePhase.setColumns(10);
		
		chckbxPosition = new JCheckBox("Position");
		chckbxPosition.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				positionIsUsed = chckbxPosition.isSelected();
			}
		});
		chckbxPosition.setSelected(positionIsUsed);
		arrayParametersPanel.add(chckbxPosition, "cell 0 7");
		
		lblMaximumValuePosition = new JLabel("Maximum Value :");
		lblMaximumValuePosition.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMaximumValuePosition, "cell 0 8,alignx trailing");
		
		textField_maximumValuePosition = new JTextField();
		arrayParametersPanel.add(textField_maximumValuePosition, "cell 1 8,growx");
		textField_maximumValuePosition.setColumns(10);
		
		lblMinimumValuePosition = new JLabel("Minimum Value :");
		lblMinimumValuePosition.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMinimumValuePosition, "cell 0 9,alignx trailing");
		
		textField_minimumValuePosition = new JTextField();
		arrayParametersPanel.add(textField_minimumValuePosition, "cell 1 9,growx");
		textField_minimumValuePosition.setColumns(10);
		
		differentialEvolutionPanel = new JPanel();
		tabbedPaneForSettings.addTab("Differential Evolution", null, differentialEvolutionPanel, null);
		differentialEvolutionPanel.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		lblPopulationNumber = new JLabel("Population Number :");
		lblPopulationNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		differentialEvolutionPanel.add(lblPopulationNumber, "cell 0 0,alignx trailing");
		
		populationNumber_textField = new JTextField();
		populationNumber_textField.setText(Integer.toString(populationNumber));
		differentialEvolutionPanel.add(populationNumber_textField, "cell 1 0,growx");
		populationNumber_textField.setColumns(10);
		
		lblMaximumIterationNumber = new JLabel("Maximum Iteration Number :");
		lblMaximumIterationNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		differentialEvolutionPanel.add(lblMaximumIterationNumber, "cell 0 1,alignx trailing");
		
		maximumIterationNumber_textField = new JTextField();
		maximumIterationNumber_textField.setText(Integer.toString(maximumIterationNumber));
		differentialEvolutionPanel.add(maximumIterationNumber_textField, "cell 1 1,growx");
		maximumIterationNumber_textField.setColumns(10);
		
		lblF = new JLabel("Scaling Factor (F) :");
		lblF.setHorizontalAlignment(SwingConstants.RIGHT);
		differentialEvolutionPanel.add(lblF, "cell 0 2,alignx trailing");
		
		F_textField = new JTextField();
		F_textField.setText(Double.toString(F));
		F_textField.setColumns(10);
		differentialEvolutionPanel.add(F_textField, "cell 1 2,growx");
		
		lblCr = new JLabel("Crossover Rate (Cr) :");
		lblCr.setHorizontalAlignment(SwingConstants.RIGHT);
		differentialEvolutionPanel.add(lblCr, "cell 0 3,alignx trailing");
		
		Cr_textField = new JTextField();
		Cr_textField.setText(Double.toString(Cr));
		Cr_textField.setColumns(10);
		differentialEvolutionPanel.add(Cr_textField, "cell 1 3,growx");
		
		ae = new AlgorithmExecuter();
		ae.execute();
	}
	
	private void getParametersFromUserInterface() {
		numberofElements = Integer.parseInt(numberOfElements_Field.getText());
	    populationNumber = Integer.parseInt(populationNumber_textField.getText());
	    maximumIterationNumber = Integer.parseInt(maximumIterationNumber_textField.getText());
	    F = Double.parseDouble(F_textField.getText());
	    Cr = Double.parseDouble(Cr_textField.getText());
	}
	
	private void calculateProblemDimension() {
		problemDimension = 0;
		if (amplitudeIsUsed) problemDimension = numberofElements;		
		if (phaseIsUsed) problemDimension += numberofElements;		
		if (positionIsUsed) problemDimension += numberofElements;
		System.out.println(problemDimension);
	}
	
	private void createMainObjects() {
		mask = new Mask();
		antennaArray = new AntennaArray(numberofElements, patterGraphResolution, mask);
		antennaArrayForPresentation = new AntennaArray(numberofElements, patterGraphResolution, mask);
		differentialEvolution = new DifferentialEvolution(numberofElements, populationNumber, maximumIterationNumber, F, Cr, L, H, antennaArray, mask, amplitudeIsUsed, phaseIsUsed, positionIsUsed);
	}
	
	private void preserveAspectRatio(JPanel innerPanel, JPanel container) {
        int w = container.getWidth();
        innerPanel.setPreferredSize(new Dimension(w, w*440/680));
        container.revalidate();
    }

	protected void drawPlot() {

		// it should not be a new implementation of AntennaArray class
		// We don't want to use the same instance which the other thread uses
		// This new instance can be another member function of this class
		// Its name may be aAforPresentation;
		// CONSIDER THIS!
		
		int delta = 0;
		if (amplitudeIsUsed) {
			// this is for amplitudes	
			for (int index = 0; index < numberofElements; index++) {
				antennaArrayForPresentation.a[index] = bestValues.valuesOfBestMember[index];
			}
			delta = numberofElements;
		}
		
		if (phaseIsUsed) {
			// this is for phases
			for (int index = 0; index < numberofElements; index++) {
				antennaArrayForPresentation.alpha[index] = bestValues.valuesOfBestMember[index + delta];
			}
			delta += numberofElements;
		}
		
		if (positionIsUsed) {
			// this is for positions. It starts with 1 instead of 0
			antennaArrayForPresentation.d[0] = 0;
			for (int index = 1; index < numberofElements; index++) {
				antennaArrayForPresentation.d[index] = antennaArrayForPresentation.d[index - 1] + 0.5 + bestValues.valuesOfBestMember[index + delta];
			}
		}
		
		antennaArrayForPresentation.createPattern();
		
		for(int x=0; x<antennaArrayForPresentation.numberofSamplePoints; x++)
		{				
			if(updateOrAdd) //false:add and true:update
			{
				seriler.update(antennaArrayForPresentation.angle[x], antennaArrayForPresentation.pattern_dB[x]);
			}
			else
			{
				seriler.add(antennaArrayForPresentation.angle[x], antennaArrayForPresentation.pattern_dB[x]);
			}
		}
		
		convergenceSeries.add(differentialEvolution.iterationIndex, differentialEvolution.fitnessOfBestMember);
		iterationText.setText(Integer.toString(differentialEvolution.iterationIndex));
		costText.setText(Double.toString(differentialEvolution.fitnessOfBestMember));		

		// ------------- Outer Mask --------------------
		int numberOfSLLOuters = mask.SLL_outers.size(); 
		Mask.SidelobeLevel SLL_outer;
		
		for (int n = 0; n < numberOfSLLOuters; n++) {
			SLL_outer = mask.SLL_outers.get(n);
			if(updateOrAdd) //false:add and true:update
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
			if(updateOrAdd) //false:add and true:update
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
		
		updateOrAdd = true;	

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
		private boolean newStart;
		boolean iterationState = true;
		
		public AlgorithmExecuter() {
			keepIterating = false;
			newStart = true;
		}

		@Override
		protected Void doInBackground() throws Exception {
			while(!isCancelled())
			{
				while (keepIterating && iterationState) {
					iterationState = differentialEvolution.iterate();
					double[] valuesOfBestMember = new double[problemDimension];
					for (int d = 0; d < problemDimension; d++) {
						valuesOfBestMember[d] = differentialEvolution.members[d][differentialEvolution.bestMember];
					}
					publish(new BestValues(differentialEvolution.bestMember, differentialEvolution.fitnessOfBestMember, valuesOfBestMember));
				}
			}			
			return null;
		}
		
		@Override
		protected void process(List<BestValues> chunks) {
			bestValues = chunks.get(chunks.size()-1);
			if(iterationState == false)
			{	
				keepIterating = false;
				newStart = true;
				startStopButton.setText("Start Optimization");
			}
				
			drawPlot();
			
		}
	}
}
