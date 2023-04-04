package com.mycompany.calculadoraremota;
    
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class ServidorCalculadora {
    public static void main(String[] args) throws IOException {
  
        // Criando um socket para escutar na porta 1234
        DatagramSocket ds = new DatagramSocket(1234);
  
        byte[] dados = null;
  
        // Inicializando-os inicialmente com null
        DatagramPacket DpReceive = null;
        DatagramPacket DpSend = null;
  
        while (true) {
            dados = new byte[1000];
  
            // Criando um DatagramPacket para receber os dados.
            DpReceive = new DatagramPacket(dados, dados.length);
  
            // Recebendo os dados no buffer de bytes.
            ds.receive(DpReceive);
  
            String equacao = new String(dados, 0, dados.length);
  
            // Usando o método trim() para remover espaços extras.
            equacao = equacao.trim();
  
            System.out.println("Equação Recebida: "+ equacao);
  
            // Saia do servidor se o cliente enviar "sair"
            if (equacao.equals("sair")) {
                System.out.println("Cliente enviou 'sair', saindo do servidor...");
                System.out.println("Saiu do servidor.");
                // Sai do programa aqui mesmo sem verificar mais
                break;
            }
 
            double resultado;
            // Use StringTokenizer para quebrar a equação em operando e operação
            StringTokenizer st = new StringTokenizer(equacao);
            
            double numero1 = Double.parseDouble(st.nextToken());
            String operacao = st.nextToken();
            double numero2 = Double.parseDouble(st.nextToken());
  
            // Execute a operação necessária
            if (operacao.equals("+"))
                resultado = numero1 + numero2;
  
            else if (operacao.equals("-"))
                resultado = numero1 - numero2;
            
            else if (operacao.equals("*"))
                resultado = numero1 * numero2;
  
            else
                resultado = numero1 / numero2;
  
            System.out.println("Enviando o resultado..");
            
            // Convertendo a valor de Double para String
            String resp = Double.toString(resultado);
  
            // Limpando o buffer após cada resultado, armazenando a String no array de bytes
            dados = resp.getBytes();
  
            // Obtendo a porta do cliente
            int port = DpReceive.getPort();
  
            // Envia o resultado de volta para o cliente, atraves do endereço IP da máquina local e a porta do cliente
            DpSend = new DatagramPacket(dados, dados.length, InetAddress.getLocalHost(),port);
            ds.send(DpSend);
        }
    }
}
