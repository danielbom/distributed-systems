package view;

import java.awt.EventQueue;
import java.util.Properties;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import core.Service;
import core.ServiceResult;
import core.Utils;
import models.AlunoView;
import models.Curso;
import models.Disciplina;

/**
 * Classe Frame principal de inicialização do sistema.
 * 
 * @author daniel e mara
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private Service service;
	
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JComboBox comboDisciplina, comboCurso;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 5005;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(host, port);
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
	public MainFrame(String host, int port) {
		service = new Service(host, port);
		this.initiateComponents();
	}


	private void initiateComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 782, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel comboxPanel = new JPanel();
		comboxPanel.setBounds(12, 12, 748, 87);
		contentPane.add(comboxPanel);
		comboxPanel.setLayout(null);
		
		JLabel lblDisciplina = new JLabel("Disciplina:");
		lblDisciplina.setBounds(18, 56, 80, 15);
		comboxPanel.add(lblDisciplina);
		
		comboDisciplina = new JComboBox();
		comboDisciplina.setBounds(102, 51, 634, 24);
		comboDisciplina.addActionListener(evt -> this.listenerComboDisciplina());
		comboxPanel.add(comboDisciplina);

		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(18, 17, 66, 15);
		comboxPanel.add(lblCurso);
		
		comboCurso = new JComboBox();
		comboxPanel.add(comboCurso);
		comboCurso.setBounds(102, 12, 634, 24);
		comboCurso.addActionListener(evt -> this.listenerComboCurso());
		initializeComboCurso();
		
		JButton button = new JButton("Adicionar nota");
		button.setBounds(149, 111, 144, 25);
		button.addActionListener(evt -> this.listenerAddButton());
		contentPane.add(button);
		
		button = new JButton("Alterar nota");
		button.setBounds(321, 111, 144, 25);
		button.addActionListener(evt -> this.listenerUpdateButton());
		contentPane.add(button);
		
		button = new JButton("Remover nota");
		button.setBounds(493, 111, 155, 25);
		button.addActionListener(evt -> this.listenerDeleteButton());
		contentPane.add(button);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 160, 758, 300);
		contentPane.add(scrollPane);
	}

	private void initializeComboCurso() {
		Utils.log("Inicializando cursos");
		ServiceResult<Curso> result = service.getAllCursos();
		
		System.out.println("Cabeçalho.");
		result.getHeader().forEach(head -> {
			System.out.println(head);
		});
		System.out.println();
		
		System.out.println("Valores");
		result.getValues().forEach(value -> {
			this.comboCurso.addItem(value);
		});
	}


	private void listenerDeleteButton() {
		if (table == null) return;
		
		int row = table.getSelectedRow();
		int column = table.getSelectedColumn();
		if (row == -1 || column == -1) return;
		
		int ra = (int) table.getValueAt(row, 0);
		String cod_disciplina = (String) table.getValueAt(row, 4);
		int ano = (int) table.getValueAt(row, 8);
		int semestre = (int) table.getValueAt(row, 5);
		Utils.log("Remove: ra [" + ra + "] " + "cod_disciplina [" + cod_disciplina
				+ "] ano [" + ano + "] semestre [" + semestre + "]");
		if (service != null) {
			service.deleteNota(ra, cod_disciplina, ano, semestre);
			this.updateTable();
		}
	}
	
	private void listenerUpdateButton() {
		if (table == null) return;
		
		int row = table.getSelectedRow();
		int column = table.getSelectedColumn();
		Utils.log("Alterar [" + row + "," + column + "]");
		if (row == -1 || column == -1) return;
		
		int ra = (int) table.getValueAt(row, 0);
		String nome = (String) table.getValueAt(row, 1);
		String cod_disciplina = (String) table.getValueAt(row, 4);
		int ano = (int) table.getValueAt(row, 8);
		int semestre = (int) table.getValueAt(row, 5);
		float nota = (float) table.getValueAt(row, 6);
		int faltas = (int) table.getValueAt(row, 7);
		
		Disciplina disciplina = (Disciplina) comboDisciplina.getSelectedItem();
		Utils.log("Atualizando nota para: RA(" + ra + ") disciplina: " + cod_disciplina);
		
		Consumer<Properties> consumer = (props) -> {
			int anoC = Integer.parseInt((String) props.get("ano"));
			int semestreC = Integer.parseInt((String) props.get("semestre"));
			float notaC = Float.parseFloat((String) props.get("nota"));
			int faltasC = Integer.parseInt((String) props.get("faltas"));
			Utils.log("Atualizando nota");
			Utils.log("Ano [" + anoC + "] " + " Semestre [" + semestreC + "]");
			Utils.log("Nota [" + notaC + "] " + " Faltas [" + faltasC + "]");
			if (service != null) {
				this.service.putNota(ra, cod_disciplina, anoC, semestreC, notaC, faltasC);
				this.updateTable();
			}
		};
		

		try {
			FormNota dialog = new FormNota(this, nome, disciplina.getNome(),
					ra, ano, semestre, nota, faltas, cod_disciplina,
					"Atualizar nota", consumer);
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private void listenerAddButton() {
		if (table == null) return;
		
		int row = table.getSelectedRow();
		if (row == -1) return;

		int ra = (int) table.getValueAt(row, 0);
		String nome = (String) table.getValueAt(row, 1);
		String cod_disciplina = (String) table.getValueAt(row, 4);
		Utils.log("Adicionando nota para: RA(" + ra + ") disciplina: " + cod_disciplina);
		Disciplina disciplina = (Disciplina) comboDisciplina.getSelectedItem();
		
		Consumer<Properties> consumer = (props) -> {
			int ano = Integer.parseInt((String) props.get("ano"));
			int semestre = Integer.parseInt((String) props.get("semestre"));
			float nota = Float.parseFloat((String) props.get("nota"));
			int faltas = Integer.parseInt((String) props.get("faltas"));
			Utils.log("Adicionando nota");
			Utils.log("Ano [" + ano + "] " + " Semestre [" + semestre + "]");
			Utils.log("Nota [" + nota + "] " + " Faltas [" + faltas + "]");
			if (service != null) {
				this.service.postNota(ra, cod_disciplina, ano, semestre, nota, faltas);
				this.updateTable();
			}
		};

		try {
			FormNota dialog = new FormNota(this, nome, disciplina.getNome(),
					ra, cod_disciplina, "Adicionar nota", consumer);
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void listenerComboDisciplina() {
		if (comboDisciplina == null) return;
		Disciplina selected = (Disciplina) comboDisciplina.getSelectedItem();
		System.out.println("Disciplina selecionada: " + selected);
		this.updateTable();
	}
	
	private void listenerComboCurso() {
		if (comboCurso == null) return;
		Curso curso = (Curso) comboCurso.getSelectedItem();
		int cod_curso = curso.getCodigo();
		System.out.println("Selecionado: " + curso + " " + cod_curso);
		
		for (int i = 0; i < comboDisciplina.getItemCount(); i++)
			comboDisciplina.removeItemAt(i);
		
		ServiceResult<Disciplina> result = service.getDisciplinaByCodCurso(cod_curso);
		
		if (result != null) {
			System.out.println("Cabeçalho.");
			result.getHeader().forEach(head -> {
				System.out.println(head);
			});
			System.out.println();
			
			System.out.println("Valores");
			result.getValues().forEach(value -> {
				this.comboDisciplina.addItem(value);
			});
			this.updateTable();
		}
	}

	public void updateTable() {
		if (scrollPane == null) return;
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	        	return false;
	        };
	    };
	    table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model.addColumn("RA");
		model.addColumn("Nome");
		model.addColumn("Periodo");
		model.addColumn("Codigo curso");
		model.addColumn("Codigo disciplina");
		model.addColumn("Semestre");
		model.addColumn("Nota");
		model.addColumn("Faltas");
		model.addColumn("Ano");
		
		Disciplina disciplina = (Disciplina) comboDisciplina.getSelectedItem();
		ServiceResult<AlunoView> result = service.getAlunos(disciplina.getCodigo());
		
		if (result != null) {
			System.out.println("Cabeçalho.");
			result.getHeader().forEach(head -> {
				System.out.println(head);
			});
			System.out.println();
			
			System.out.println("Valores");
			result.getValues().forEach(value -> model.addRow(value.getObjectArray()));
		}
		
		scrollPane.setViewportView(table);
	}
	
}
