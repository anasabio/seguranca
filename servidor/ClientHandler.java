package servidor;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private SSLSocket socket;
    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("ironman", "jarvis123");
        users.put("admin", "root2025");
    }

    public ClientHandler(SSLSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            writer.write("Bem-vindo! Digite o usuário:
");
            writer.flush();
            String user = reader.readLine();

            writer.write("Digite a senha:
");
            writer.flush();
            String pass = reader.readLine();

            if (!Authenticator.authenticate(user, pass)) {
                writer.write("Autenticação falhou.
");
                writer.flush();
                Logger.log("Tentativa de login inválida para: " + user);
                socket.close();
                return;
            }

            Logger.log("Usuário autenticado: " + user);
            writer.write("Autenticado com sucesso! Digite mensagens (digite 'sair' para encerrar):
");
            writer.flush();

            String msg;
            while ((msg = reader.readLine()) != null) {
                if ("sair".equalsIgnoreCase(msg)) {
                    break;
                }
                Logger.log("Mensagem de " + user + ": " + msg);
                writer.write("Recebido: " + msg + "
");
                writer.flush();
            }
            Logger.log("Conexão encerrada com: " + user);
        } catch (IOException e) {
            Logger.log("Erro ao lidar com cliente: " + e.getMessage());
        }
    }
}
