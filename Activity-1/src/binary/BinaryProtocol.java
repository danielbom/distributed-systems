package binary;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por encapsular o mapeamento das operações
 * que podem ser executadas pelo cliente, além do mapeamento
 * de mensagens comuns de erros.
 * 
 * @author daniel
 *
 */
public class BinaryProtocol {
	private static final Map<Integer, String> OPERATIONS = 
		new HashMap<Integer, String>() {
			private static final long serialVersionUID = 1L;
		{
			put(1, "ADDFILE");
			put(2, "DELETE");
			put(3, "GETFILESLIST");
			put(4, "GETFILE");
		}};
	private static final Map<Integer, String> TYPE =
		new HashMap<Integer, String>() {
			private static final long serialVersionUID = 1L;
		{
			put(1, "REQUEST");
			put(2, "RESPONSE");
		}};
	
	private static final Map<String, Integer> OPERATIONSSTR =
		new HashMap<String, Integer>() {
			private static final long serialVersionUID = 1L;
		{
			put("ADDFILE", 1);
			put("DELETE", 2);
			put("GETFILELIST", 3);
			put("GETFILE", 4);
		}};
	private static final Map<String, Integer> TYPESTR =
		new HashMap<String, Integer>() {
			private static final long serialVersionUID = 1L;
		{
			put("REQUEST", 1);
			put("RESPONSE", 2);
		}};
		
	private static final HashMap<String, String> ERRORS =
		new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
		{
			put("404", "\tNão encontrado");
			put("500", "\tErro interno do servidor");
		}};
		
	public static String getOperation(Integer op) {
		return OPERATIONS.getOrDefault(op, "");
	}
	
	public static Integer getOperation(String op) {
		return OPERATIONSSTR.getOrDefault(op, 0);
	}
	
	public static String getType(Integer type) {
		return TYPE.getOrDefault(type, "");
	}
	
	public static Integer getType(String type) {
		return TYPESTR.getOrDefault(type, 0);
	}
	
	public static String getError(String key) {
		return ERRORS.get(key);
	}

}
