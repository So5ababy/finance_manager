import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class UserSettings {
    private final Map<String, User> users = new HashMap<>();
    private static final String fileNameUsers = "users.json";
    private Gson gson = new Gson();
    private final Memory memory = new Memory();

    public void saveUsers() {
        try (Writer writer = new FileWriter(fileNameUsers)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении пользователей: " + e.getMessage());
        }
    }

    public void loadUsers() {
        File file = new File(fileNameUsers);
        if (!file.exists()) return;
        try (Reader reader = new FileReader(fileNameUsers)) {
            Type type = new TypeToken<Map<String, User>>(){}.getType();
            Map<String, User> loadedUsers = gson.fromJson(reader, type);
            if (loadedUsers != null) {
                users.putAll(loadedUsers);
                for (User u : users.values()) {
                    Wallet wal = memory.load(u.getLogin());
                    if (wal != null ) {
                        u.setWallet(wal);
                    } else {
                        u.setWallet(new Wallet());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке пользователей: " + e.getMessage());
        }
    }


    private int inputInt(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.print("Некорректный ввод! Введите число: ");
                scanner.nextLine();
            }
        }
    }

    public User register(String login, String password) {
        if (users.containsKey(login)) {
            System.out.println("Такой пользователь уже существует");
            return null;
        }
        User newUser = new User(login, password);
        users.put(login, newUser);
        memory.save(newUser.getWallet(), login);
        saveUsers();
        return newUser;
    }

    public User login(String login, String password) {
        User user = users.get(login);
        if (user == null) {
            System.out.println("Пользователя не существует");
            return null;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("Пароль неверный");
            return null;
        }
        return user;
    }
}
