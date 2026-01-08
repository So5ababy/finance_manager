import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserSettings settings = new UserSettings();
        settings.loadUsers();
        Memory memory = new Memory();

        while (true) {
            System.out.println("Введите 1 для регистрации");
            System.out.println("Введите 2 для входа");
            System.out.println("Введите 3 для выхода из программы");
            System.out.print("Ваш выбор: ");
            int registerOrLogIn = inputInt(scanner);
            scanner.nextLine();

            if (registerOrLogIn == 1) {
                System.out.println("Логин: ");
                String login = scanner.nextLine();
                System.out.println("Пароль: ");
                String password = scanner.nextLine();
                User newUser = settings.register(login, password);
                if (newUser !=null ) {
                    System.out.println("Регистрация пользователя " + newUser.getLogin() + " завершена");
                }
            }

            else if (registerOrLogIn == 3) {
                break;
            }

            else if (registerOrLogIn == 2) {
                System.out.println("Логин: ");
                String login = scanner.nextLine();
                System.out.println("Пароль: ");
                String password = scanner.nextLine();
                User user = settings.login(login, password);
                if (user != null) {
                    System.out.println("Добро пожаловать, " + user.getLogin());
                    WalletInterface walletInterface = new WalletInterface(user.getWallet(), scanner);
                    walletInterface.start();
                    memory.save(user.getWallet(), user.getLogin());
                    settings.saveUsers();
                }
            } else {
                System.out.println("Неверный логин или пароль");
            }
        }
    }
    private static int inputInt(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.print("Некорректный ввод! Введите число: ");
                scanner.nextLine();
            }
        }
    }

}
