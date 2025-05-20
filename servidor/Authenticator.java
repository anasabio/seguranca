package servidor;

import java.util.Map;

public class Authenticator {
    private static final Map<String, String> validUsers = Map.of(
            "ironman", "jarvis123",
            "admin", "root2025"
    );

    public static boolean authenticate(String user, String password) {
        return validUsers.containsKey(user) && validUsers.get(user).equals(password);
    }
}
