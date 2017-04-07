import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class AnimalTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void animal_instantiatesCorrectly_true() {
    Animal testAnimal = new Animal("Deer");
    assertEquals(true, testAnimal instanceof Animal);
  }

  @Test
  public void getId_animalInstantiatesWithID_true() {
    Animal testAnimal = new Animal("Deer");
    testAnimal.save();
    assertTrue(testAnimal.getId() > 0);
  }

  @Test
  public void getName_animalInstantiatesWithName_true() {
    Animal testAnimal = new Animal("Deer");
    assertEquals("Deer", testAnimal.getName());
  }

  @Test
  public void all_returnsAllInstancesOfAnimal_true() {
    Animal firstAnimal = new Animal("Deer");
    firstAnimal.save();
    Animal secondAnimal = new Animal("Black Bear");
    secondAnimal.save();
    assertEquals(true, Animal.all().get(0).equals(firstAnimal));
    assertEquals(true, Animal.all().get(1).equals(secondAnimal));
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
    Animal firstAnimal = new Animal("Deer");
    firstAnimal.save();
    Animal secondAnimal = new Animal("Black Bear");
    secondAnimal.save();
    assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
  }

  @Test
  public void find_returnsNullWhenNoAnimalFound_null() {
    assertTrue(Animal.find(999) == null);
  }

  @Test
  public void getSightings_returnsSightingsForAnimal() {
    Animal testAnimal = new Animal("Deer");
    testAnimal.save();
    Sighting testSighting = new Sighting(testAnimal.getId(), "45.472428, -121.946466", "Ranger Avery");
    testSighting.save();

    assertEquals(1, testAnimal.getSightings().size());
  }

  @Test
  public void equals_returnsTrueIfAnimalsAreSame_true() {
    Animal firstAnimal = new Animal("Deer");
    Animal anotherAnimal = new Animal("Deer");
    assertTrue(firstAnimal.equals(anotherAnimal));
  }

  @Test
  public void save_savesObjectToDatabase_true() {
    Animal testAnimal = new Animal("Deer");
    testAnimal.save();
    assertEquals(testAnimal.getId(), Animal.find(testAnimal.getId()).getId());
  }

  public void updateName_updatesNameInDatabase_String() {
    Animal testAnimal = new Animal("Deer");
    testAnimal.save();
    testAnimal.updateName("Buck");
    assertEquals("Buck", Animal.find(testAnimal.getId()).getName());
  }

  @Test
  public void delete_deletesAnimalFromDatabase_0() {
    Animal testAnimal = new Animal("Deer");
    testAnimal.save();
    int testAnimalID = testAnimal.getId();
    testAnimal.delete();
    assertEquals(null, Animal.find(testAnimalID));
  }

}
