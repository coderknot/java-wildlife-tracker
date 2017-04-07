import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class EndangeredAnimalTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void endangeredAnimal_instantiatesCorrectly_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    assertEquals(true, testEndangeredAnimal instanceof EndangeredAnimal);
  }

  @Test
  public void getId_endangeredAnimalInstatiatesWithId_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    assertTrue(testEndangeredAnimal.getId() > 0);
  }

  @Test
  public void getName_endangeredAnimalInstantiatesWithName_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    assertEquals("Fox", testEndangeredAnimal.getName());
  }

  @Test
  public void getHealth_endangeredAnimalInstantiatesWithHealth_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    assertEquals("Healthy", testEndangeredAnimal.getHealth());
  }

  @Test
  public void getAge_endangeredAnimalInstantiatesWithAge_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    assertEquals("Young", testEndangeredAnimal.getAge());
  }

  @Test
  public void all_returnsAllInstancesOfEndangeredAnimal_true() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Badger", "Okay", "Adult");
    secondEndangeredAnimal.save();
    assertEquals(true, EndangeredAnimal.all().get(0).equals(firstEndangeredAnimal));
    assertEquals(true, EndangeredAnimal.all().get(1).equals(secondEndangeredAnimal));
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Badger", "Okay", "Adult");
    secondEndangeredAnimal.save();
    assertEquals(EndangeredAnimal.find(secondEndangeredAnimal.getId()), secondEndangeredAnimal);
  }

  @Test
  public void find_returnNullWhenNoEndangeredAnimalFound_null() {
    assertTrue(EndangeredAnimal.find(999) == null);
  }

  @Test
  public void equals_returnsTrueIfEndangeredAnimalsAreSame_true() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    assertTrue(firstEndangeredAnimal.equals(secondEndangeredAnimal));
  }

  @Test
  public void save_savesObjectToDatabase_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    assertEquals(true, EndangeredAnimal.all().get(0).equals(testEndangeredAnimal));
  }

  @Test
  public void update_updatesHealthInDatabase_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    testEndangeredAnimal.updateHealth("ill");
    assertEquals("ill", EndangeredAnimal.find(testEndangeredAnimal.getId()).getHealth());
  }

  @Test
  public void update_updatesAgeInDatabase_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    testEndangeredAnimal.updateAge("Adult");
    assertEquals("Adult", EndangeredAnimal.find(testEndangeredAnimal.getId()).getAge());
  }

}
