package models;

/**
 * Classe de representação dos dados de um aluno no banco.
 * 
 * @author daniel e mara
 */
public class Aluno {
	private int ra;
	private String nome;
	private int periodo;
	private int cod_curso;
	
	public Aluno(int ra, String nome, int periodo, int cod_curso) {
		this.ra = ra;
		this.nome = nome;
		this.periodo = periodo;
		this.cod_curso = cod_curso;
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
	
	public String toString() {
		return ra + ": " + nome;
	}
	
}
