package chat;

import java.awt.BorderLayout;

/*
 * ChatGUI.java
 *
 * Created on 29 de Setembro de 2005, 10:30
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Interface Gráficas dos Usuários. Chat usando sockets TCP
 *
 * @author daniel e mara
 */
public class ChatGUI extends javax.swing.JFrame implements UIControl {

  private static final long serialVersionUID = 1L;

  Color orange = new Color(255, 165, 2, 100);
  Color cyan = new Color(30, 144, 255);
  Color red = new Color(255, 71, 159);
  Color green = new Color(15, 185, 177);
  Color gray = new Color(206, 214, 224);

  Style blackStyle, cyanStyle, redStyle, orangeStyle, turquoiseStyle;
  StyledDocument styledDoc;

  private DefaultListModel modelList;
  private JTextPane areaMensagem;
  private JGradientButton btnConectar, btnDesconectar, btnEnviar;
  private JLabel lblApelido, lblIP, lblPorta, lblPortaUDP;
  private JList<String> lstLista;
  private JPanel pnlBotoes, pnlConfiguracao, pnlMensagem, pnlOpcoes;
  private JScrollPane scrollArea;
  private JTextField txtApelido, txtIP, txtMensagem, txtPorta, txtPortaUDP;

  private Listener listener;
  private ProtocolController protoController;

  public ChatGUI() {
    initComponents();

    styledDoc = areaMensagem.getStyledDocument();

    turquoiseStyle = areaMensagem.addStyle("torquoseStyle", null);
    blackStyle = areaMensagem.addStyle("blackStyle", null);
    cyanStyle = areaMensagem.addStyle("blueStyle", null);
    redStyle = areaMensagem.addStyle("greenStyle", null);
    StyleConstants.setForeground(blackStyle, Color.black);
    StyleConstants.setForeground(cyanStyle, cyan);
    StyleConstants.setForeground(redStyle, red);
    StyleConstants.setForeground(turquoiseStyle, green);

    modelList = new DefaultListModel();
    lstLista.setModel(modelList);

    btnDesconectar.setEnabled(false);
  }

