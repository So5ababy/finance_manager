import java.io.*;
import com.google.gson.Gson;
import java.io.*;
import java.util.*;

public class Memory {
    private Gson gson = new Gson();

    public void save(Wallet wallet, String login) {
        String filename = login + "_wallet.json";
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(wallet, writer);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    public Wallet load(String login) {
        String filename = login + "_wallet.json";
        File file = new File(filename);
        if (!file.exists()) return new Wallet();

        try (Reader reader = new FileReader(filename)) {
            return gson.fromJson(reader, Wallet.class);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
            return new Wallet();
        }
    }
}
