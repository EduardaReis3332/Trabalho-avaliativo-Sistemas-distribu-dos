package com.mycompany.chatserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends JFrame implements ActionListener {
    // Componentes da interface gráfica
    private JTextField textField;
    private JTextArea textArea;
    private JButton button;
    private JLabel label;
    private JPanel panel;

    // Streams para enviar e receber mensagens do servidor
    private BufferedReader in;
    private PrintWriter out;

    // Construtor da classe
    public ChatClient() {
        super("Sala de Bate-papo");

        // Cria os componentes da interface gráfica
        textField = new JTextField();
        textArea = new JTextArea();
        textArea.setEditable(false);
        button = new JButton("Enviar");
        label = new JLabel("Mensagem:");
        panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Adiciona um ouvinte de eventos ao botão
        button.addActionListener(this);

        // Define o tamanho da janela e torna-a visível
        setSize(400, 300);
        setVisible(true);

        // Conecta ao servidor e inicia as streams
        try {
            Socket socket = new Socket("localhost", 9000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Solicita que o usuário informe seu apelido
            String nickname = JOptionPane.showInputDialog(this, "Informe seu apelido:");
            out.println(nickname);

            // Cria uma thread para receber as mensagens do servidor e exibi-las na tela
            new Thread(() -> {
                try {
                    String input;
                    while ((input = in.readLine()) != null) {
                        textArea.append(input + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método chamado quando o botão é clicado
    @Override
    public void actionPerformed(ActionEvent e) {
        // Envia a mensagem do usuário para o servidor
        out.println(textField.getText());
        textField.setText("");
    }

    // Método principal para iniciar o programa
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
