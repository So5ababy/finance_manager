import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    @Test
    void testProfit() {
        Wallet wallet = new Wallet();
        wallet.profit(100, "Зп");
        assertEquals(100, wallet.getBalance());
    }

    @Test
    void testExpense() {
        Wallet wallet = new Wallet();
        wallet.profit(200, "Зп");
        wallet.expense(110, "еда");
        assertEquals(90, wallet.getBalance());
    }

    @Test
    void testCategoryLimit() {
        Wallet wallet = new Wallet();
        wallet.addCategory("развлечения", 100);
        wallet.profit(300, "Зп");
        wallet.expense(50, "развлечения");
        wallet.expense(80, "развлечения");
        assertEquals(250, wallet.getBalance());
    }

    @Test
    void testTotalProfit() {
        Wallet wallet = new Wallet();
        wallet.profit(100, "зп");
        wallet.profit(100, "бонус");
        assertEquals(200, wallet.getTotalProfit());
    }

    @Test
    void testExpenseOverBalance() {
        Wallet wallet = new Wallet();
        wallet.expense(50, "еда"); // баланса 0
        assertEquals(0, wallet.getBalance());
    }

    @Test
    void testAddAndRemoveCategory() {
        Wallet wallet = new Wallet();
        wallet.addCategory("кофе", 50);
        assertTrue(wallet.getCategoryLimits().containsKey("кофе"));
        wallet.removeCategory("кофе");
        assertFalse(wallet.getCategoryLimits().containsKey("кофе"));
    }
}
