package models;

/**
 * Classe de valores personalizados da relação entre matricula e aluno
 * do banco de dados utilizado pelo front-end.
 * 
 * TODO: Encontrar uma maneira de evitar este tipo de construção, por
 * exemplo, utilizar Properties.
 * 
 * @author daniel e mara
 */
public class AlunoView {
	
	private int ra;
	private String nome;
	private int periodo;
	private int cod_curso;
	private String cod_disciplina;
	private int semestre;
	private float nota;
	private int faltas;
	private int ano;

	public AlunoView(
		int ra,
		String nome,
		int periodo,
		int cod_curso,
		String cod_disciplina,
		int semestre,
		float nota,
		int faltas,
		int ano
	) {
		this.ra = ra;
		this.nome = nome;
		this.periodo = periodo;
		this.cod_curso = cod_curso;
		this.cod_disciplina = cod_disciplina;
		this.semestre = semestre;
		this.nota = nota;
		this.faltas = faltas;
		this.ano = ano;
	}

	
	public int getRa() {
		return ra;
	}
	public String getNome() {
		return nome;
	}
	public int getPeriodo() {
		return periodo;
	}
	public int getCod_curso() {
		return cod_curso;
	}
	public String getCod_disciplina() {
		return cod_disciplina;
	}
	public int getSemestre() {
		return semestre;
	}
	public float getNota() {
		return nota;
	}
	public int getFaltas() {
		return faltas;
	}
	public int getAno() {
		return ano;
	}

	public Object[] getObjectArray() {
		return new Object[] {
			ra,
			nome,
			periodo,
			cod_curso,
			cod_disciplina,
			semestre,
			nota,
			faltas,
			ano
		};
	}
}
