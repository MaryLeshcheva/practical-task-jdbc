package positive_tests;

import core.DatabaseConfig;
import core.Queries;
import org.aeonbits.owner.ConfigFactory;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.sql.*;

public class BaseTest {

    private static Connection connection;

    @BeforeAll
    public static void prepareConnection() throws SQLException { //предусловие для всех тестов
        DatabaseConfig databaseConfig = ConfigFactory.create(DatabaseConfig.class);
        String databaseUrl = databaseConfig.databaseUrl();
        String databaseUser = databaseConfig.databaseUser();
        String databasePass = databaseConfig.databasePass();

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(databaseUrl);
        dataSource.setUser(databaseUser);
        dataSource.setPassword(databasePass);

        connection = dataSource.getConnection();
    }

    @AfterAll
    public static void closeConnection() throws SQLException { //постусловие для всех тестов
        connection.close();
    }


    public static long addProduct(String name, String type, boolean isExotic) throws SQLException { // метод добавления товара
        PreparedStatement insertStatement = connection.
                prepareStatement(Queries.insertNewProduct, Statement.RETURN_GENERATED_KEYS);
        int isExoticNumber = isExotic ? 1 : 0;
        insertStatement.setString(1, name);
        insertStatement.setString(2, type);
        insertStatement.setInt(3, isExoticNumber);

        int affectedRows = insertStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        ResultSet resultSet = insertStatement.getGeneratedKeys();
        long id = 0;
        while (resultSet.next()) {
            id = resultSet.getLong(1);
        }

        return id;
    }

    public static void checkProductAvailability(long id) throws SQLException { //метод проверки добавления товара
        PreparedStatement selectStatement = connection.prepareStatement(Queries.selectProductById);
        selectStatement.setLong(1, id);
        ResultSet resultSet = selectStatement.executeQuery();
        boolean result = resultSet.next();
        Assertions.assertTrue(result, "Товар с id " + id + " не найден в таблице");
    }

    protected void deleteProduct(long id) throws SQLException { //метод удаления товара
        PreparedStatement deleteStatement = connection.prepareStatement(Queries.deleteProduct);
        deleteStatement.setLong(1, id);
        deleteStatement.executeUpdate();
    }

}
