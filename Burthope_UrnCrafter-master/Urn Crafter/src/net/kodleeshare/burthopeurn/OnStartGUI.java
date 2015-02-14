package net.kodleeshare.burthopeurn;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Urn;
import net.kodleeshare.generic.UrnProcess;
import net.kodleeshare.generic.UrnProcess.method;

public class OnStartGUI extends JFrame
{

	private JPanel		contentPane;
	private JTable		table;
	private JComboBox	input_urntype;
	private JSpinner	input_amt;
	private JComboBox	input_method;

	/**
	 * Create the frame.
	 */
	public OnStartGUI()
	{
		Global.updateStatus("Waiting for use input");
		setTitle("FS Urn Crafter");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 290, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("FS Urn Crafter");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 255, 20);
		contentPane.add(lblNewLabel);

		input_urntype = new JComboBox();
		input_urntype.setToolTipText("What type of urn do you want made?");
		input_urntype.setModel(new DefaultComboBoxModel(Urn.URNS_ALL_ALPHA));
		input_urntype.setBounds(20, 40, 235, 20);
		contentPane.add(input_urntype);

		JButton button_add = new JButton("Add Urn");
		button_add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if ((int) input_amt.getValue() == 0)
					return;
				((DefaultTableModel) table.getModel()).addRow(new Object[] { (Urn) input_urntype.getSelectedItem(), (int) input_amt.getValue(), (String) input_method.getSelectedItem() });
				input_amt.setValue(0);
				input_urntype.setSelectedIndex(0);
			}
		});
		button_add.setBounds(20, 150, 100, 23);
		contentPane.add(button_add);

		JButton button_remove = new JButton("Remove Urn");
		button_remove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int[] rows = table.getSelectedRows();
				for (int i = 0; i < rows.length; i++)
				{
					model.removeRow(rows[i]
							- i);
				}
			}
		});
		button_remove.setBounds(155, 150, 100, 23);
		contentPane.add(button_remove);

		input_amt = new JSpinner();
		input_amt.setToolTipText("How many urns?");
		input_amt.setModel(new SpinnerNumberModel(new Integer(0), new Integer(-1), null, new Integer(1)));
		input_amt.setBounds(20, 110, 235, 20);
		contentPane.add(input_amt);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 183, 235, 139);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Urn Type", "#", "Method" })
		{
			Class[]	columnTypes	= new Class[] { Object.class, Object.class, String.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			boolean[]	columnEditables	= new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(0).setMinWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setMinWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setFillsViewportHeight(true);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
				Object[][] tableData = new Object[nRow][nCol];
				for (int i = 0; i < nRow; i++)
					for (int j = 0; j < nCol; j++)
						tableData[i][j] = dtm.getValueAt(i, j);
				Vars.UrnsToMake = new UrnProcess[tableData.length];
				for (int i = 0; i < tableData.length; i++)
				{
					method howTo = null;
					switch ((String) tableData[i][2])
					{

						case "Use soft clay":
							howTo = UrnProcess.method.BANKSOFTCLAY;
							break;
						case "Use clay":
							howTo = UrnProcess.method.BANKCLAY;
							break;
						case "Mine clay":
						default:
							howTo = UrnProcess.method.MINE;
							break;
					}
					Vars.UrnsToMake[i] = new UrnProcess((Urn) tableData[i][0], (int) tableData[i][1], howTo);
				}
				Global.setLoaded();
				dispose();
			}
		});
		btnNewButton.setBounds(20, 333, 235, 23);
		contentPane.add(btnNewButton);

		input_method = new JComboBox();
		input_method.setToolTipText("How should I get the clay?");
		input_method.setModel(new DefaultComboBoxModel(new String[] { "Mine clay", "Use clay", "Use soft clay" }));
		input_method.setBounds(20, 70, 235, 20);
		contentPane.add(input_method);

		JLabel lblifTheBank = new JLabel("[If the bank runs out, it will default to mining]");
		lblifTheBank.setHorizontalAlignment(SwingConstants.RIGHT);
		lblifTheBank.setForeground(Color.GRAY);
		lblifTheBank.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblifTheBank.setBounds(20, 90, 235, 14);
		contentPane.add(lblifTheBank);

		JLabel lbluseFor = new JLabel("[Use -1 for infinite]");
		lbluseFor.setHorizontalAlignment(SwingConstants.RIGHT);
		lbluseFor.setForeground(Color.GRAY);
		lbluseFor.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lbluseFor.setBounds(20, 130, 235, 14);
		contentPane.add(lbluseFor);

		this.setVisible(true);
	}
}
