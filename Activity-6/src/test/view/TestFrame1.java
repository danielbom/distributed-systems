package test.view;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Teste básico de utilização de 'JFrame'.
 * 
 * @author daniel e mara
 */
public class TestFrame1 extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	String[] cursos = {
	    "ciência da computação", "engenharia ambiental",
	    "engenharia civil", "engenharia de alimentos",
	    "engenharia eletrônica", "engenharia química",
	    "licenciatura em química"
	};
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TestFrame1 frame = new TestFrame1();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestFrame1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 2, 0, 0));
		
		panel = new JPanel();
		contentPane.add(panel);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("codigo");
		model.addColumn("nome");
		for (int i = 0; i < cursos.length; i++) {
			model.addRow(new Object[] {i, cursos[i]});
		}
		
		scrollPane.setViewportView(table);
	}

}
