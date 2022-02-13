package Model;

public class Disciplina {
	String code_d;
	String name;
	String professor;
	Course code;
	
	public void setCode_d (String code_d) {
		this.code_d = code_d;
	}
	
	public String getCode_d () {
		return code_d;
	}
	
	public void setProfessor (String professor) {
		this.professor = professor;
	}
	
	public String getProfessor () {
		return professor;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCode (Course code) {
		this.code = code;
	}
	
	public Course getCode () {
		return code;
	}

}
