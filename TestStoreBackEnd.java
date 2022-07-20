// --== CS400 File Header Information ==--
// Name: Allistair Mascarenhas
// Email: anmascarenha@wisc.edu
// Team: DC
// Role: Back End Developer 2
// TA: Yelun Bao
// Lecturer: Gary Dahl
// Notes to Grader: This file was by the Back End to initially test whether StoreBackEnd.java
// functions correctly

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test Suite made for the Back end.
 * 
 * @author Allistair
 *
 */
public class TestStoreBackEnd {

  /**
   * Loads the data from a test csv file in order to not affect the real data when the application
   * is being used
   */
  @BeforeClass
  public static void load() {
    DataLoader.CSVPATH = "test.csv";
    StoreBackEnd.loadGroceries();
  }

  HashTableMap<Grocery.Category, ArrayList<Grocery>> hashTable = StoreBackEnd.categories;

  /**
   * Tests whether the data is correctly correctly from the csv into the hash table.
   */
  @Test
  public void testLoadGroceries() {
    assertTrue(hashTable.size() != 0);

    // CLOTHING, FURNITURE, SCHOOLSUPPLIES, GAMES, FOOD
    int[] catSize = new int[] {16, 11, 11, 10, 21};
    Grocery.Category[] cats = Grocery.Category.values();
    ArrayList<Grocery> list;
    for (int i = 0; i < catSize.length; i++) {
      list = hashTable.get(cats[i]);
      assertEquals(catSize[i], list.size());
    }
  }

  /**
   * Tests whether the data in the hash table is stored with the right key.
   */
  @Test
  public void testGetGroceryList() {
    // test null
    assertEquals(null, StoreBackEnd.getGroceryList(null));

    // test non-existant category
    assertEquals(null, StoreBackEnd.getGroceryList("Shoes"));

    // tests different cases of categories
    ArrayList<Grocery> clothing = StoreBackEnd.getGroceryList("clothing");
    ArrayList<Grocery> furniture = StoreBackEnd.getGroceryList("FURNITURE");
    ArrayList<Grocery> schoolSupplies = StoreBackEnd.getGroceryList("sChOoLsUpPlIeS");
    ArrayList<Grocery> games = StoreBackEnd.getGroceryList("GAMEs");
    ArrayList<Grocery> food = StoreBackEnd.getGroceryList("fOOd");

    // expected item at nth index in array list

    Grocery expClothing0 = new Grocery("Oakley's Sunglasses", 10, 100, Grocery.Category.CLOTHING);
    assertEquals(groceryToString(expClothing0), groceryToString(clothing.get(0)));

    Grocery expFurniture10 = new Grocery("The Kitchen Sink (Yeah; we took everything)", 1, 28,
        Grocery.Category.FURNITURE);
    assertEquals(groceryToString(expFurniture10), groceryToString(furniture.get(10)));

    Grocery expSchool5 = new Grocery("Highlighter", 49, 2.77, Grocery.Category.SCHOOLSUPPLIES);
    assertEquals(groceryToString(expSchool5), groceryToString(schoolSupplies.get(5)));

    Grocery expGames3 = new Grocery("Cursed X2", 48, 26.88, Grocery.Category.GAMES);
    assertEquals(groceryToString(expGames3), groceryToString(games.get(3)));

    Grocery expFood20 = new Grocery("Hiker-Delights", 45, 3.65, Grocery.Category.FOOD);
    assertEquals(groceryToString(expFood20), groceryToString(food.get(20)));

  }

