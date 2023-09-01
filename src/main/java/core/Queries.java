package core;
public class Queries {
    public static final String selectProductById = "SELECT * FROM FOOD WHERE FOOD_ID = ?";
    public static final String insertNewProduct = "INSERT INTO FOOD (FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC) VALUES(?, ?, ?)";
    public static final String deleteProduct = "DELETE FROM FOOD WHERE FOOD_ID = ?";

}
