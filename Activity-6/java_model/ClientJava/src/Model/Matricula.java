package Model;

public class Matricula {

	Student ra;
	Disciplina code;
	int ano;
	int semestre;
	float nota;
	int faltas;

	public void setRA (Student ra) {
		this.ra = ra;
	}
	
	public Student getRA () {
		return ra;
	}
	
	public void setCode (Disciplina code) {
		this.code = code;
	}
	
	public Disciplina getCode () {
		return code;
	}
	
	public void setAno (int ano) {
		this.ano = ano;
	}
	
	public int getAno () {
		return ano;
	}
	
	public void setSemestre (int semestre) {
		this.semestre = semestre;
	}
	
	public int getSemestre () {
		return semestre;
	}
	
	public void setFaltas (int faltas) {
		this.faltas = faltas;
	}
	
	public int getFaltas () {
		return faltas;
	}
	
	public void setNota (float nota) {
		this.nota = nota;
	}
	
	public float getNotas () {
		return nota;
	}

}
