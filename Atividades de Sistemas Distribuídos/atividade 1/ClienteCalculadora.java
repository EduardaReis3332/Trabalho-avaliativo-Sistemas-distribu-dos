package com.mycompany.calculadoraremota;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteCalculadora {
    public static void main(String args[])throws IOException {
  
        // Criando um objeto da classe Scanner para ler a entrada do usuário
        Scanner entrada = new Scanner(System.in);
  
        // Cria um objeto da classe DatagramSocket para enviar e receber datagramas UDP.
        DatagramSocket ds = new DatagramSocket();
        
        // Obtém o endereço IP local do host
        InetAddress ip = InetAddress.getLocalHost();
        
        //Declara um array de bytes para armazenar os dados a serem enviados e recebidos.
        byte dados[] = null;
  
        // loop enquanto o usuário não digita "sair"
        while (true) {
            System.out.println("Insira a equação no formato:");
            System.out.println("'<numero1> <sinal> <numero2>'");
  
            // Aguardando entrada inserida no scanner armazenando na variável "equação"
            String equacao = entrada.nextLine();
            
            // Cria um novo array de bytes para armazenar os dados da entrada do usuário.
            dados = new byte[1000];
            
            System.out.println("Enviando a operação pro servidor...");
            
            // Convertendo a entrada String em um array de byte
            dados = equacao.getBytes();
  
            // Criando o datagramPacket para enviar os dados, com o endereço IP do servidor e a porta do servidor a ser usado para enviar os dados..
            DatagramPacket DpSend = new DatagramPacket(dados, dados.length, ip, 1234);
  
            // Chamando a chamada de envio para realmente enviar os dados.
            ds.send(DpSend);
  
            // Interrompe o loop se o usuário digitar "sair" usando a palavra-chave break
            if (equacao.equals("sair"))
                break;
            
            // Cria um novo array de bytes para armazenar os dados recebidos do servidor.
            dados = new byte[1000];
            System.out.println("Resultado recebido do servidor.");
  
            // Cria um novo objeto da classe DatagramPacket para receber os dados do servidor
            DatagramPacket DpReceive = new DatagramPacket(dados, dados.length);
            ds.receive(DpReceive);
  
            // Comando de impressão e exibição
            System.out.println("O resultado da operação "+equacao+" é = " +new String(dados, 0, dados.length));
            System.out.println("--------------------------------------------------------");
        }
    }
}
