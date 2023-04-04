package com.mycompany.chatserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    // Lista de usuários conectados na sala
    private static List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // Cria um servidor socket que escuta conexões na porta 9000
            ServerSocket serverSocket = new ServerSocket(9000);
            System.out.println("Chat server iniciado na porta 9000...");

            // Loop infinito para aceitar conexões de novos clientes
            while (true) {
                // Aguarda uma conexão
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado.");

                // Cria um thread para tratar as mensagens do cliente
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para tratar as mensagens do cliente
    private static void handleClient(Socket clientSocket) {
        try {
            // Cria streams para enviar e receber mensagens do cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Solicita que o cliente escolha um apelido
            out.println("Informe seu apelido:");
            String nickname = in.readLine().trim();
            out.println("Bem-vindo(a) à sala de bate-papo, " + nickname + "!");

            // Adiciona o cliente à lista de usuários conectados
            clients.add(out);

            // Envia uma mensagem de boas-vindas para os outros usuários
            broadcast(nickname + " entrou na sala.");

            // Loop infinito para receber mensagens do cliente e enviar para os outros usuários
            String input;
            while ((input = in.readLine()) != null) {
                if (input.equalsIgnoreCase("#QUIT")) {
                    // Remove o cliente da lista de usuários conectados e encerra a thread
                    clients.remove(out);
                    broadcast(nickname + " saiu da sala.");
                    break;
                } else if (input.equalsIgnoreCase("#USERS")) {
                    // Envia a lista de usuários conectados para o cliente
                    out.println("Usuários conectados:");
                    for (PrintWriter client : clients) {
                        out.println("- " + getNickname(client));
                    }
                } else {
                    // Envia a mensagem do cliente para os outros usuários
                    broadcast(nickname + ": " + input);
                }
            }

            // Fecha as streams e o socket do cliente
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar uma mensagem para todos os clientes conectados
    private static void broadcast(String message) {
        for (PrintWriter client : clients) {
            client.println(message);
        }
    }

    // Método para obter o apelido de um cliente
    private static String getNickname(PrintWriter client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) == client) {
                return "Usuário #" + (i + 1);
            }
        }
        return "Desconhecido";
    }
}