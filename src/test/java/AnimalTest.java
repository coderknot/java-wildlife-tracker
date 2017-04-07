import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
  public void equals_returnsTrueIfAnimalsAreSame_true() {
    Animal firstAnimal = new Animal("Deer");
    Animal anotherAnimal = new Animal("Deer");
    assertTrue(firstAnimal.equals(anotherAnimal));
  }

  @Test
  public void save_savesObjectToDatabase_true() {
    Animal testAnimal = new Animal("Deer");
    testAnimal.save();
    assertEquals(true, Animal.all().get(0).equals(testAnimal));
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
    testAnimal.delete();
    assertEquals(0, Animal.all().size());
  }

}
