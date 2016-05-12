package silisyum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class DialogBoxForAddingMask extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9016107987493756122L;
	private final JPanel contentPanel = new JPanel();
	private JTextField maskName_textField;
	private Mask mask;
	private JTextField starAngle_textField;
	private JTextField stopAngle_textField;
	private JTextField numberOfPoints_textField;
	private JTextField level_textField;
	private JTextField weight_textField;

	/**
	 * Create the dialog.
	 * @param b 
	 * @param string 
	 * @param userInterface 
	 */
	public DialogBoxForAddingMask(UserInterface _frame, String _title, boolean _modal, Mask _mask) {
		super(_frame, _title, _modal);
		setTitle("Add Outer Mask");
		mask = _mask;
		//setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 236);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][][]"));
		{
			JLabel lblMaskName = new JLabel("Mask Name :");
			lblMaskName.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblMaskName, "cell 0 0,alignx trailing");
		}
		{
			maskName_textField = new JTextField();
			maskName_textField.setText("Test_SLL_03");
			contentPanel.add(maskName_textField, "cell 1 0,growx");
			maskName_textField.setColumns(10);
		}
		{
			JLabel lblStartAngle = new JLabel("Start Angle :");
			lblStartAngle.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblStartAngle, "cell 0 1,growx,aligny baseline");
		}
		{
			starAngle_textField = new JTextField();
			starAngle_textField.setText("30");
			starAngle_textField.setColumns(10);
			contentPanel.add(starAngle_textField, "cell 1 1,growx");
		}
		{
			JLabel lblStopAngle = new JLabel("Stop Angle :");
			lblStopAngle.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblStopAngle, "cell 0 2,alignx trailing");
		}
		{
			stopAngle_textField = new JTextField();
			stopAngle_textField.setText("79");
			stopAngle_textField.setColumns(10);
			contentPanel.add(stopAngle_textField, "cell 1 2,growx");
		}
		{
			JLabel lblNumberOfPoints = new JLabel("Number of Points :");
			lblNumberOfPoints.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNumberOfPoints, "cell 0 3,alignx trailing");
		}
		{
			numberOfPoints_textField = new JTextField();
			numberOfPoints_textField.setText("49");
			numberOfPoints_textField.setColumns(10);
			contentPanel.add(numberOfPoints_textField, "cell 1 3,growx");
		}
		{
			JLabel lblLevel = new JLabel("Level :");
			lblLevel.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblLevel, "cell 0 4,alignx trailing");
		}
		{
			level_textField = new JTextField();
			level_textField.setText("-20");
			level_textField.setColumns(10);
			contentPanel.add(level_textField, "cell 1 4,growx");
		}
		{
			JLabel lblWeight = new JLabel("Weight :");
			lblWeight.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblWeight, "cell 0 5,alignx trailing");
		}
		{
			weight_textField = new JTextField();
			weight_textField.setText("1");
			weight_textField.setColumns(10);
			contentPanel.add(weight_textField, "cell 1 5,growx");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton Add_btn = new JButton("Add Outer Mask");
				Add_btn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int numberOfSLLOuters = mask.SLL_outers.size(); 
						Mask.SidelobeLevel SLL_outer;
						
						boolean itIsANewName = true;
						for (int n = 0; n < numberOfSLLOuters; n++) {							
							SLL_outer = mask.SLL_outers.get(n);
							if(maskName_textField.getText().equals(SLL_outer.name)) {
								JOptionPane.showMessageDialog(null, "There is a mask in the list with a same name with which you want to add. You should change the name of the new mask.");
								itIsANewName = false;
								break;
							}
						}
						
						if (itIsANewName) {
							double starAngle = Double.parseDouble(starAngle_textField.getText());
							double stopAngle = Double.parseDouble(stopAngle_textField.getText());
							int numberOfPoints = Integer.parseInt(numberOfPoints_textField.getText());
							double level = Double.parseDouble(level_textField.getText());
							double weight = Double.parseDouble(weight_textField.getText());
							mask.addNewSLL_outer(maskName_textField.getText(), starAngle, stopAngle, numberOfPoints, level, weight);
							setVisible(false);
						}
					}
				});
				buttonPane.add(Add_btn);
			}
		}
	}

}
