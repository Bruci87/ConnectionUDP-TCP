package com.ufrn.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        // Porta definida para o trabalho
        int porta = 6789;
        DatagramSocket udpsocket = new DatagramSocket(porta);

        // Buffer de 64KB (limite máximo do UDP para evitar truncamento)
        byte[] buffer = new byte[65507];

        System.out.println("--- SERVIDOR UDP INICIADO (Porta: " + porta + ") ---");
        System.out.println("Aguardando pacotes do Cliente...");

        while (true) {
            // Prepara o pacote para receber os dados
            DatagramPacket recebe = new DatagramPacket(buffer, buffer.length);

            // O servidor trava aqui até receber algo
            udpsocket.receive(recebe);

            InetAddress ipCliente = recebe.getAddress();
            int portaCliente = recebe.getPort();
            int tamanhoRecebido = recebe.getLength();

            System.out.println("Recebido: " + tamanhoRecebido + " bytes de " + ipCliente);

            // Envia o ACK (Confirmação) de volta para o cliente
            byte[] ack = "OK".getBytes();
            DatagramPacket responde = new DatagramPacket(ack, ack.length, ipCliente, portaCliente);
            udpsocket.send(responde);
        }
    }
}