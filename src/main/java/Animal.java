import org.sql2o.*;
import java.util.List;

public class Animal {

  public int id;
  public String name;

  public static final String DATABASE_TYPE = "non-endangered";

  public Animal(String name) {
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public static List<Animal> all() {

    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE type = :DATABASE_TYPE;";
      return con.createQuery(sql)
        .addColumnMapping("type", "DATABASE_TYPE")
        .addParameter("DATABASE_TYPE", DATABASE_TYPE)
        .throwOnMappingFailure(false)
        .executeAndFetch(Animal.class);
    }
  }

  public static Animal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id = :id and type = :DATABASE_TYPE;";
      Animal animal = con.createQuery(sql)
        .addColumnMapping("type", "DATABASE_TYPE")
        .addParameter("id", id)
        .addParameter("DATABASE_TYPE", DATABASE_TYPE)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Animal.class);
      return animal;
    }
  }

  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE animal_id = :id;";
        List<Sighting> sightings = con.createQuery(sql)
          .addColumnMapping("animal_id", "id")
          .addColumnMapping("ranger_name", "rangerName")
          .addParameter("id", this.getId())
          .executeAndFetch(Sighting.class);
      return sightings;
    }
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if(!(otherAnimal instanceof Animal)) {
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getName().equals(newAnimal.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name, type) VALUES (:name, :type);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.getName())
        .addParameter("type", this.DATABASE_TYPE)
        .throwOnMappingFailure(false)
        .executeUpdate()
        .getKey();
    }
  }

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET name = :name WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("name", name)
        .throwOnMappingFailure(false)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM animals WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

}
