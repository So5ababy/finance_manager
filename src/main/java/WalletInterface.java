import java.util.Scanner;

public class WalletInterface {
    private final Wallet wallet;
    private final Scanner scanner;

    public WalletInterface (Wallet wallet, Scanner scanner) {
        this.wallet = wallet;
        this.scanner = scanner;
    }

    public void start() {
        boolean exit = false;

        while (!exit) {
            System.out.println("Меню кошелька");
            System.out.println("1 - показать баланс");
            System.out.println("2 - добавить доход");
            System.out.println("3 - добавить расход");
            System.out.println("4 - просмотр транзакций");
            System.out.println("5 - добавить категорию с бюджетом");
            System.out.println("6 - показать информацию по категориям");
            System.out.println("7 - показать сводку по кошельку");
            System.out.println("8 - изменить бюджет по категории");
            System.out.println("9 - удалить категорию");
            System.out.println("10 - выйти из кошелька");
            System.out.print("Выберите действие: ");

            int option = safeInt(scanner);
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Баланс: " + wallet.getBalance());
                    break;
                case 2:
                    System.out.print("Сумма дохода: ");
                    double sumProfit = safeInput();
                    //double sumProfit = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Категория: ");
                    String profitCategory = scanner.nextLine();
                    wallet.profit(sumProfit, profitCategory);
                    break;
                case 3:
                    System.out.print("Сумма расхода: ");
                    double sumExpense = safeInput();
                    //double sumExpense = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Категория: ");
                    String expenseCategory = scanner.nextLine();
                    wallet.expense(sumExpense, expenseCategory);
                    break;
                case 4:
                    System.out.println("История транзакций");
                    for (Transactions tr : wallet.getTransactions()) {
                        System.out.println("Сумма: " + tr.getSum() + " | категория: " + tr.getCategory() + " | тип: " + tr.getType()); // + " | дата: " + tr.getDateTime());
                    }
                    break;
                case 5:
                    System.out.print("Введите название категории: ");
                    String category = scanner.nextLine();
                    System.out.print("Введите бюджет: ");
                    double limit = safeInput();
                    scanner.nextLine();
                    wallet.addCategory(category, limit);
                    break;
                case 6:
                    wallet.allLimitsCategorys();
                    break;
                case 7:
                    System.out.println("Текущий баланс: " + wallet.getBalance());
                    System.out.println("Общий доход: " + wallet.getTotalProfit());
                    System.out.println("Общий расход: " + wallet.getTotalExpense());
                    for (String cat : wallet.getCategoryLimits().keySet()) {
                        double lim = wallet.getCategoryLimits().get(cat);
                        double spent = wallet.getSpentByCategory(cat);
                        double pct = (lim == Double.MAX_VALUE) ? 0.0 : (spent/lim) * 100;
                        System.out.println(cat + " | лимит: " + lim + " | потрачено: " + spent + "(" + pct + "%" + ")");
                    }
                    break;
                case 8:
                    System.out.print("Введите название категории: ");
                    String editCategory = scanner.nextLine();
                    System.out.print("Введите новый лимит: ");
                    double newLimit = safeInput();
                    scanner.nextLine();
                    wallet.udpateCategoryLimit(editCategory, newLimit);
                    break;
                case 9:
                    System.out.println("Введите название категории для удаления: ");
                    String delCategory = scanner.nextLine();
                    wallet.removeCategory(delCategory);
                    break;
                case 10:
                    exit = true;
                    break;
                default:
                    System.out.println("Ошибка ввода, попробуйте снова");
            }
        }
    }
    private double safeInput() {
        while (true) {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                System.out.print("Некорректный ввод! Введите число: ");
                scanner.nextLine();
            }
        }
    }

    private int safeInt(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.print("Некорректный ввод! Выберите действие от 1 до 10: ");
                scanner.nextLine();
            }
        }
    }
}
