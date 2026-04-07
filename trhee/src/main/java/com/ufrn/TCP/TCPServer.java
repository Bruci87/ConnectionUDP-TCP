package com.ufrn.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6789);
        System.out.println("Servidor de métricas iniciado na porta 6789...");


        while (true) {
            try (Socket conexao = serverSocket.accept()) {
                DataInputStream in = new DataInputStream(conexao.getInputStream());
                DataOutputStream out = new DataOutputStream(conexao.getOutputStream());


                int tamanhoPacote = in.readInt();
                byte[] buffer = new byte[tamanhoPacote];


                in.readFully(buffer);


                // Envia um byte de confirmação (ACK)
                out.writeByte(1);
                out.flush();
            } catch (Exception e) {
                System.out.println("Conexão encerrada.");
            }
        }
    }
}

