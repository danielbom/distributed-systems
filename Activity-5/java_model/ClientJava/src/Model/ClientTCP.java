package Model;

/**
 * @author a1711156 
 * 
 *
 * 

/**
 from config import *
from core.packet import Packet


def get_input():
    resources = ["alunos", "curso", "matricula", "disciplinas"]
    complete = False
    while not complete:
        try:
            verb = int(input("Operação [1-4]: "))
            resource = resources[int(input("Recurso [0-3]: "))]
            params = input("Opções: ")
            complete = True
        except KeyboardInterrupt:
            break
        except:
            pass
    print()
    return Packet(1, verb, resource, params)

print('Para sair use CTRL+X\n')
msg = get_input()
while msg != '\x18':
    tcp.send(msg.getBytes())
    msg = get_input()
tcp.close()
 * operações cadastrar - remover - atualizar - consultar
 * notas - faltas - alunos
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCP {
	
	public static void main (String args[]) {
		Socket clientSocket = null; // socket do cliente // socket de comunicação do cliente
	    Scanner reader = new Scanner(System.in); // ler mensagens via teclado
            
        try {
            /* Endereço e porta do servidor */
            int serverPort = 6666;   
            InetAddress serverAddr = InetAddress.getByName("127.0.0.1"); // ipv4 ipv6
            
            /* conecta com o servidor */  
            clientSocket = new Socket(serverAddr, serverPort);  // instanciar o socket OPERAÇÃO CONNECT 
            
            /* cria objetos de leitura e escrita  STREAMS */
            DataInputStream in = new DataInputStream( clientSocket.getInputStream()); // input
            DataOutputStream out = new DataOutputStream( clientSocket.getOutputStream()); // output
        
            /* protocolo de comunicação */
            String buffer = "";
            while (true) {
                System.out.print("Mensagem: ");
                buffer = reader.nextLine();
            
                out.writeUTF(buffer);      	// envia a mensagem para o servidor
	
                if (buffer.equals("SAIR")) break;
                
                buffer = in.readUTF();      // aguarda resposta do servidor
                System.out.println("Server disse: " + buffer);
            } 
	    } catch (UnknownHostException ue){
	    	System.out.println("Socket:" + ue.getMessage());
        } catch (EOFException eofe){
        	System.out.println("EOF:" + eofe.getMessage());
        } catch (IOException ioe){
        	System.out.println("IO:" + ioe.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ioe) {
                System.out.println("IO: " + ioe);;
            }
        }
     }

}
