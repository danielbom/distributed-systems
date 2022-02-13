package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Properties;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import core.Service;
import core.Utils;

/**
 * Formulário para adicionar e remoção de notas utilizando 'JDialog'.
 * 
 * @author daniel e mara
 */
public class FormNota extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JTextField textNome;
	private JTextField textDisciplina;
	private JTextField textAno;
	private JTextField textSemestre;
	private JTextField textNota;
	private JTextField textFaltas;

	private Service service;
	private Consumer<Properties> saveConsumer;

	public FormNota(JFrame frame, String nome, String disciplina, Integer ra,
			String cod_disciplina, String title, Consumer<Properties> consumer) {
		this(frame, nome, disciplina, ra, null, null, null, null,
				cod_disciplina, title, consumer);
	}
	
	public FormNota(JFrame frame, String nome, String disciplina, Integer ra,
			Integer ano, Integer semestre, Float nota, Integer faltas,
			String cod_disciplina, String title, Consumer<Properties> consumer) {
		super(frame);
		this.service = service;
		this.saveConsumer = consumer;

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle(title);
		setBounds(100, 100, 700, 234);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setBounds(12, 26, 66, 15);
		contentPanel.add(lblNome);
		
		JLabel lblDisciplina = new JLabel("Disciplina:");
		lblDisciplina.setBounds(12, 53, 84, 15);
		contentPanel.add(lblDisciplina);
		{
			textNome = new JTextField();
			textNome.setText(nome);
			textNome.setEditable(false);
			textNome.setBounds(96, 24, 592, 19);
			contentPanel.add(textNome);
			textNome.setColumns(10);
		}
		{
			textDisciplina = new JTextField();
			textDisciplina.setText(disciplina);
			textDisciplina.setEditable(false);
			textDisciplina.setBounds(96, 51, 592, 19);
			contentPanel.add(textDisciplina);
			textDisciplina.setColumns(10);
		}
		{
			JLabel lblAno = new JLabel("Ano:");
			lblAno.setBounds(12, 116, 66, 15);
			contentPanel.add(lblAno);
		}
		{
			JLabel lblSemestre = new JLabel("Semestre:");
			lblSemestre.setBounds(169, 116, 84, 15);
			contentPanel.add(lblSemestre);
		}
		{
			JLabel lblNota = new JLabel("Nota:");
			lblNota.setBounds(373, 116, 84, 15);
			contentPanel.add(lblNota);
		}
		{
			JLabel lblFaltas = new JLabel("Faltas:");
			lblFaltas.setBounds(525, 116, 84, 15);
			contentPanel.add(lblFaltas);
		}
		{
			textAno = new JTextField();
			textAno.setColumns(10);
			textAno.setBounds(54, 114, 87, 19);
			if (ano != null)
				textAno.setText(String.valueOf(ano));
			contentPanel.add(textAno);
		}
		{
			textSemestre = new JTextField();
			textSemestre.setColumns(10);
			textSemestre.setBounds(243, 114, 87, 19);
			if (semestre != null)
				textSemestre.setText(String.valueOf(semestre));
			contentPanel.add(textSemestre);
		}
		{
			textNota = new JTextField();
			textNota.setColumns(10);
			textNota.setBounds(420, 114, 87, 19);
			if (nota != null)
				textNota.setText(String.valueOf(nota));
			contentPanel.add(textNota);
		}
		{
			textFaltas = new JTextField();
			textFaltas.setColumns(10);
			textFaltas.setBounds(581, 114, 87, 19);
			if (faltas != null)
				textFaltas.setText(String.valueOf(faltas));
			contentPanel.add(textFaltas);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(evt -> this.listenerCancelButton());
				buttonPane.add(cancelButton);
			}
			{
				JButton saveButton = new JButton("Salvar");
				saveButton.setActionCommand("OK");
				saveButton.addActionListener(evt -> this.listenerSaveButton());
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
		}
	}

	private void listenerSaveButton() {
		try {
			Properties props = new Properties();
			props.put("ano", textAno.getText());
			props.put("semestre", textSemestre.getText());
			props.put("nota", textNota.getText());
			props.put("faltas", textFaltas.getText());
			if (saveConsumer != null)
				saveConsumer.accept(props);
			
			Utils.log("Chamando dispose");
			this.dispose();
		} catch(Exception e) {
		}
	}

	private void listenerCancelButton() {
		this.dispose();
	}

	public static void main(String[] args) {
		try {
			FormNota dialog = new FormNota(null, "Aluno", "Disciplina",
					1, 200, 200, (float) 200, 200, "O", "Test", null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
