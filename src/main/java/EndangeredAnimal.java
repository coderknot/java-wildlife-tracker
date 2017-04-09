import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class EndangeredAnimal extends Animal implements DatabaseManagement {

  private String health;
  private String age;

  public static final String DATABASE_TYPE = "endangered";

  public static final String HEALTH_HEALTHY = "healthy";
  public static final String HEALTH_OKAY = "okay";
  public static final String HEALTH_ILL = "ill";

  public static final String AGE_ADULT = "adult";
  public static final String AGE_YOUNG = "young";
  public static final String AGE_NEWBORN = "newborn";

  public EndangeredAnimal(String name, String health, String age) {
    super(name);
    this.health = health;
    this.age = age;
  }

  public String getHealth() {
    return this.health;
  }

  public String getAge() {
    return this.age;
  }

  public static List<String> healthOptions() {
    List<String> healthOptions = new ArrayList<String>();
    healthOptions.add(EndangeredAnimal.HEALTH_HEALTHY);
    healthOptions.add(EndangeredAnimal.HEALTH_OKAY);
    healthOptions.add(EndangeredAnimal.HEALTH_ILL);
    return healthOptions;
  }

  public static List<String> ageOptions() {
    List<String> ageOptions = new ArrayList<String>();
    ageOptions.add(EndangeredAnimal.AGE_ADULT);
    ageOptions.add(EndangeredAnimal.AGE_YOUNG);
    ageOptions.add(EndangeredAnimal.AGE_NEWBORN);
    return ageOptions;
  }

  public static List<Animal> all() {
    List<Animal> animalsList = new ArrayList<Animal>();

    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE type = :DATABASE_TYPE;";
      List<EndangeredAnimal> endangeredAnimalsList = con.createQuery(sql)
        .addColumnMapping("type", "DATABASE_TYPE")
        .addParameter("DATABASE_TYPE", DATABASE_TYPE)
        .throwOnMappingFailure(false)
        .executeAndFetch(EndangeredAnimal.class);

      for(EndangeredAnimal endangeredAnimal : endangeredAnimalsList) {
        animalsList.add((Animal) endangeredAnimal);
      }
    }

    return animalsList;
  }

  public static EndangeredAnimal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id = :id AND type = :DATABASE_TYPE;";
      EndangeredAnimal endangeredanimal = con.createQuery(sql)
        .addColumnMapping("type", "DATABASE_TYPE")
        .addParameter("id", id)
        .addParameter("DATABASE_TYPE", DATABASE_TYPE)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(EndangeredAnimal.class);
      return endangeredanimal;
    }
  }

  @Override
  public boolean equals(Object otherEndangeredAnimal) {
    if(!(otherEndangeredAnimal instanceof EndangeredAnimal)) {
      return false;
    } else {
      EndangeredAnimal newEndangeredAnimal = (EndangeredAnimal) otherEndangeredAnimal;
      return this.getName().equals(newEndangeredAnimal.getName()) && this.getHealth().equals(newEndangeredAnimal.getHealth()) && this.getAge().equals(newEndangeredAnimal.getAge());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name, type, health, age) VALUES (:name, :type, :health, :age);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.getName())
        .addParameter("type", this.DATABASE_TYPE)
        .addParameter("health", this.getHealth())
        .addParameter("age", this.getAge())
        .executeUpdate()
        .getKey();
    }
  }

  public void updateHealth(String health) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET health = :health WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .addParameter("health", health)
        .executeUpdate();
    }
  }

  public void updateAge(String age) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET age = :age WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .addParameter("age", age)
        .executeUpdate();
    }
  }

}
