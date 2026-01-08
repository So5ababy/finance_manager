import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet {
    private double balance = 0;
    private final List<Transactions> transactions = new ArrayList<>();

    private final Map<String, Double> categoryLimit = new HashMap<>();
    private final Map<String, Double> categorySpent = new HashMap<>();

    public Map<String, Double> getCategoryLimits() {
        return categoryLimit;
    }

    public void profit(double sumProfit, String profitCategory) {
        balance += sumProfit;
        transactions.add(new Transactions(sumProfit, profitCategory, "доход"));
    }

    public void expense(double sumExpense, String expenseCategory) {
        if (balance < sumExpense) {
            System.out.println("Недостаточно средств");
            return;
        }

        double limit = categoryLimit.getOrDefault(expenseCategory, Double.MAX_VALUE);
        double spent = categorySpent.getOrDefault(expenseCategory, 0.0);

        if (spent + sumExpense > limit) {
            System.out.println("Превышен лимит по категории " + expenseCategory + ", оплата отменена");
            return;
        }

        balance -= sumExpense;
        transactions.add(new Transactions(sumExpense, expenseCategory, "расход"));
        categorySpent.put(expenseCategory, spent + sumExpense);
        notfication(expenseCategory);
    }

    public void addCategory (String category, double limit) {
        categoryLimit.put(category, limit);
        System.out.println("Категория " + category + " добавлена, лимит - " + limit);
    }

    public void allLimitsCategorys() {
        if (categoryLimit.isEmpty()) {
            System.out.println("Лимиты по категориям не заданы");
            return;
        }
        for (String limcat : categoryLimit.keySet()) {
            double limit = categoryLimit.get(limcat);
            double spent = categorySpent.getOrDefault(limcat, 0.0);
            System.out.println(limcat + " | лимит: " + limit + " | потрачено: " + spent);
        }
    }

    public double getTotalProfit() {
        double total = 0;
        for (Transactions t : transactions ) {
            if ("доход".equalsIgnoreCase(t.getType())) total += t.getSum();
        }
        return total;
    }

    public double getTotalExpense() {
        double total = 0;
        for (Transactions t : transactions) {
            if("расход".equalsIgnoreCase(t.getType())) total += t.getSum();
        }
        return total;
    }

    public double getSpentByCategory(String category) {
        return categorySpent.getOrDefault(category, 0.0);
    }

    private void notfication(String category) {
        double limit = categoryLimit.getOrDefault(category, Double.MAX_VALUE);
        if (limit == Double.MAX_VALUE) return;
        double spent = categorySpent.getOrDefault(category, 0.0);
        double percent = (spent/limit) * 100;
        if (percent >= 100) {
            System.out.println("Вы превысили лимит по категории: " + category);
        } else if (percent >= 80) {
            System.out.println("Предупреждение: вы использовали " + percent + "% лимита по категории " + category);
        }
    }

    public void udpateCategoryLimit(String category, double newLimit) {
        if (!categoryLimit.containsKey(category)) {
            System.out.println("Категория " + category + " не существует");
            return;
        }
        //categoryLimit.put(category, newLimit);
        // System.out.println("Лимит по категории " + category + " изменен на " + newLimit);

        double spent = categorySpent.getOrDefault(category, 0.0);
        if (spent > newLimit) {
            System.out.println("По категории " + category + " уже потрачено " + spent + " ,что превышает новый лимит."
                    + " Лимит не изменен");

        } else {
            categoryLimit.put(category, newLimit);
            System.out.println("Лимит по категории " + category + " изменен на " + newLimit);
        }
    }

    public void removeCategory(String category) {
        if (!categoryLimit.containsKey(category)) {
            System.out.println("Категории " + category + " не существует");
            return;
        }
        categoryLimit.remove(category);
        categorySpent.remove(category);
        System.out.println("Категория " + category + " удалена");
    }

    public double getBalance() {
        return balance;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }
}
