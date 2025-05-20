package cliente;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "servidor.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "senha123");

        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 8443);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            new Thread(() -> {
                String msg;
                try {
                    while ((msg = reader.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("Conexão encerrada.");
                }
            }).start();

            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                writer.write(input + "
");
                writer.flush();
                if ("sair".equalsIgnoreCase(input)) break;
            }

            socket.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
