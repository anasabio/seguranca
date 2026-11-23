package servidor;

import java.util.Map;

public class Authenticator {
    private static final Map<String, String> validUsers = Map.of();

    public static boolean authenticate(String user, String password) {
        return validUsers.containsKey(user) && validUsers.get(user).equals(password);
    }
}
