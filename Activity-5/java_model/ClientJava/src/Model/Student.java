package Model;

public class Student {
	
	private String name;
	private int studentcode;
	private int period;
	Course code;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCode(int studentcode) {
		this.studentcode = studentcode;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
	
	public void setCourse(Course code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	
	public int getCode() {
		return studentcode;
	}

	public int getPeriod() {
		return period;
	}
	
	public Course getCourse() {
		return code;
	}

}