  /**
   * Tests whether the change quantity method changes the quantities of the method by the right
   * amount. Also checks for extreme inputs.
   */
  @Test
  public void testChangeQuantity() {

    // test null
    Grocery item = StoreBackEnd.changeQuantity("clothing", "fake item", 0);
    assertEquals(item, null);

    // test lookup
    Grocery lookup = StoreBackEnd.changeQuantity("Furniture", "Night Stand", 0);
    Grocery expected = new Grocery("Night Stand", 0, 59.2, Grocery.Category.FURNITURE);
    assertEquals(groceryToString(lookup), groceryToString(expected));

    // test remove
    int beforeRemoveQuantity = StoreBackEnd.changeQuantity("FOOD", "Fries", 0).getQuantity();
    Grocery afterRemove = StoreBackEnd.changeQuantity("food", "Fries", -5);

    if (afterRemove.getQuantity() - beforeRemoveQuantity != -5
        || (afterRemove.getQuantity() == 0 && beforeRemoveQuantity == 0)) {
      fail("Remove in changeQuantity() failed.");
    }

    // test add
    int beforeAddQuantity =
        StoreBackEnd.changeQuantity("Schoolsupplies", "Skateboard", 0).getQuantity();
    Grocery afterAdd = StoreBackEnd.changeQuantity("schoolsupplies", "Skateboard", 100);

    if (afterAdd.getQuantity() - beforeAddQuantity != 100
        || (afterRemove.getQuantity() == 1000000 && beforeRemoveQuantity == 1000000)) {
      fail("Add in changeQuantity() failed.");
    }

    // test lower limit
    Grocery lowerLimit = StoreBackEnd.changeQuantity("games", "Cursed X1", -9999);
    assertEquals(lowerLimit.getQuantity(), 0);

    // test upper limit
    Grocery upperLimit = StoreBackEnd.changeQuantity("CLOTHING", "Pink Pants", 12361283);
    assertEquals(upperLimit.getQuantity(), 1000000);

  }
  
  /**
   * Tests whether changing the quantity of an item updates the csv file.
   */
  @Test
  public void testUpdateCsv() {
    DataLoader.CSVPATH = "test.csv";

    Grocery oldItem = StoreBackEnd.changeQuantity("Clothing", "Oakley's Sunglasses", 6);
    StoreBackEnd.loadGroceries();
    HashTableMap<Grocery.Category, ArrayList<Grocery>> tempHash = StoreBackEnd.categories;
    Grocery newItem = tempHash.get(Grocery.Category.CLOTHING).get(0);
    assertEquals(groceryToString(oldItem), groceryToString(newItem));
    assertEquals(oldItem.getQuantity(), newItem.getQuantity());
  }
  
  /**
   * Tests private helper method to convert a string to the matching enumeration
   */
  @Test
  public void testStringToEnum() {
    // test null
    assertEquals(StoreBackEnd.stringToEnum(null), null);

    // test lower case
    assertEquals(StoreBackEnd.stringToEnum("clothing"), Grocery.Category.CLOTHING);

    // test upper case
    assertEquals(StoreBackEnd.stringToEnum("FURNITURE"), Grocery.Category.FURNITURE);

    // test mixed cases
    assertEquals(StoreBackEnd.stringToEnum("sChOoLsUpPlIeS"), Grocery.Category.SCHOOLSUPPLIES);
  }
  
  /**
   * Tests private helper method to convert an enumeration into a string in UpperCamelCase
   */
  @Test
  public void testEnumToString() {
    // test Clothing
    assertEquals(StoreBackEnd.enumToString(Grocery.Category.CLOTHING), "Clothing");

    // test Furniture
    assertEquals(StoreBackEnd.enumToString(Grocery.Category.FURNITURE), "Furniture");

    // test SchoolSupplies
    assertEquals(StoreBackEnd.enumToString(Grocery.Category.SCHOOLSUPPLIES), "SchoolSupplies");

    // test Games
    assertEquals(StoreBackEnd.enumToString(Grocery.Category.GAMES), "Games");

    // test Food
    assertEquals(StoreBackEnd.enumToString(Grocery.Category.FOOD), "Food");
  }

  /**
   * Private helper method to get all the information in a Grocery object as a String.
   * 
   * @param item - Grocery
   * @return String of all the information in a Grocery object
   */
  public String groceryToString(Grocery item) {
    String str = "";
    str += item.getProductName();
    str += item.getPrice();
    str += item.getCategory().toString();
    return str;
  }

}

