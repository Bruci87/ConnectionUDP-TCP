package com.ufrn.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class TCPClient {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int porta = 6789;


        // Tamanho em KB
        int[] tamanhosKB = {1, 10, 20, 30, 40, 50, 60};


        System.out.println("Iniciando testes de vazão (Throughput)...");
        System.out.println("Tamanho (KB) | Tempo (ms) | Taxa (Mbps)");
        System.out.println("---------------------------------------");


        for (int kb : tamanhosKB) {
            int tamanhoBytes = kb * 1024;
            byte[] dados = new byte[tamanhoBytes];


            long inicio = System.nanoTime();


            try (Socket socket = new Socket(host, porta)) {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());


                out.writeInt(tamanhoBytes);
                out.write(dados);
                out.flush();


                // Espera o ACK do servidor
                in.readByte();


                long fim = System.nanoTime();




                double tempoSegundos = (fim - inicio) / 1_000_000_000.0;
                double tempoMilissegundos = (fim - inicio) / 1_000_000.0;




                double bits = tamanhoBytes * 8;
                double mbps = (bits / tempoSegundos) / 1_000_000.0;


                System.out.printf("%10d KB | %10.3f ms | %10.2f Mbps\n", kb, tempoMilissegundos, mbps);
            }
        }
    }
}

