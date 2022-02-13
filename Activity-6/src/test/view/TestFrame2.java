package test.view;

import java.awt.EventQueue;
import java.awt.Label;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Atualização cuidadosa sobre o teste TesteFrame1.
 * 
 * @author daniel e mara
 */
public class TestFrame2 extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame2 frame = new TestFrame2();
					frame.setLocationRelativeTo(null);
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
	public TestFrame2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 0, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("Adicionar");
		button.setBounds(50, 131, 114, 25);
		button.addActionListener(evt -> {
			System.out.println("Adicionar " + evt);
			int row = table.getSelectedRow();
			int column = table.getSelectedColumn();
			System.out.println(row + "," + column);
			int id = (int) table.getValueAt(row, 0);
			System.out.println(id);
		});
		contentPane.add(button);
		
		button = new JButton("Alterar");
		button.setBounds(230, 131, 114, 25);
		button.addActionListener(evt -> {
			System.out.println("Alterar " + evt);
		});
		contentPane.add(button);
		
		button = new JButton("Remover");
		button.setBounds(426, 131, 114, 25);
		button.addActionListener(evt -> {
			System.out.println("Remover " + evt);
		});
		contentPane.add(button);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 566, 90);
		contentPane.add(panel);
		panel.setLayout(null);
		{
			TextField txtParams = new TextField();
			txtParams.setBounds(94, 57, 370, 20);
			panel.add(txtParams);
			
			Label labelParametros = new Label("Parametros: ");
			labelParametros.setBounds(10, 57, 90, 20);
			panel.add(labelParametros);
			
			button = new JButton("Buscar");
			button.setBounds(470, 57, 86, 23);
			button.addActionListener(evt -> {
				System.out.println("Click [Buscar] - " + evt);
			});
			panel.add(button);
			
			Label label = new Label("Recurso: ");
			label.setBounds(10, 30, 68, 21);
			panel.add(label);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(94, 27, 167, 24);
			comboBox.addActionListener(evt -> {
				System.out.println("Click [comboBox] - " + evt);
			});
			panel.add(comboBox);
			
			comboBox.addItem("Cursos");
			comboBox.addItem("Disciplinas");
			comboBox.addItem("Estudantes");
		}
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 180, 566, 380);
		contentPane.add(scrollPane);
		
		this.updateTable("");
	}
	
	private void updateTable(String resource) {
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	        	return false;
	        };
	    };
	    table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model.addColumn("codigo");
		model.addColumn("nome");
		model.addTableModelListener(evt -> {
			System.out.println("Table event " + evt);
			System.out.println(evt.getLastRow());
			System.out.println(evt.getSource());
		});
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
