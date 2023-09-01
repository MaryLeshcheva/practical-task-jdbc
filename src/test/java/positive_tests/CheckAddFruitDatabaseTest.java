package positive_tests;


import org.junit.jupiter.api.Test;
import java.sql.SQLException;


public class CheckAddFruitDatabaseTest extends BaseTest {

    @Test
    public void testCheckAddFruit() throws SQLException { //проверка добавления нового товара в таблицу FOOD
        String name = "Ананас";
        String type = "FRUIT";
        boolean isExotic = true;

        long id = addProduct(name, type, isExotic);
        checkProductAvailability(id);
        deleteProduct(id);
    }
}