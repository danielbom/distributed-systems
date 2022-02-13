package models;

/**
 * Classe de representação dos dados de um curso no banco.
 * 
 * @author daniel e mara
 */
public class Curso {

	private int codigo;
	private String nome;
	
	public Curso(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public String toString() {
		return this.nome.toUpperCase();
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}
}
