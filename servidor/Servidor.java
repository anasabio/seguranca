package servidor;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private static final int PORT = 8443;
    private static final int MAX_CLIENTS = 10;

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "servidor.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "senha123");

        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        Logger.log("Iniciando o servidor...");

        try {
            SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(PORT);
            Logger.log("Servidor escutando na porta " + PORT);

            while (true) {
                pool.execute(new ClientHandler(serverSocket.accept()));
            }
        } catch (IOException e) {
            Logger.log("Erro no servidor: " + e.getMessage());
        }
    }
}
