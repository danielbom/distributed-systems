package main;

import java.awt.EventQueue;

import view.MainFrame;

/**
 * Classe de inicialização da janela de acesso ao sistema simples
 * de cursos, disciplinas e notas.
 * 
 * @author daniel e mara
 */
public class FrameInit {
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 5005;
		
		EventQueue.invokeLater(() -> {
			try {
				MainFrame frame = new MainFrame(host, port);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
