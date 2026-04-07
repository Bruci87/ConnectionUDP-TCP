package com.ufrn.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        // Porta sugerida nos slides
        DatagramSocket udpsocket = new DatagramSocket(6789);

        // Buffer grande para suportar até 60KB sem truncar
        byte[] buffer = new byte[65507];

        System.out.println("Servidor UDP pronto para os testes do Trabalho 03...");

        while (true) {
            DatagramPacket recebe = new DatagramPacket(buffer, buffer.length);

            // O servidor fica bloqueado aqui até chegar um pacote [cite: 198]
            udpsocket.receive(recebe);

            InetAddress ip = recebe.getAddress();
            int port = recebe.getPort();

            // Apenas avisa no console que chegou (sem converter 60KB para String)
            System.out.println("Pacote de " + recebe.getLength() + " bytes recebido de " + ip);

            // Envia o ACK (Confirmação) exigido no Trabalho 03
            byte[] ack = "OK".getBytes();
            DatagramPacket responde = new DatagramPacket(ack, ack.length, ip, port);
            udpsocket.send(responde);
        }
    }
}