import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String url = String.format("/sightings");
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings", (request, respone) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");

      model.put("sightings", Sighting.all());
      model.put("template_content", "templates/sightings.vtl");

      List<Animal> animalsList = Animal.all();
      model.put("animals", animalsList);

      List<Animal> endangeredAnimalsList = EndangeredAnimal.all();
      model.put("endangeredAnimals", endangeredAnimalsList);

      String animalType = Animal.DATABASE_TYPE;
      model.put("animalType", animalType);

      String endangeredAnimalType = EndangeredAnimal.DATABASE_TYPE;
      model.put("endangeredAnimalType", endangeredAnimalType);

      model.put("template_form", "templates/sighting-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals", (request, respone) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");

      List<Animal> animalsList = Animal.all();
      model.put("animals", animalsList);

      List<Animal> endangeredAnimalsListTemp = EndangeredAnimal.all();
      List<EndangeredAnimal> endangeredAnimalsList = new ArrayList<EndangeredAnimal>();
      for(Animal animal : endangeredAnimalsListTemp) {
        if(animal instanceof EndangeredAnimal) {
          endangeredAnimalsList.add((EndangeredAnimal) animal);
        }
      }
      model.put("endangeredAnimals", endangeredAnimalsList);
      model.put("template_content", "templates/animals.vtl");

      model.put("healthOptions", EndangeredAnimal.healthOptions());
      model.put("ageOptions", EndangeredAnimal.ageOptions());
      model.put("template_form", "templates/animal-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      String name = request.queryParams("add-animal-name");
      String type = request.queryParams("add-animal-type");

      if(type.equals(Animal.DATABASE_TYPE)) {
        Animal newAnimal = new Animal(name);
        newAnimal.save();
      } else if(type.equals(EndangeredAnimal.DATABASE_TYPE)) {
        String health = "";
        String age = "";

        List<String> healthOptions = EndangeredAnimal.healthOptions();
        if(healthOptions.contains(request.queryParams("add-animal-health"))) {
          health = request.queryParams("add-animal-health");
        }

        List<String> ageOptions = EndangeredAnimal.ageOptions();
        if(ageOptions.contains(request.queryParams("add-animal-age"))) {
          age = request.queryParams("add-animal-age");
        }

        EndangeredAnimal newEndangeredAnimal = new EndangeredAnimal(name, health, age);
        newEndangeredAnimal.save();
      }

      String url = String.format("/animals");
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sightings/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int animalId = 0;
      String type = request.queryParams("add-sighting-type");

      if(type.equals(Animal.DATABASE_TYPE)) {
        animalId = Integer.parseInt(request.queryParams("add-sighting-non"));
      } else if(type.equals(EndangeredAnimal.DATABASE_TYPE)) {
        animalId = Integer.parseInt(request.queryParams("add-sighting-endangered"));
      }

      String location = request.queryParams("add-sighting-location");
      String rangerName = request.queryParams("add-sighting-ranger");

      Sighting sighting = new Sighting(animalId, location, rangerName);
      sighting.save();

      String url = String.format("/sightings");
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