  private void initComponents() {
    GridBagConstraints gridBagConstraints;
    txtMensagem = new JTextField();
    scrollArea = new JScrollPane();
    areaMensagem = new JTextPane();

    pnlConfiguracao = new JPanel();
    pnlMensagem = new JPanel();
    pnlOpcoes = new JPanel();
    pnlBotoes = new JPanel();

    lblIP = new JLabel();
    lblPorta = new JLabel();
    lblApelido = new JLabel();
    lblPortaUDP = new JLabel();

    txtIP = new JTextField();
    txtPorta = new JTextField();
    txtApelido = new JTextField();
    txtPortaUDP = new JTextField();

    btnDesconectar = new JGradientButton("Desconectar");
    btnConectar = new JGradientButton("Conectar");
    btnEnviar = new JGradientButton("Enviar");

    lstLista = new JList<String>();

    pnlConfiguracao.setBackground(gray);
    pnlMensagem.setBackground(gray);
    pnlOpcoes.setBackground(gray);
    pnlBotoes.setBackground(gray);
    this.setBackground(gray);

    Color button = green;
    btnConectar.setBackground(button);
    btnDesconectar.setBackground(button);
    btnEnviar.setBackground(button);

    getContentPane().setLayout(new BorderLayout(3, 1));

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Private Mensager");
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    pnlMensagem.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));

    txtMensagem.setColumns(30);
    txtMensagem.setMinimumSize(new Dimension(50, 50));
    txtMensagem
        .setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 0, 0, 0), new EmptyBorder(10, 10, 10, 10)));

    txtMensagem.addActionListener(evt -> btnEnviarActionPerformed(evt));
    pnlMensagem.add(txtMensagem);

    btnEnviar.setMnemonic('E');
    btnEnviar.setText("Enviar");
    btnEnviar.addActionListener(evt -> btnEnviarActionPerformed(evt));

    pnlMensagem.add(btnEnviar);

    getContentPane().add(pnlMensagem, BorderLayout.SOUTH);

    areaMensagem.setPreferredSize(new Dimension(300, 300));
    scrollArea.setViewportView(areaMensagem);

    getContentPane().add(scrollArea, BorderLayout.CENTER);

    pnlConfiguracao.setLayout(new BorderLayout());

    pnlConfiguracao
        .setBorder(new TitledBorder(null, " Configurações ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
    pnlOpcoes.setLayout(new java.awt.GridBagLayout());

    pnlOpcoes.setBorder(BorderFactory.createLineBorder(Color.black));

    // Labels
    lblApelido.setText("Apelido: ");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    pnlOpcoes.add(lblApelido, gridBagConstraints);

    pnlOpcoes.setMinimumSize(new Dimension(250, 50));
    pnlOpcoes.setPreferredSize(new Dimension(250, 80));
    lblIP.setText("ID Grupo: ");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    pnlOpcoes.add(lblIP, gridBagConstraints);

    lblPorta.setText("Porta: ");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    pnlOpcoes.add(lblPorta, gridBagConstraints);

    lblPortaUDP.setText("Porta UDP: ");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    pnlOpcoes.add(lblPortaUDP, gridBagConstraints);

    // Input
    txtApelido.setColumns(10);
    txtApelido.setText("");
    txtApelido.setMinimumSize(new Dimension(100, 19));
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    pnlOpcoes.add(txtApelido, gridBagConstraints);
    txtApelido.addActionListener(evt -> btnConectarActionPerformed(evt));

    txtIP.setColumns(10);
    txtIP.setText("225.1.2.3");
    txtIP.setMinimumSize(new Dimension(100, 19));
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    pnlOpcoes.add(txtIP, gridBagConstraints);
    txtIP.addActionListener(evt -> btnConectarActionPerformed(evt));

    txtPorta.setColumns(10);
    txtPorta.setText("6789");
    txtPorta.setMinimumSize(new Dimension(100, 19));
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    pnlOpcoes.add(txtPorta, gridBagConstraints);
    txtPorta.addActionListener(evt -> btnConectarActionPerformed(evt));

    txtPortaUDP.setColumns(10);
    txtPortaUDP.setText("4569");
    txtPortaUDP.setMinimumSize(new Dimension(100, 19));
    txtPortaUDP.setMinimumSize(new Dimension(100, 19));
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    pnlOpcoes.add(txtPortaUDP, gridBagConstraints);
    txtPortaUDP.addActionListener(evt -> btnConectarActionPerformed(evt));

    pnlConfiguracao.add(pnlOpcoes, BorderLayout.CENTER);

    pnlBotoes.setLayout(new GridLayout(2, 1, 0, 5));

    btnConectar.setMnemonic('n');
    btnConectar.setText("Entrar");
    btnConectar.addActionListener(evt -> btnConectarActionPerformed(evt));

    pnlBotoes.add(btnConectar);

    btnDesconectar.setMnemonic('S');
    btnDesconectar.setText("Sair");
    btnDesconectar.addActionListener(evt -> btnDesconectarActionPerformed(evt));

    pnlBotoes.add(btnDesconectar);
    pnlBotoes.setBorder(new EmptyBorder(5, 5, 5, 5));

    pnlConfiguracao.add(pnlBotoes, BorderLayout.EAST);

    getContentPane().add(pnlConfiguracao, BorderLayout.NORTH);

    lstLista.setBorder(new javax.swing.border.EtchedBorder());
    lstLista.setFont(new Font("Dialog", 0, 10));
    lstLista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    lstLista.setFixedCellWidth(100);
    lstLista.setPreferredSize(new Dimension(150, 0));
    getContentPane().add(lstLista, BorderLayout.EAST);

    pack();
  }

  public void addNickname(String apelido) {
    modelList.addElement(apelido);
  }

  public void rmNickname(String apelido) {
    modelList.removeElement(apelido);
  }

  // ********************************************************************* //
  // Métodos úteis //
  // ********************************************************************* //
  private boolean isValidNickname() {
    return !this.getApelido().matches("Todos|^$");
  }

  public String getApelido() {
    return txtApelido.getText();
  }

  @Override
  public void update(Message m) {
    switch (m.getType()) {
      case 1:
        this.addNickname(m.getSource());
        break;
      case 2:
        this.addNickname(m.getSource());
        break;
      case 3:
      case 4:
        this.writeMessage(m.getSource(), m.getMessage());
        break;
      case 5:
        this.rmNickname(m.getSource());
        break;
    }

  }

  // ********************************************************************* //
  // Eventos da GUI //
  // ********************************************************************* //
  private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {
    Utils.log("Chamando função do botão conectar.");

    if (!this.isValidNickname()) {
      Utils.log("ERRO");
      JOptionPane.showMessageDialog(this, "Apelido inválido!", "Erro apelido inválido", JOptionPane.ERROR_MESSAGE);
      return;
    }

    int port = Integer.parseInt(txtPortaUDP.getText());
    if (!Utils.available(port)) {
      Utils.log("ERRO");
      JOptionPane.showMessageDialog(this, "Porta UDP já em uso!", "Erro apelido inválido", JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      Utils.log("DEBUG");
      this.addNickname("Todos");
      lstLista.setSelectedIndex(0);

      // desabilita caixas de texto na GUI
      btnConectar.setEnabled(false); // JOIN
      txtApelido.setEnabled(false);
      txtIP.setEnabled(false);
      txtPorta.setEnabled(false);
      txtPortaUDP.setEnabled(false);
      btnDesconectar.setEnabled(true); // LEAVE

      // implementação do entrar no grupo
      Properties prop = new Properties();
      prop.put("multicastIP", InetAddress.getByName(txtIP.getText()));
      prop.put("multicastPort", Integer.parseInt(txtPorta.getText()));
      prop.put("udpPort", port);
      prop.put("nickname", this.getApelido());
      prop.put("UI", this);

      protoController = new ProtocolController(prop);
      listener = new Listener(protoController);

      // juntar-se ao grupo e inicializar o recebimento de mensagens
      protoController.join();
      listener.start();

    } catch (UnknownHostException uhe) {
      JOptionPane.showMessageDialog(this, uhe.getMessage(), "Erro ao entrar no grupo.", JOptionPane.ERROR_MESSAGE);
    } catch (IOException ioe) {
      JOptionPane.showMessageDialog(this, ioe.getMessage(), "Erro de I/O.", JOptionPane.ERROR_MESSAGE);
    }

  }

  private void btnDesconectarActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      // Saindo do grupo
      if (protoController != null) {
        protoController.leave();
        protoController.close();
        protoController = null;
      }
    } catch (IOException ioe) {
      JOptionPane.showMessageDialog(this, ioe.getMessage(), "Erro ao sair do grupo.", JOptionPane.ERROR_MESSAGE);
    }

    // Resetando configurações iniciais da GUI
    btnDesconectar.setEnabled(false);
    btnConectar.setEnabled(true);
    txtPortaUDP.setEnabled(true);
    txtApelido.setEnabled(true);
    txtPorta.setEnabled(true);
    txtIP.setEnabled(true);

    modelList.clear();
  }

  // Enviar (JButton)
  private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {
    String msg = txtMensagem.getText();
    Utils.log("Chamando função do botão enviar.");
    Utils.log("Mensagem: " + msg);

    if ((msg.trim().length() > 0) && (lstLista.getSelectedIndex() != -1)) {
      // Obtem o apelido do destinatario
      String nickTarget = (String) lstLista.getSelectedValue();

      // Enviando mensagem para o grupo ou individuo
      try {
        protoController.send(nickTarget, txtMensagem.getText());
      } catch (IOException e) {
        Utils.log("Erro ao enviar a messagem.");
      }

      // Atualizando a interface
      this.writeLocalMessage(this.getApelido(), msg);
      txtMensagem.setText("");
      txtMensagem.requestFocus();
    }

  }

  // Fechando a aplicação
  private void formWindowClosing(java.awt.event.WindowEvent evt) {
    this.btnDesconectarActionPerformed(null);
  }

  /**
   * Escreve uma mensagem remota na area de mensagem
   * 
   * @param id       apelido
   * @param mensagem mensagem a ser enviada
   */
  public void writeMessage(String id, String mensagem) {
    try {
      mensagem = Emoji.format(mensagem);

      styledDoc.insertString(styledDoc.getLength(), "<" + id + "> ", redStyle);
      styledDoc.insertString(styledDoc.getLength(), Emoji.format(mensagem) + "  \n", blackStyle);

      areaMensagem.setCaretPosition(areaMensagem.getText().length());
    } catch (BadLocationException ble) {
      System.err.println("Erro ao escrever mensagem na UI" + ble);
    }
  }

  /**
   * Escreve uma mensagem local na area de mensagem
   * 
   * @param id       apelido
   * @param mensagem mensagem a ser enviada
   */
  public void writeLocalMessage(String id, String mensagem) {
    try {
      styledDoc.insertString(styledDoc.getLength(), "<" + id + "> ", cyanStyle);
      styledDoc.insertString(styledDoc.getLength(), Emoji.format(mensagem) + "  \n", blackStyle);

      areaMensagem.setCaretPosition(areaMensagem.getText().length());
    } catch (BadLocationException ble) {
      System.err.println("Erro ao escrever mensagem na UI" + ble);
    }
  }

  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(() -> new ChatGUI().setVisible(true));
  }

}
