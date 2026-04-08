package com.ufrn.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Uso correto: UDPClient <IP_do_Servidor> <Porta>");
            System.exit(0);
        }

        String host = args[0];
        int porta = Integer.parseInt(args[1]);
        InetAddress server = InetAddress.getByName(host);

        DatagramSocket udpclient = new DatagramSocket();
        // Timeout de 2 segundos
        udpclient.setSoTimeout(2000);

        int[] tamanhosKB = {1, 10, 20, 30, 40, 50, 60};

        System.out.println("--- INICIANDO TESTES UDP (TRABALHO 03) ---");
        System.out.println("Tamanho\t\tTempo (ms)\tVazão (Mbps)");

        for (int kb : tamanhosKB) {
            int tamanhoBytes = kb * 1024;
            byte[] dados = new byte[tamanhoBytes];

            DatagramPacket pedido = new DatagramPacket(dados, dados.length, server, porta);

            try {
                long inicio = System.nanoTime();

                // Envia o pacote
                udpclient.send(pedido);

                // Espera o ACK do Servidor
                byte[] bufferACK = new byte[1024]; // Buffer para a resposta
                DatagramPacket resposta = new DatagramPacket(bufferACK, bufferACK.length);
                udpclient.receive(resposta);

                long fim = System.nanoTime();

                double tempoMs = (fim - inicio) / 1_000_000.0;
                double mbps = (tamanhoBytes * 8.0) / (tempoMs / 1000.0) / 1_000_000.0;

                System.out.printf("%d KB\t\t%.3f ms\t\t%.3f Mbps\n", kb, tempoMs, mbps);

            } catch (java.net.SocketTimeoutException e) {
                System.out.println(kb + " KB\t\tFALHA (Timeout/Pacote perdido)");
            }
        }

        udpclient.close();
        System.out.println("--- TESTES CONCLUÍDOS ---");
    }
}