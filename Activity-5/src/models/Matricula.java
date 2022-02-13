package models;

/**
 * Classe de representação dos dados de uma matricula no banco.
 * 
 * @author daniel e mara
 */
public class Matricula {

	private int ra;
	private String cod_disciplina;
	private int ano;
	private int semestre;
	private float nota;
	private int faltas;

	public Matricula(int ra, String cod_disciplina, int ano, int semestre, float nota, int faltas) {
		this.ra = ra;
		this.cod_disciplina = cod_disciplina;
		this.ano = ano;
		this.semestre = semestre;
		this.nota = nota;
		this.faltas = faltas;
	}

	public int getRa() {
		return ra;
	}
	public String getCod_disciplina() {
		return cod_disciplina;
	}
	public int getAno() {
		return ano;
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
	
}
