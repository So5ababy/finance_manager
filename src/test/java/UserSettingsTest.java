import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class UserSettingsTest {
    private UserSettings userSettings;

    @BeforeEach
    void setUp() {
        userSettings = new UserSettings();
    }
    @Test
    void testRegisterAndLogin() {
        User user = userSettings.register("test", "123");
        assertNotNull(user);
        assertEquals("test", user.getLogin());

        User loginUser = userSettings.login("test", "123");
        assertNotNull(loginUser);
    }

    @Test
    void testRegisterDuplicate() {
        userSettings.register("test", "123");
        User duplicate = userSettings.register("test", "123");
        assertNull(duplicate);
    }

    @Test
    void testLoginWrongPassword() {
        userSettings.register("test", "123");
        User login = userSettings.login("test", "456");
        assertNull(login);
    }

    @Test
    void testLoginNonExistingUser() {
        User login = userSettings.login("unuser", "123");
        assertNull(login);
    }
}
