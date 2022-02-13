package chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

/**
 * Classe que gerencia o protocolo e o processamento das mensagens.
 * 
 * @author daniel e mara
 */
public class ProtocolController {

  private final MulticastSocket multicastSocket;
  private final DatagramSocket udpSocket;
  private final InetAddress group;
  private final Integer mport, uport;
  private final String nick;
  private final HashMap<String, InetAddress> onlineUsers;
  private final HashMap<String, Integer> portUsers;
  private final UIControl ui;

  public ProtocolController(Properties properties) throws IOException {
    mport = (Integer) properties.get("multicastPort");
    uport = (Integer) properties.get("udpPort");
    group = (InetAddress) properties.get("multicastIP");
    nick = (String) properties.get("nickname");
    ui = (UIControl) properties.get("UI");

    Utils.log("ProtocolController Propriedades");
    Utils.log("Multicast port: " + mport);
    Utils.log("UDP port: " + uport);
    Utils.log("Group: " + group.toString());
    Utils.log("Nick: " + nick);

    multicastSocket = new MulticastSocket(mport);
    udpSocket = new DatagramSocket(uport);

    onlineUsers = new HashMap<>();
    onlineUsers.put("Todos", group);

    portUsers = new HashMap<>();
  }

  public void send(String targetUser, String msg) throws IOException {
    Utils.log("---");
    Utils.log("Enviando mensagem: " + msg);
    Utils.log("Para: " + targetUser + " " + onlineUsers.get(targetUser).getHostAddress() + ":"
        + portUsers.getOrDefault(targetUser, -1));
    if (targetUser.equals("Todos")) {
      this.sendMessageGroup(new Message((byte) 3, nick, msg));
    } else {
      this.sendMessage(new Message((byte) 4, nick, msg), onlineUsers.get(targetUser), portUsers.get(targetUser));
    }
  }

  public void receiveUdpPacket() throws IOException {
    byte[] buffer = new byte[520];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

    Utils.log("Esperando por pacotes UDP.");
    this.udpSocket.receive(packet);
    this.processPacket(packet);
    Utils.log("Pacote UDP recebido.");
  }

  private void sendMessageGroup(Message msg) throws IOException {
    Utils.log("Enviando mensagem grupo: " + msg);
    byte[] buffer = msg.getBytes();
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, mport);
    multicastSocket.send(packet);
  }

  private void sendMessage(Message msg, InetAddress target, int port) throws IOException {
    byte[] buffer = msg.getBytes();
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, target, port);
    udpSocket.send(packet);
    Utils.log("Pacote enviado: " + msg);
  }

  public void join() throws IOException {
    Utils.log("Enviando mensagem de JOIN.");
    multicastSocket.joinGroup(group);
    this.sendMessageGroup(new Message((byte) 1, nick, Integer.toString(uport)));
  }

  public void leave() throws IOException {
    Utils.log("Enviando mensagem de LEAVE.");
    this.sendMessageGroup(new Message((byte) 5, nick, Integer.toString(uport)));
    this.close();
  }

  public void close() throws IOException {
    Utils.log("Fechando os sockets");
    if (udpSocket != null)
      udpSocket.close();
    if (multicastSocket != null)
      multicastSocket.close();
  }

  private void addUser(DatagramPacket p, Message m) {
    int port = Integer.parseInt(m.getMessage());
    onlineUsers.put(m.getSource(), p.getAddress());
    portUsers.put(m.getSource(), port);
  }

  public void processPacket(DatagramPacket p) throws IOException {
    byte[] buffer = p.getData();
    int i;
    for (i = 0; i < buffer.length && buffer[i] != 0; i++)
      ;
    Message message = new Message(Arrays.copyOfRange(buffer, 0, i));

    if (message.getSource().equals(nick))
      return;

    ui.update(message);
    Utils.log("Mensagem recebida: " + message);
    Utils.log("Tamanho da mensagem interna: " + message.getMessage().length());

    switch (message.getType()) {
      case 1:
        if (!message.getSource().equals(nick)) {
          this.addUser(p, message);
          this.sendMessage(new Message((byte) 2, nick, Integer.toString(uport)), p.getAddress(),
              Integer.parseInt(message.getMessage()));
        }
        break;
      case 2:
        this.addUser(p, message);
        break;
      case 5:
        Utils.log("Usuário " + message.getSource() + " deixou o grupo!");
        onlineUsers.remove(message.getSource());
        portUsers.remove(message.getSource());
        break;
    }
  }

  public void receiveMulticastPacket() throws IOException {
    byte[] buffer = new byte[520];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

    Utils.log("Esperando por pacotes multicast.");
    multicastSocket.receive(packet);
    this.processPacket(packet);
    Utils.log("Pacote multicast recebido.");
  }

}
