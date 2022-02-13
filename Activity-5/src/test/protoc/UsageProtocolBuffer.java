package test.protoc;

import java.io.IOException;
import java.util.Arrays;

import protoc.Models;

/**
 * Teste básico sobre o "Protocol Buffer".
 * 
 * @author daniel e mara
 */
public class UsageProtocolBuffer {
	public static void main(String[] args) throws IOException {
		byte[] data;
		Models.aluno.Builder alunoBuilder = Models.aluno.newBuilder();
		alunoBuilder.setRa(10);
		alunoBuilder.setNome("Daniel Augusto");
		alunoBuilder.setPeriodo(6);
		alunoBuilder.setCodCurso(1);
		
		Models.streams.Builder streamsBuilder = Models.streams.newBuilder();
		streamsBuilder.addAlunos(alunoBuilder);
		streamsBuilder.addHeader("RA");
		streamsBuilder.addHeader("Nome");
		streamsBuilder.addHeader("Periodo");
		streamsBuilder.addHeader("Codigo curso");
		
		Models.aluno aluno = alunoBuilder.build();
		System.out.println("--- aluno ---");
		System.out.println(aluno.getSerializedSize());
		System.out.println(aluno.toByteArray());
		System.out.println();
		
		Models.streams streams = streamsBuilder.build();
		System.out.println("--- stream ---");
		System.out.println(Arrays.toString(streams.toByteArray()));
		System.out.println(new String(streams.toByteArray()));
		
		Models.streams s2 = protoc.Models.streams.parseFrom(streams.toByteArray());
		s2.getHeaderList().forEach(head -> {
			System.out.println(head);
		});
		
	}
}
