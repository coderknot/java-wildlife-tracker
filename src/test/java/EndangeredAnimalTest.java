import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

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
  public void healthOptions_returnsHealthConstants_true() {
    List<String> healthOptionsList = EndangeredAnimal.healthOptions();

    assertTrue(healthOptionsList.contains(EndangeredAnimal.HEALTH_HEALTHY));
    assertTrue(healthOptionsList.contains(EndangeredAnimal.HEALTH_OKAY));
    assertTrue(healthOptionsList.contains(EndangeredAnimal.HEALTH_ILL));
  }

  @Test
  public void ageOptions_returnsAgeConstants_true() {
    List<String> ageOptionsList = EndangeredAnimal.ageOptions();

    assertTrue(ageOptionsList.contains(EndangeredAnimal.AGE_ADULT));
    assertTrue(ageOptionsList.contains(EndangeredAnimal.AGE_YOUNG));
    assertTrue(ageOptionsList.contains(EndangeredAnimal.AGE_NEWBORN));
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
    assertEquals(secondEndangeredAnimal, EndangeredAnimal.find(secondEndangeredAnimal.getId()));
  }

  @Test
  public void find_returnNullWhenNoEndangeredAnimalFound_null() {
    assertTrue(EndangeredAnimal.find(999) == null);
  }

  @Test
  public void getSightings_returnsSightingsForAnimal() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    Sighting testSighting = new Sighting(testEndangeredAnimal.getId(), "45.472428, -121.946466", "Ranger Avery");
    testSighting.save();
    assertEquals(1, testEndangeredAnimal.getSightings().size());
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
    assertEquals(testEndangeredAnimal.getId(), EndangeredAnimal.find(testEndangeredAnimal.getId()).getId());
  }

  @Test
  public void updateName_updatesNameInDatabase_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    testEndangeredAnimal.updateName("Red Fox");
    assertEquals("Red Fox", EndangeredAnimal.find(testEndangeredAnimal.getId()).getName());
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

  @Test
  public void delete_deletesEndangeredAnimalFromDatabase_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Fox", "Healthy", "Young");
    testEndangeredAnimal.save();
    int testEndangeredAnimalId = testEndangeredAnimal.getId();
    testEndangeredAnimal.delete();
    assertEquals(null, Animal.find(testEndangeredAnimalId));
  }

}
