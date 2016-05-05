package silisyum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DialogBoxForAddingMask extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9016107987493756122L;
	private final JPanel contentPanel = new JPanel();
	private JTextField maskName_textField;

	/**
	 * Create the dialog.
	 * @param b 
	 * @param string 
	 * @param userInterface 
	 */
	public DialogBoxForAddingMask(UserInterface _frame, String _title, boolean _modal) {
		super(_frame, _title, _modal);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[]"));
		{
			JLabel lblMaskName = new JLabel("Mask Name :");
			contentPanel.add(lblMaskName, "cell 0 0,alignx trailing");
		}
		{
			maskName_textField = new JTextField();
			contentPanel.add(maskName_textField, "cell 1 0,growx");
			maskName_textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton Add_btn = new JButton("Add");
				buttonPane.add(Add_btn);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
