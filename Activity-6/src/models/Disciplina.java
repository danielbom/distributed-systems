package models;

/**
 * Classe de representação dos dados de uma disciplina no banco.
 * 
 * @author daniel e mara
 */
public class Disciplina {
	private String codigo;
	private String nome;
	private String professor;
	private int cod_curso;
	
	public Disciplina(String codigo, String nome, String professor, int cod_curso) {
		this.codigo = codigo;
		this.nome = nome;
		this.professor = professor;
		this.cod_curso = cod_curso;
	}

	public String getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public String getProfessor() {
		return professor;
	}
	public int getCod_curso() {
		return cod_curso;
	}

	public String toString() {
		return codigo + " - " + nome;
	}
	
}
