package test.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Teste básico para a criação de 'JDialog'.
 * 
 * @author daniel e mara
 */
public class TestDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TestDialog dialog = new TestDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TestDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 52, 426, 107);
		contentPanel.add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("codigo");
		model.addColumn("nome");
		String[] cursos = {
		    "ciência da computação",
		    "engenharia ambiental",
		    "engenharia civil",
		    "engenharia de alimentos",
		    "engenharia eletrônica",
		    "engenharia química",
		    "licenciatura em química"
		};
		for (int i = 0; i < cursos.length; i++) {
			model.addRow(new Object[] {i, cursos[i]});
		}
		
		scrollPane.setViewportView(table);
	}
}
