// --== CS400 File Header Information ==--
// Name: Justin Qiao
// Email: sqiao6@wisc.edu
// Team: DC
// Role: Test Engineer 1
// TA: Yelun Bao
// Lecturer: Florian Heimerl
// Notes to Grader:

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the implementation of Grocery and DataLoader. Checks whether the data in csv file is
 * correctly transfered to the back-end developer.
 * 
 * @author Justin Qiao
 */
class DataWranglerTest {

  /**
   * Setup test target csv file
   * 
   */
  @BeforeEach
  void setup() {
    // set up the test targeting groceries_for_testing.csv
    DataLoader.CSVPATH = "groceries_for_testing.csv";
  }

  /**
   * Recovers csv path stored in DataLoader.java.
   * 
   */
  @AfterEach
  void recover() {
    // restore DataLoader for application
    DataLoader.CSVPATH = "groceries.csv";
  }
  
  
  /**
   * Checks whether the Grocery class is working as excepted. Test fails if the grocery instance's
   * getters return incorrect data.
   */
  @Test
  void testGrocery() {
    try {
      Grocery grocery = new Grocery("testProduct", 2, 1.99, Grocery.Category.FOOD);
      if (!grocery.getProductName().equals("testProduct") || grocery.getQuantity() != 2
          || grocery.getPrice() != 1.99 || grocery.getCategory() != Grocery.Category.FOOD)
        fail("Grocery instance was not created properly.");
    } catch (Exception e) {
      fail("Un expected exception was thorwn during the usage of class Grocery.");
    }
  }

  /**
   * Checks whether the ArrayList given by DataLoader's getGroceries functions properly. Test fails
   * if the csv file information was not stored in the ArrayList correctly.
   */
  @Test
  void testDataLoader() {
    try {
      ArrayList<Grocery> groceries = DataLoader.getGroceries();
      if (groceries.size() != 69)
        fail("The number of groceries was not initialized correctly according to the csv file.");
      Grocery test = groceries.get(0);
      if (!test.getProductName().equals("Oakley's Sunglasses")
          || !test.getCategory().equals(Grocery.Category.CLOTHING) || test.getPrice() != 100
          || test.getQuantity() != 10)
        fail("The first grocery item was read incorrectly from the csv file.");
      test = groceries.get(29);
      if (!test.getProductName().equals("Candy Poppers")
          || !test.getCategory().equals(Grocery.Category.FOOD) || test.getPrice() != 4.99
          || test.getQuantity() != 54)
        fail("The 30th grocery item, Candy Poppers, was read incorrectly from the csv file.");
      test = groceries.get(groceries.size() - 1);
      if (!test.getProductName().equals("Buyer's Remorse - The Game")
          || !test.getCategory().equals(Grocery.Category.GAMES) || test.getPrice() != 999.99
          || test.getQuantity() != 999)
        fail("The last grocery item was read incorrectly from the csv file.");
    } catch (Exception e) {
      fail("Unexpected exception was thorwn during the usage of class Grocery.");
    }
  }

}
