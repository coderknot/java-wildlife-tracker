import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;

public class Sighting implements DatabaseManagement {

  private int id;
  private int animalId;
  private String location;
  private String rangerName;
  private Timestamp timestamp;

  public Sighting(int animalId, String location, String rangerName) {
    this.animalId = animalId;
    this.location = location;
    this.rangerName = rangerName;
  }

  public int getId() {
    return this.id;
  }

  public int getAnimalId() {
    return this.animalId;
  }

  public String getAnimalName() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT name FROM animals where id = :id;";
      return con.createQuery(sql)
        .addParameter("id", this.getAnimalId())
        .executeAndFetchFirst(String.class);
    }
  }

  public String getLocation() {
    return this.location;
  }

  public String getRangerName() {
    return this.rangerName;
  }

  public Timestamp getTimestamp() {
    return this.timestamp;
  }

  public static List<Sighting> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings;";
      return con.createQuery(sql)
        .addColumnMapping("animal_id", "animalId")
        .addColumnMapping("ranger_name", "rangerName")
        .executeAndFetch(Sighting.class);
    }
  }

  public static Sighting find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE id=:id;";
      Sighting sighting = con.createQuery(sql)
        .addColumnMapping("animal_id", "animalId")
        .addColumnMapping("ranger_name", "rangerName")
        .addParameter("id", id)
        .executeAndFetchFirst(Sighting.class);
      return sighting;
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }

  @Override
  public boolean equals(Object otherSighting) {
    if(!(otherSighting instanceof Sighting)) {
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.getAnimalId() == (newSighting.getAnimalId()) && this.getLocation().equals(newSighting.getLocation()) && this.getRangerName().equals(newSighting.getRangerName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (animal_id, location, ranger_name, timestamp) VALUES (:animalId, :location, :rangerName, now());";

      this.id = (int) con.createQuery(sql, true)
        .addColumnMapping("animal_id", "animalId")
        .addColumnMapping("ranger_name", "rangerName")
        .addParameter("animalId", this.getAnimalId())
        .addParameter("location", this.getLocation())
        .addParameter("rangerName", this.getRangerName())
        .executeUpdate()
        .getKey();
    }

    try(Connection con = DB.sql2o.open()) {
      String timeStampQuery = "SELECT timestamp FROM sightings WHERE id = :id;";
      this.timestamp = con.createQuery(timeStampQuery)
        .addColumnMapping("animal_id", "animalId")
        .addColumnMapping("ranger_name", "rangerName")
        .addParameter("id", id)
        .executeAndFetchFirst(Timestamp.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM sightings WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

}
