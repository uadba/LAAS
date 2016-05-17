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
import javax.swing.table.AbstractTableModel;

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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
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
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

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
	
	private ChartPanel chartPanelPattern;
	private ChartPanel chartPanelConvergence;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;    
    
    private int numberofElements = 26;
    private int problemDimension;
    private double[] L = {0, 0, -0.1}; // initial values of amplitude, phase, and position minimum limits
    private double[] H = {1, 10, 0.1}; // initial values of amplitude, phase, and position maximum limits    
    private boolean amplitudeIsUsed = true;
    private boolean phaseIsUsed = false;
    private boolean positionIsUsed = true;
    private Mask mask;
    private int patterGraphResolution = 181; //721;
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
    private JTextPane messagePane;
    List<String> messagesOfErrors = new ArrayList<String>();
    private String messageToUser;
    private JPanel outerMaskPanel;
    private JTable outerTable;
    private JList<String> outerList;
    private JButton btnAddOuterMaskSegment;
    private DialogBoxForAddingOuterMaskSegment dialogBoxForAddingOuterMaskSegment;
    private DialogBoxForEditingOuterMaskSegment dialogBoxForEditingOuterMaskSegment;
    private DefaultListModel<String> listModelForOuterMaskSegments;
    private JButton btnDeleteOuterMaskSegment;
    private JPanel outerMaskSegmentOperations;
    private JButton btnEditOuterMaskSegment;
    private JLabel lblMaskSegmentNames;
    private JLabel lblSelectedMaskValues;
	private int selectedOuterMaskSegmentIndex = -1;
	private int selectedInnerMaskSegmentIndex = -1;
	private JPanel innerMaskPanel;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JPanel innerMaskSegmentOperations;
	private JLabel label;
	private JLabel label_1;
	private JList<String> innerList;
	private JTable innerTable;
	private DefaultListModel<String> listModelForInnerMaskSegments;
    private DialogBoxForAddingInnerMaskSegment dialogBoxForAddingInnerMaskSegment;
    private DialogBoxForEditingInnerMaskSegment dialogBoxForEditingInnerMaskSegment;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface frame = new UserInterface();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
		setTitle("Antenna Array Synthesizer");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1429, 991);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		mask = new Mask();
		dialogBoxForAddingOuterMaskSegment = new DialogBoxForAddingOuterMaskSegment(this, "Add a New Outer Mask Segment", true, mask);
		dialogBoxForEditingOuterMaskSegment = new DialogBoxForEditingOuterMaskSegment(this, "Edit a Outer Mask Segment", true, mask);
		
		dialogBoxForAddingInnerMaskSegment = new DialogBoxForAddingInnerMaskSegment(this, "Add a New Inner Mask Segment", true, mask);
		dialogBoxForEditingInnerMaskSegment = new DialogBoxForEditingInnerMaskSegment(this, "Edit a Inner Mask Segment", true, mask);
		
		createTemporaryMasks();
		
		seriler = new XYSeries("Pattern", false, false);
		maskOuter = new XYSeries("Outer Mask", false, false);
		maskInner = new XYSeries("Inner Mask", false, false);
		convergenceSeries = new XYSeries("Convergence Curve");
		XYSeriesCollection veri_seti = new XYSeriesCollection(seriler);
		XYSeriesCollection veri_seti2 = new XYSeriesCollection(convergenceSeries);
		veri_seti.addSeries(maskOuter);
		veri_seti.addSeries(maskInner);
		JFreeChart grafik = ChartFactory.createXYLineChart("A P", "Angle", "Array Factor (in dB)", veri_seti);
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
		
		lblNewLabel = new JLabel("Iteration Number:");
		panelConvergenceGraphProperties.add(lblNewLabel);
		
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
				if(validateParameters()) {
					if(ae.keepIterating == false) {
						if(ae.newStart) {
							getParametersFromUserInterface();
							calculateProblemDimension();
							createMainObjects();
							seriler.clear();
							maskOuter.clear();
							maskInner.clear();
							convergenceSeries.clear();
							ae.newStart = false;
							ae.iterationHasNotCompletedYet = true;
							sendMessageToPane("<font color=#006400><b>Optimization process has been <i>started</i> successfully!</b></font>", true);
							if(isThereAnyGapInOuterMask()) {
								sendMessageToPane("<br><font color=#666600>Warning: There is at least one gap in the <i>outer mask</i>. It does not affect the optimization process adversely but it may be the sign of a bad designed mask.</font>", false);								
							}
							if(isThereAnyGapInInnerMask()) {
								sendMessageToPane("<br><font color=#666600>Warning: There is at least one gap in the <i>inner mask</i>. It does not affect the optimization process adversely but it may be the sign of a bad designed mask.</font>", false);								
							}
						} else {
							sendMessageToPane("<br><font color=#006400><b>Optimization process has been <i>restarted</i>.</b></font>", false);
						}
						ae.keepIterating = true;
						startStopButton.setText("Stop Optimization");
						terminateOptimizationButton.setVisible(false);
						drawOuterMask();
						drawInnerMask();
					} else {
						ae.keepIterating = false;
						startStopButton.setText("Continue Optimization");
						terminateOptimizationButton.setVisible(true);
						sendMessageToPane("<br><font color=#006400><b>Optimization process has been <i>stopped</i>.</b></font>", false);
					}
				} else {
					presentErrorMessages();					
				}
				//Goto to tab 0. Is it necessary?
				//tabbedPaneForSettings.setSelectedIndex(0);
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
				sendMessageToPane("<br><font color=#006400><b>Optimization process has been </b></font> <font color=red><b><i>terminated</i></b></font> <font color=#006400><b>by the user</b></font>.", false);
			}
		});
		terminateOptimizationButton.setVisible(false);
		terminateOptimizationButton.setForeground(new Color(255, 255, 255));
		terminateOptimizationButton.setBackground(new Color(255, 69, 0));
		startStopPanel.add(terminateOptimizationButton, "cell 2 1,alignx center");
		
		tabbedPaneForSettings = new JTabbedPane(JTabbedPane.TOP);
		rightPannel.add(tabbedPaneForSettings, BorderLayout.CENTER);
		//table.setFillsViewportHeight(true);
		
		mainControlsPanel = new JPanel();
		tabbedPaneForSettings.addTab("Main Controls", null, mainControlsPanel, null);
		mainControlsPanel.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		lblMessages = new JLabel("Messages");
		mainControlsPanel.add(lblMessages, "cell 0 0");
		
		messagePane = new JTextPane();
		messagePane.setContentType("text/html");
		JScrollPane scrollPaneForList = new JScrollPane(messagePane);
		mainControlsPanel.add(scrollPaneForList, "cell 0 1,grow");		
		
		outerMaskPanel = new JPanel();
		tabbedPaneForSettings.addTab("Outer Mask", null, outerMaskPanel, null);
		outerMaskPanel.setLayout(new MigLayout("", "[180px,grow][grow]", "[][][grow]"));
		listModelForOuterMaskSegments = new DefaultListModel<String>();		
		outerList = new JList<String>(listModelForOuterMaskSegments);
		outerList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				selectedOuterMaskSegmentIndex = outerList.getSelectedIndex();				
				refreshOuterMaskSegmentDetailsTable();
			}
		});
		
		outerMaskSegmentOperations = new JPanel();
		outerMaskSegmentOperations.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Mask Segment Operations", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		FlowLayout fl_outerMaskSegmentOperations = (FlowLayout) outerMaskSegmentOperations.getLayout();
		fl_outerMaskSegmentOperations.setAlignment(FlowLayout.LEFT);
		outerMaskPanel.add(outerMaskSegmentOperations, "cell 0 0 2 1,grow");
		
		btnAddOuterMaskSegment = new JButton("Add");
		outerMaskSegmentOperations.add(btnAddOuterMaskSegment);
		
		btnEditOuterMaskSegment = new JButton("Edit");
		btnEditOuterMaskSegment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedOuterMaskSegmentIndex != -1) {
					Mask.MaskSegment outerMaskSegment = mask.outerMaskSegments.get(selectedOuterMaskSegmentIndex);
					
					dialogBoxForEditingOuterMaskSegment.setTextFields(selectedOuterMaskSegmentIndex, outerMaskSegment.name, Double.toString(outerMaskSegment.startAngle),
							Double.toString(outerMaskSegment.stopAngle), Integer.toString(outerMaskSegment.numberOfPoints), Double.toString(outerMaskSegment.level), Double.toString(outerMaskSegment.weight));
					
					dialogBoxForEditingOuterMaskSegment.setLocationRelativeTo(dialogBoxForEditingOuterMaskSegment.getParent());				
					dialogBoxForEditingOuterMaskSegment.setVisible(true);
					refreshOuterMaskSegmentsList();
					refreshOuterMaskSegmentDetailsTable();
					drawOuterMask();
				}
			}
		});
		outerMaskSegmentOperations.add(btnEditOuterMaskSegment);
		
		btnDeleteOuterMaskSegment = new JButton("Delete");
		btnDeleteOuterMaskSegment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {				
				if (selectedOuterMaskSegmentIndex != -1) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete THIS mask?", "Warning", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						mask.deleteOuterMaskSegments(selectedOuterMaskSegmentIndex);
						refreshOuterMaskSegmentsList();
						refreshOuterMaskSegmentDetailsTable();
						drawOuterMask();
					} 
				}
			}
		});
		outerMaskSegmentOperations.add(btnDeleteOuterMaskSegment);
		btnAddOuterMaskSegment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dialogBoxForAddingOuterMaskSegment.setLocationRelativeTo(dialogBoxForAddingOuterMaskSegment.getParent());
				dialogBoxForAddingOuterMaskSegment.setVisible(true);
				refreshOuterMaskSegmentsList();
				refreshOuterMaskSegmentDetailsTable();
				drawOuterMask();
			}
		});
		
		lblMaskSegmentNames = new JLabel("Mask Segment Names");
		outerMaskPanel.add(lblMaskSegmentNames, "cell 0 1,alignx center");
		
		lblSelectedMaskValues = new JLabel("Selected Mask Segment Values");
		outerMaskPanel.add(lblSelectedMaskValues, "cell 1 1,alignx center");
		outerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane outerListScroller = new JScrollPane(outerList);
		outerMaskPanel.add(outerListScroller, "cell 0 2,grow");
		refreshOuterMaskSegmentsList();
		
		outerTable = new JTable(new TableModelForOuterMask());
		JScrollPane scrollPaneForOuterTable = new JScrollPane(outerTable);
		outerMaskPanel.add(scrollPaneForOuterTable, "cell 1 2,grow");
		
		innerMaskPanel = new JPanel();
		tabbedPaneForSettings.addTab("Inner Mask", null, innerMaskPanel, null);
		innerMaskPanel.setLayout(new MigLayout("", "[180px,grow][grow]", "[][][grow]"));
		
		innerMaskSegmentOperations = new JPanel();
		FlowLayout flowLayout = (FlowLayout) innerMaskSegmentOperations.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		innerMaskSegmentOperations.setBorder(new TitledBorder(null, "Mask Segment Operations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		innerMaskPanel.add(innerMaskSegmentOperations, "cell 0 0 2 1,grow");
		
		button = new JButton("Add");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dialogBoxForAddingInnerMaskSegment.setLocationRelativeTo(dialogBoxForAddingInnerMaskSegment.getParent());
				dialogBoxForAddingInnerMaskSegment.setVisible(true);
				refreshInnerMaskSegmentsList();
				refreshInnerMaskSegmentDetailsTable();
				drawInnerMask();
			}
		});
		innerMaskSegmentOperations.add(button);
		
		button_1 = new JButton("Edit");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (selectedInnerMaskSegmentIndex != -1) {
					Mask.MaskSegment innerMaskSegment = mask.innerMaskSegments.get(selectedInnerMaskSegmentIndex);
					
					dialogBoxForEditingInnerMaskSegment.setTextFields(selectedInnerMaskSegmentIndex, innerMaskSegment.name, Double.toString(innerMaskSegment.startAngle),
							Double.toString(innerMaskSegment.stopAngle), Integer.toString(innerMaskSegment.numberOfPoints), Double.toString(innerMaskSegment.level), Double.toString(innerMaskSegment.weight));
					
					dialogBoxForEditingInnerMaskSegment.setLocationRelativeTo(dialogBoxForEditingInnerMaskSegment.getParent());				
					dialogBoxForEditingInnerMaskSegment.setVisible(true);
					refreshInnerMaskSegmentsList();
					refreshInnerMaskSegmentDetailsTable();
					drawInnerMask();
				}
			}
		});
		innerMaskSegmentOperations.add(button_1);
		
		button_2 = new JButton("Delete");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedInnerMaskSegmentIndex != -1) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete THIS mask?", "Warning", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						mask.deleteInnerMaskSegments(selectedInnerMaskSegmentIndex);
						refreshInnerMaskSegmentsList();
						refreshInnerMaskSegmentDetailsTable();
						drawInnerMask();
					} 
				}
			}
		});
		innerMaskSegmentOperations.add(button_2);
		
		label = new JLabel("Mask Segment Names");
		innerMaskPanel.add(label, "cell 0 1,alignx center");
		
		label_1 = new JLabel("Selected Mask Segment Values");
		innerMaskPanel.add(label_1, "cell 1 1,alignx center");
		
		listModelForInnerMaskSegments = new DefaultListModel<String>();
		innerList = new JList<String>(listModelForInnerMaskSegments);
		innerList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				selectedInnerMaskSegmentIndex = innerList.getSelectedIndex();				
				refreshInnerMaskSegmentDetailsTable();
			}
		});
		innerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane innerListScroller = new JScrollPane(innerList);
		innerMaskPanel.add(innerListScroller, "cell 0 2,grow");
		refreshInnerMaskSegmentsList();
		
		innerTable = new JTable(new TableModelForInnerMask());
		JScrollPane scrollPaneForInnerTable = new JScrollPane(innerTable);
		innerMaskPanel.add(scrollPaneForInnerTable, "cell 1 2,grow");
		
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
		
		textField_maximumValueAmplitude = new JTextField(Double.toString(H[0]));
		arrayParametersPanel.add(textField_maximumValueAmplitude, "cell 1 2,growx");
		textField_maximumValueAmplitude.setColumns(10);
		
		lblMinimumValueAmplitude = new JLabel("Minimum Value :");
		lblMinimumValueAmplitude.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMinimumValueAmplitude, "cell 0 3,alignx trailing");
		
		textField_minimumValueAmplitude = new JTextField(Double.toString(L[0]));
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
		
		textField_maximumValuePhase = new JTextField(Double.toString(H[1]));
		arrayParametersPanel.add(textField_maximumValuePhase, "cell 1 5,growx");
		textField_maximumValuePhase.setColumns(10);
		
		lblMinimumValuePhase = new JLabel("Minimum Value :");
		lblMinimumValuePhase.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMinimumValuePhase, "cell 0 6,alignx trailing");
		
		textField_minimumValuePhase = new JTextField(Double.toString(L[1]));
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
		
		textField_maximumValuePosition = new JTextField(Double.toString(H[2]));
		arrayParametersPanel.add(textField_maximumValuePosition, "cell 1 8,growx");
		textField_maximumValuePosition.setColumns(10);
		
		lblMinimumValuePosition = new JLabel("Minimum Value :");
		lblMinimumValuePosition.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMinimumValuePosition, "cell 0 9,alignx trailing");
		
		textField_minimumValuePosition = new JTextField(Double.toString(L[2]));
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
		
		drawOuterMask();
		drawInnerMask();
		
		ae = new AlgorithmExecuter();
		ae.execute();
	}
		
	//	For outer mask segment
	class TableModelForOuterMask extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3580573026741275615L;
		private String[] columnNames = {"Angle (Degree)",
                "Level (dB)",
                "Weight"};
	
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	
	    public int getRowCount() {
	    	if(selectedOuterMaskSegmentIndex == -1) return 0;
	    	Mask.MaskSegment SLL_outer;
			SLL_outer = mask.outerMaskSegments.get(selectedOuterMaskSegmentIndex);
			return SLL_outer.angles.length;
	    }
	
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	
	    public Object getValueAt(int row, int col) {
			Mask.MaskSegment SLL_outer;
			SLL_outer = mask.outerMaskSegments.get(selectedOuterMaskSegmentIndex);
			double returnedValue = 0;
			if(col == 0) returnedValue = SLL_outer.angles[row];
			if(col == 1) returnedValue = SLL_outer.levels[row];
			if(col == 2) returnedValue = SLL_outer.weights[row];
			return returnedValue;
	    }
	
	    public Class<?> getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	
	    /*
	     * Don't need to implement this method unless your table's
	     * editable.
	     */
	    public boolean isCellEditable(int row, int col) {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.
	        if (col < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	    /*
	     * Don't need to implement this method unless your table's
	     * data can change.
	     */
	    public void setValueAt(Object value, int row, int col) {
			Mask.MaskSegment SLL_outer;
			SLL_outer = mask.outerMaskSegments.get(selectedOuterMaskSegmentIndex);

			//if(col == 0) SLL_outer.angles[row] = (double) value;
			if(col == 1) SLL_outer.levels[row] = (double) value;
			if(col == 2) SLL_outer.weights[row] = (double) value;
	    	
	        fireTableCellUpdated(row, col);
	        
	        drawOuterMask();
	    }
	}
	
	//	For inner mask segment
	class TableModelForInnerMask extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6848149317192706675L;
		private String[] columnNames = {"Angle (Degree)",
                "Level (dB)",
                "Weight"};
	
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	
	    public int getRowCount() {
	    	if(selectedInnerMaskSegmentIndex == -1) return 0;
	    	Mask.MaskSegment innerMaskSegment;
			innerMaskSegment = mask.innerMaskSegments.get(selectedInnerMaskSegmentIndex);
			return innerMaskSegment.angles.length;
	    }
	
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	
	    public Object getValueAt(int row, int col) {
			Mask.MaskSegment innerMaskSegment;
			innerMaskSegment = mask.innerMaskSegments.get(selectedInnerMaskSegmentIndex);
			double returnedValue = 0;
			if(col == 0) returnedValue = innerMaskSegment.angles[row];
			if(col == 1) returnedValue = innerMaskSegment.levels[row];
			if(col == 2) returnedValue = innerMaskSegment.weights[row];
			return returnedValue;
	    }
	
	    public Class<?> getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	
	    /*
	     * Don't need to implement this method unless your table's
	     * editable.
	     */
	    public boolean isCellEditable(int row, int col) {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.
	        if (col < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	    /*
	     * Don't need to implement this method unless your table's
	     * data can change.
	     */
	    public void setValueAt(Object value, int row, int col) {
			Mask.MaskSegment innerMaskSegment;
			innerMaskSegment = mask.innerMaskSegments.get(selectedInnerMaskSegmentIndex);

			//if(col == 0) SLL_outer.angles[row] = (double) value;
			if(col == 1) innerMaskSegment.levels[row] = (double) value;
			if(col == 2) innerMaskSegment.weights[row] = (double) value;
	    	
	        fireTableCellUpdated(row, col);
	        
	        drawInnerMask();
	    }
	}

	private void refreshOuterMaskSegmentDetailsTable() {
		TableModelForOuterMask model = (TableModelForOuterMask) outerTable.getModel();
		model.fireTableDataChanged();		
	}

	private void refreshInnerMaskSegmentDetailsTable() {
		TableModelForInnerMask model = (TableModelForInnerMask) innerTable.getModel();
		model.fireTableDataChanged();		
	}
	
	private void refreshOuterMaskSegmentsList() {
		// ------------- Outer Mask --------------------
		int numberOfOuterMaskSegments = mask.outerMaskSegments.size(); 
		Mask.MaskSegment outerMaskSegment;
		
		listModelForOuterMaskSegments.removeAllElements();
		for (int n = 0; n < numberOfOuterMaskSegments; n++) {
			outerMaskSegment = mask.outerMaskSegments.get(n);
			listModelForOuterMaskSegments.addElement(outerMaskSegment.name);
		}
		
		selectedOuterMaskSegmentIndex = outerList.getSelectedIndex();
	}
	
	private void refreshInnerMaskSegmentsList() {
		// ------------- Inner Mask --------------------
		int numberOfInnerMaskSegments = mask.innerMaskSegments.size(); 
		Mask.MaskSegment innerMaskSegment;
		
		listModelForInnerMaskSegments.removeAllElements();
		for (int n = 0; n < numberOfInnerMaskSegments; n++) {
			innerMaskSegment = mask.innerMaskSegments.get(n);
			listModelForInnerMaskSegments.addElement(innerMaskSegment.name);
		}
		
		selectedInnerMaskSegmentIndex = innerList.getSelectedIndex();
	}
	
	private boolean isThereAnyGapInOuterMask() {
		boolean gapExistency = false;
		
		int numberOfOuterMaskSegments = mask.outerMaskSegments.size();
		Mask.MaskSegment outerMaskSegment;
		
		if (numberOfOuterMaskSegments > 0) {
			for (int n = 0; n < numberOfOuterMaskSegments - 1; n++) {
				outerMaskSegment = mask.outerMaskSegments.get(n);
				// Check the first angle (0)
				if (n == 0) {
					if (outerMaskSegment.startAngle != 0)
						gapExistency = true;
				}

				if (outerMaskSegment.stopAngle != mask.outerMaskSegments.get(n + 1).startAngle) {
					gapExistency = true;
				}
			}
			// Check the last angle (180)
			if (mask.outerMaskSegments.get(numberOfOuterMaskSegments - 1).stopAngle != 180) {
				gapExistency = true;
			} 
		}
		
		return gapExistency;
	}
	
	
	private boolean isThereAnyGapInInnerMask() {
		boolean gapExistency = false;
		
		int numberOfInnerMaskSegments = mask.innerMaskSegments.size();
		Mask.MaskSegment innerMaskSegment;
		
		if (numberOfInnerMaskSegments > 0) {
			for (int n = 0; n < numberOfInnerMaskSegments - 1; n++) {
				innerMaskSegment = mask.innerMaskSegments.get(n);
				// Check the first angle (0)
				if (n == 0) {
					if (innerMaskSegment.startAngle != 0)
						gapExistency = true;
				}
				
				if (innerMaskSegment.stopAngle != mask.innerMaskSegments.get(n + 1).startAngle) {
					gapExistency = true;
				}
			}
			// Check the last angle (180)
			if (mask.innerMaskSegments.get(numberOfInnerMaskSegments - 1).stopAngle != 180) {
				gapExistency = true;
			} 
		}
		
		return gapExistency;
	}
	
	
	
	private void sendMessageToPane(String additionalMessage, boolean deletePreviousMessages) {
		if (deletePreviousMessages)
			messageToUser = additionalMessage;
		else
			messageToUser += additionalMessage;
		messagePane.setText(messageToUser);
	}
	
	private void presentErrorMessages() {
		// How many error messages are there?
		int errors = messagesOfErrors.size();
		
		String tempMessage = null;
		if (errors == 1) {
			tempMessage = "Please press <b>Start Optimization</b> button again after fixing the following error:<br><br>";
			tempMessage += "<center><b>Error</b></center>";
			tempMessage += "<ul>";			
			tempMessage += "<li>";
			tempMessage += messagesOfErrors.get(0);
			tempMessage += "</li>";
			tempMessage += "</ul>";
		} else {
			tempMessage = "Please press <b>Start Optimization</b> button again after fixing the following errors:<br><br>";
			tempMessage += "<center><b>Errors</b></center>";			
			tempMessage += "<ol>";
			for (int i = 0; i < errors; i++) {
				tempMessage += "<li>";
				tempMessage += messagesOfErrors.get(i);
				tempMessage += "</li>";
			}
			tempMessage += "</ol>";
		}

		
		sendMessageToPane(tempMessage, true);
		
		messagesOfErrors.clear();
	}
	
	private boolean validateParameters() {
		boolean parametersAreValid = true;
		
		if(amplitudeIsUsed == false && phaseIsUsed == false && positionIsUsed == false)
		{
			parametersAreValid = false;
			messagesOfErrors.add("There is not any selected antenna parameters to optimize. At least one of them (amplitude, phase or position) must be selected.");
		}
		
		return parametersAreValid;
	}
	
	private void getParametersFromUserInterface() {
		// DE parameters
		numberofElements = Integer.parseInt(numberOfElements_Field.getText());
	    populationNumber = Integer.parseInt(populationNumber_textField.getText());
	    maximumIterationNumber = Integer.parseInt(maximumIterationNumber_textField.getText());
	    F = Double.parseDouble(F_textField.getText());
	    Cr = Double.parseDouble(Cr_textField.getText());
	    
	    // Antenna array parameters
	    H[0] = Double.parseDouble(textField_maximumValueAmplitude.getText());
	    L[0] = Double.parseDouble(textField_minimumValueAmplitude.getText());
	    H[1] = Double.parseDouble(textField_maximumValuePhase.getText());
	    L[1] = Double.parseDouble(textField_minimumValuePhase.getText());
	    H[2] = Double.parseDouble(textField_maximumValuePosition.getText());
	    L[2] = Double.parseDouble(textField_minimumValuePosition.getText());	    
	}
	
	private void calculateProblemDimension() {
		problemDimension = 0;
		if (amplitudeIsUsed) problemDimension = numberofElements;		
		if (phaseIsUsed) problemDimension += numberofElements;		
		if (positionIsUsed) problemDimension += numberofElements;
	}
	
	private void createMainObjects() {
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
			seriler.addOrUpdate(antennaArrayForPresentation.angle[x], antennaArrayForPresentation.pattern_dB[x]);
		}
		
		convergenceSeries.add(differentialEvolution.iterationIndex, differentialEvolution.fitnessOfBestMember);
		iterationText.setText(Integer.toString(differentialEvolution.iterationIndex));
		costText.setText(Double.toString(differentialEvolution.fitnessOfBestMember));		

		
		
		drawInnerMask();
		

	}

	private void drawOuterMask() {
		// ------------- Outer Mask --------------------
		int numberOfSLLOuters = mask.outerMaskSegments.size(); 
		Mask.MaskSegment SLL_outer;
		
		maskOuter.clear();
		for (int n = 0; n < numberOfSLLOuters; n++) {
			SLL_outer = mask.outerMaskSegments.get(n);
			for (int i = 0; i < SLL_outer.angles.length; i++) {
				if(i==0)
					maskOuter.addOrUpdate(SLL_outer.angles[i]+0.0000000001, SLL_outer.levels[i]);
				else
					maskOuter.addOrUpdate(SLL_outer.angles[i], SLL_outer.levels[i]);		
			}			
		}
	}

	private void drawInnerMask() {
		// ------------- Inner Mask --------------------
		int numberOfSLLInners = mask.innerMaskSegments.size(); 
		Mask.MaskSegment SLL_inner;

		maskInner.clear();
		for (int n = 0; n < numberOfSLLInners; n++) {
			SLL_inner = mask.innerMaskSegments.get(n);
			for (int i = 0; i < SLL_inner.angles.length; i++) {
				if(i==0)
					maskInner.addOrUpdate(SLL_inner.angles[i]+0.0000000001, SLL_inner.levels[i]);
				else
					maskInner.addOrUpdate(SLL_inner.angles[i], SLL_inner.levels[i]);		
			}			
		}
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
		boolean iterationHasNotCompletedYet = true;
		
		public AlgorithmExecuter() {
			keepIterating = false;
			newStart = true;
		}

		@Override
		protected Void doInBackground() throws Exception {
			while(!isCancelled())
			{
				while (keepIterating && iterationHasNotCompletedYet) {
					iterationHasNotCompletedYet = differentialEvolution.iterate();
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
			if(iterationHasNotCompletedYet == false)
			{	
				keepIterating = false;
				newStart = true;
				startStopButton.setText("Start Optimization");
				String tempMessage = messagePane.getText();
				tempMessage += "<br>Iterion has been completed";
				messagePane.setText(tempMessage);
			}
				
			drawPlot();
			
		}
	}
	
	private void createTemporaryMasks() {
		
//		mask.addNewOuterMaskSegments("SLL_01", 0, 20, 20, -24, 1);
//		mask.addNewOuterMaskSegments("SLL_02", 20, 30, 10, -40, 1);
//		mask.addNewOuterMaskSegments("SLL_03", 30, 79, 49, -20, 1);
//		mask.addNewOuterMaskSegments("SLL_04", 79, 80, 5, -60, 1);
//		mask.addNewOuterMaskSegments("SLL_05", 80, 100, 20, 0, 1);
//		mask.addNewOuterMaskSegments("SLL_06", 100, 110, 10, -20, 1);
//		mask.addNewOuterMaskSegments("SLL_07", 110, 115, 15, -40, 1);
//		mask.addNewOuterMaskSegments("SLL_08", 115, 180, 65, -24, 1);		
//
//		mask.addNewInnerMaskSegments("SLL_01", 0, 40, 3, -95, 1);
//		mask.addNewInnerMaskSegments("SLL_02", 40, 60, 30, -30, 1);
//		mask.addNewInnerMaskSegments("SLL_03", 60, 70, 20, -35, 1);
//		mask.addNewInnerMaskSegments("SLL_04", 70, 150, 3, -95, 1);
//		mask.addNewInnerMaskSegments("SLL_05", 150, 160, 10, -40, 1);
//		mask.addNewInnerMaskSegments("SLL_06", 160, 180, 3, -95, 1);
		
		mask.addNewOuterMaskSegments("SLL_01_out", 0, 83, 84, -35, 1);
		mask.addNewOuterMaskSegments("SLL_02_out", 83, 97, 3, 0, 1);
		mask.addNewOuterMaskSegments("SLL_03_out", 97, 180, 84, -35, 1);

		mask.addNewInnerMaskSegments("SLL_01_in", 0, 88, 2, -95, 1);
		mask.addNewInnerMaskSegments("SLL_02_in", 88, 92, 2, -3, 1);
		mask.addNewInnerMaskSegments("SLL_03_in", 92, 180, 2, -95, 1);
		
	}
}
