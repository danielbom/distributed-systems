package core;

import java.util.ArrayList;

/**
 * Classe responsável por representar a estrutura básica
 * das respostas utilizada no front-end.
 * 
 * OBS: Pode ser substituída pela classe "Properties" em versões
 * futuras.
 * 
 * @author daniel e mara
 */
public class ServiceResult<T> {

	private ArrayList<String> header = new ArrayList<String>();
	private ArrayList<T> values = new ArrayList<T>();
	
	public ArrayList<String> getHeader() {
		return header;
	}
	public void setHeader(ArrayList<String> header) {
		this.header = header;
	}
	
	public ArrayList<T> getValues() {
		return values;
	}
	public void setValues(ArrayList<T> values) {
		this.values = values;
	}
	
}
