package view;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import models.Aluno;
import models.Curso;
import models.Disciplina;
import models.Matricula;

/**
 * Classe principal de visualização do sistema utilizando 'mocks'.
 * 
 * @author daniel e mara
 */
public class MainFrameMocks extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JComboBox comboDisciplina, comboCurso;
	private JTable table;
	
	String[] cursos = {
	    "ciência da computação",
	    "engenharia ambiental",
	    "engenharia civil",
	    "engenharia de alimentos",
	    "engenharia eletrônica",
	    "engenharia química",
	    "licenciatura em química"
	};

	String[] disciplinas = {
	    "LM31A - Lógica Matemática (IC1A_CM)",
	    "IC31A - INTRODUÇÃO A CIENCIA DA COMPUTAÇÃO (IC1A_CM)",
	    "GA3X1 - GEOMETRIA ANALÍTICA E ÁLGEBRA LINEAR (IC1A_CM)",
	    "EP3XA - Ética, Profissão e Cidadania (IC1A_CM)",
	    "CV31A - COMUNICAÇÃO LINGUISTICA (IC1A_CM)",
	    "CD3X1 - CALCULO DIFERENCIAL E INTEGRAL 1 (IC1A_CM)",
	    "BCC31A - ALGORITMOS (IC1B_CM)",
	    "BCC31A - ALGORITMOS (IC1A_CM)",
	    "AT31A - Atividades Complementares (IC2A_CM)",
	    "PB3XA - PROBABILIDADE E ESTATÍSTICA (IC2A_CM)",
	    "BCC32C - FUNDAMENTOS DE ADMINISTRACAO (IC2A_CM)",
	    "FS3X3 - FISICA 3 (IC2B_CM)",
	    "FS3X3 - FISICA 3 (IC2A_CM)",
	    "BCC32B - Elementos de Lógica Digital",
	    "BCC32A - ALGORITMOS E ESTRUTURAS DE DADOS 1 (IC2A_CM)",
	    "BCC33A - Algoritmos E Estruturas De Dados 2 (IC3A_CM)",
	    "BCC33D - ANALISE E PROJETO ORIENTADO A OBJETOS (IC3B_CM)",
	    "BCC33A - Algoritmos E Estruturas De Dados 2 (IC3B_CM)",
	    "BCC33C - INTERACAO HOMEM-COMPUTADOR (IC3A_CM)",
	    "CN3XA - CALCULO NUMERICO (IC3A_CM)",
	    "BCC33E - BANCO DE DADOS 1 (IC3B_CM)",
	    "BCC33E - BANCO DE DADOS 1 (IC3A_CM)",
	    "BCC33B - ARQUITETURA E ORGANIZACAO DE COMPUTADORES (IC3B_CM)",
	    "BCC33B - ARQUITETURA E ORGANIZACAO DE COMPUTADORES (IC3A_CM)",
	    "BCC33D - ANALISE E PROJETO ORIENTADO A OBJETOS (IC3A_CM)",
	    "BCC33A - ALGORITMOS E ESTRUTURAS DE DADOS 2",
	    "BCC34A - Analise De Algoritmos (IC4A_CM)",
	    "BCC34A - Analise De Algoritmos (IC4A)",
	    "BCC34C - SISTEMAS MICROCONTROLADOS (IC4B_CM)",
	    "BCC34G - SISTEMAS OPERACIONAIS (IC4A_CM)",
	    "BCC34C - SISTEMAS MICROCONTROLADOS (IC4A_CM)",
	    "BCC34D - REDES DE COMPUTADORES 1 (IC4A_CM)",
	    "BCC34F - PROGRAMACAO DE APLICATIVOS (IC4A_CM)",
	    "BCC34B - LINGUAGENS FORMAIS, AUTOMATOS E COMPUTABILIDADE (IC4A_CM)",
	    "BCC34E - BANCO DE DADOS 2 (IC4A_CM)",
	    "BCC35B - TEORIA DOS GRAFOS (IC5A_CM)",
	    "BCC35D - REDES DE COMPUTADORES 2 (IC5A_CM)",
	    "BCC35C - PROJETO INTEGRADOR (IC5A_CM)",
	    "BCC35A - Linguagens de Programação (IC5A_CM)",
	    "BCC35G - INTELIGENCIA ARTIFICIAL (IC5A_CM)",
	    "BCC35E - Engenharia de Software 1 (IC5A_CM)",
	    "BCC35F - Computação Gráfica (IC5A_CM)",
	    "BCC36C - SISTEMAS DISTRIBUIDOS (IC6A_CM)",
	    "BCC36D - SEGURANCA E AUDITORIA DE SISTEMAS (IC6A_CM)",
	    "BCC36F - Processamento de Imagens (IC6A_CM)",
	    "BCC36A - METODOLOGIA DE PESQUISA (IC6A_CM)",
	    "BCC36E - ENGENHARIA DE SOFTWARE 2 (IC6A_CM)",
	    "BCC36B - Compiladores (IC6A_CM)",
	    "BCC37C - Trabalho De Conclusão De Curso I (IC71A_CM)",
	    "BCC37B - Topicos Avancados Em Ciencia Da Computacao 1 (IC7B_CM)",
	    "BCC37C - TRABALHO DE CONCLUSÃO DE CURSO I (IC7A_CM)",
	    "BCC37B - TOPICOS AVANCADOS EM CIENCIA DA COMPUTACAO 1 (IC7A_CM)",
	    "BCC38B - Topicos Avancados Em Ciencia Da Computacao 2 (IC8B_CM)",
	    "ES38A - Estagio Supervisionado (IC8A_CM)",
	    "TCC2 - TRABALHO DE CONCLUSÃO DE CURSO (IC8A_CM)",
	    "LT39C - EMPREENDEDORISMO (IC8A_CM)",
	    "BCC38B - TOPICOS AVANCADOS EM CIENCIA DA COMPUTACAO 2 (IC8A_CM)",
	    "BCC38A - EMPREENDEDORISMO (IC8A_CM)"
	};
	
	int[] matricula_ra = {
	    23,39,18,50,19,35,8,43,13,39,44,48,44,15,36,36,27,42,35,15,37,23,5,17,23,19,2,
	    22,38,2,11,19,28,9,37,14,6,22,49,19,12,14,18,2,47,31,3,41,38,43,9,3,20,33,46,17,
	    26,32,4,20,35,47,22,25,22,44,16,7,50,42,33,22,9,47,5,40,45,40,50,5,22,3,25,44,
	    47,46,26,34,10,14,11,35,32,33,48,24,9,18,28,27
	};
	
	String[] matricula_cod_dis = {
	    "BCC36B","MG3XB","EL39A","FS3X3","TLD37O","LQM36C","EL38L","BCC33B","EA31F",
	    "LQM35B","CV37B","EA34E","BCC35D","LQ34C","EA38B","LT34D","EL37D","LQ33E",
	    "EL37L","FS3X3","LQM32A","LQ32G","PB3XB","MG3XC","LT34B","LQ34B","BCC31A",
	    "LQ34E","EA36C","LQ32C","EL32J","CV34C","LQM36E","EA37B","LQM38A","EA36B",
	    "MP3XA","BCC34B","EA32D","GA3X1","FS3X3","LQM32B","EA36D","FS3XB","EA32E",
	    "LQM32B","LQM34G","EL37L","GA3X1","EP3XA","EL33K","MG3XC","LT35C","LQ32H",
	    "LQM34F","LQ34B","CV32B","CV36E","EL36I","EL35L","EA38D","CV32C","CV34E",
	    "FS3X3","EA36C","EL35P","EA33C","BCC33A","CD3X3","EL35N","LQ31A","EA34G",
	    "LQM33G","LQM36F","CV36G","LQM33F","CV36D","LT34D","BCC33A","BCC36D",
	    "EA38C","EA34H","EA32D","EL35I","BCC37B","LQM33G","EA39B","EA33G","EL39G",
	    "FS3X3","LQM37B","EL36I","LQM35F","LQ33D","EA32G","EL37M","LQ32G","LQ36C",
	    "CV36A","BCC34C"
	};
	
	int[] matricula_ano = {
	    2019,2017,2017,2017,2017,2017,2019,2018,2019,2017,2017,2017,2017,2018,2017,2018,
	    2017,2017,2018,2019,2017,2018,2019,2019,2019,2019,2019,2017,2018,2019,2017,2019,
	    2018,2018,2019,2019,2017,2018,2019,2018,2019,2018,2019,2018,2019,2018,2018,2018,
	    2018,2019,2019,2018,2018,2019,2017,2019,2019,2017,2018,2018,2017,2019,2017,2019,
	    2019,2019,2018,2018,2018,2019,2019,2017,2018,2018,2017,2019,2019,2018,2017,2018,
	    2018,2017,2018,2018,2017,2017,2018,2019,2017,2017,2019,2018,2018,2018,2019,2018,
	    2017,2019,2018,2019
	};
	
	String[] aluno_nome = {
	    "Elias","Owen","Tamisha","Randall","Hertha","Sandi","Reynalda","Horacio",
	    "Debra","Deandrea","Karina","Rupert","Hal","Enriqueta","Lee","Estella",
	    "Emerald","Valda","Elton","Edgar","Teri","Ted","Marguerita","Tammera",
	    "Ciara","Dwain","Veronika","Toccara","Kraig","Clarisa","Phyliss","Lelia",
	    "Markus","Melba","Edmund","Tiny","Dell","Norris","Particia","Janie",
	    "Rolando","Garrett","Emmanuel","Bethel","Jasmin","Modesto","Neal","Jarrett",
	    "Mikaela","Ida"
	};
	
	ArrayList<Matricula> matriculas = new ArrayList<Matricula>();
	ArrayList<Aluno> alunos = new ArrayList<Aluno>();
	
	private Aluno getAluno(int ra) {
		for (Aluno aluno : alunos)
			if (aluno.getRa() == ra)
				return aluno;
		return null;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MainFrameMocks frame = new MainFrameMocks();
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
		comboDisciplina.addActionListener(evt -> {
			Disciplina selected = (Disciplina) comboDisciplina.getSelectedItem();
			System.out.println("Disciplina selecionada: " + selected);
			this.updateTable();
		});
		comboxPanel.add(comboDisciplina);

		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(18, 17, 66, 15);
		comboxPanel.add(lblCurso);
		
		comboCurso = new JComboBox();
		comboxPanel.add(comboCurso);
		comboCurso.setBounds(102, 12, 634, 24);
		comboCurso.addActionListener(evt -> {
			Curso curso = (Curso) comboCurso.getSelectedItem();
			System.out.println("Selecionado: " + curso + " " + curso.getCodigo());
			
			for (int i = 0; i < comboDisciplina.getItemCount(); i++)
				comboDisciplina.removeItemAt(i);
			for (int i = 0; i < disciplinas.length; i++) {
				String[] parse = disciplinas[i].split(" - ");
				comboDisciplina.addItem(new Disciplina(parse[0], parse[1], "prof" + i++, 1));
			}
		});
		for (int i = 0; i < cursos.length; i++)
			comboCurso.addItem(new Curso(i+1, cursos[i]));
		
		JButton button = new JButton("Adicionar");
		button.setBounds(155, 111, 114, 25);
		button.addActionListener(evt -> {
			if (table == null) return;
			System.out.println("Adicionar " + evt);
			int row = table.getSelectedRow();
			int column = table.getSelectedColumn();
			System.out.println(row + "," + column);
			int id = (int) table.getValueAt(row, 0);
			System.out.println(id);
		});
		contentPane.add(button);
		
		button = new JButton("Alterar");
		button.setBounds(335, 111, 114, 25);
		button.addActionListener(evt -> {
			if (table == null) return;
			System.out.println("Alterar " + evt);
		});
		contentPane.add(button);
		
		button = new JButton("Remover");
		button.setBounds(541, 113, 114, 25);
		button.addActionListener(evt -> {
			if (table == null) return;
			System.out.println("Remover " + evt);
		});
		contentPane.add(button);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 160, 758, 300);
		contentPane.add(scrollPane);
	}
	
	public MainFrameMocks() {
		this.initiateComponents();
		
		Random random = new Random();
		for (int i = 0; i < matricula_ra.length; i++) {
			int ra = matricula_ra[i];
			String cod_disciplina = matricula_cod_dis[i];
			int ano = matricula_ano[i];
			int semestre = random.nextInt(6) + 1;
			float nota = random.nextFloat() * 10;
			int faltas = random.nextInt(20);
			matriculas.add(new Matricula(ra, cod_disciplina, ano, semestre, nota, faltas));
		}
		
		for (int i = 0; i < aluno_nome.length; i++) {
			int periodo = random.nextInt(7) + 1;
			int cod_curso = random.nextInt(6) + 1;
			alunos.add(new Aluno(i, aluno_nome[i], periodo, cod_curso));
		}
	}

	private void updateTable() {
		if (scrollPane == null || matriculas.size() == 0 || alunos.size() == 0) return;
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	        	return false;
	        };
	    };
	    table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model.addColumn("Linha");
		model.addColumn("RA");
		model.addColumn("Nome");
		model.addColumn("Periodo");
		model.addColumn("Codigo curso");
		model.addColumn("Codigo disciplina");
		model.addColumn("Semestre");
		model.addColumn("Nota");
		model.addColumn("Faltas");
		model.addTableModelListener(evt -> {
			System.out.println("Table event " + evt);
			System.out.println(evt.getLastRow());
			System.out.println(evt.getSource());
		});
		
		Curso selectedCurso = (Curso) comboCurso.getSelectedItem();
		Disciplina selectedDisciplina = (Disciplina) comboDisciplina.getSelectedItem();
		
		int k = 0;
		for (int i = 0; i < matriculas.size(); i++) {
			Matricula matricula = matriculas.get(i);
			if (!selectedDisciplina.getCodigo().equals(matricula.getCod_disciplina()))
				continue;
			Aluno aluno = this.getAluno(matricula.getRa());
			if (aluno == null) continue;
			if (selectedDisciplina.getCod_curso() != aluno.getCod_curso()) continue;
			model.addRow(new Object[] {
				k++,
				aluno.getRa(),
				aluno.getNome(),
				aluno.getPeriodo(),
				aluno.getCod_curso(),
				matricula.getCod_disciplina(),
				matricula.getSemestre(),
				matricula.getNota(),
				matricula.getFaltas()
			});
		};
		scrollPane.setViewportView(table);
	}

}
