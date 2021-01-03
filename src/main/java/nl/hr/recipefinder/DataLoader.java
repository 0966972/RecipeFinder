package nl.hr.recipefinder;

import nl.hr.recipefinder.model.dto.IngredientDto;
import nl.hr.recipefinder.model.dto.RecipeDto;
import nl.hr.recipefinder.model.dto.RecipeIngredientDto;
import nl.hr.recipefinder.model.entity.*;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.IngredientService;
import nl.hr.recipefinder.service.RecipeIngredientService;
import nl.hr.recipefinder.service.RecipeService;
import nl.hr.recipefinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maartendegoede on 07/12/2020.
 * Copyright © 2020 Maarten de Goede. All rights reserved.
 */
@Component
public class DataLoader implements ApplicationRunner {
  private final UserService userService;
  private final RecipeService recipeService;
  private final IngredientService ingredientService;
  private final RecipeIngredientService recipeIngredientService;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;


  public DataLoader(
    UserService userService,
    RecipeService recipeService,
    IngredientService ingredientService,
    RecipeIngredientService recipeIngredientService, PasswordEncoder passwordEncoder,
    ModelMapper modelMapper
  ) {
    this.userService = userService;
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.recipeIngredientService = recipeIngredientService;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
  }


  @Override
  public void run(ApplicationArguments args) {
    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword(passwordEncoder.encode("admin"));
    admin.setRole(Role.ADMIN);
    userService.save(admin);


    List<IngredientDto> ingredients = List.of(
      new IngredientDto("Demi Créme Fraîche"),
      new IngredientDto("Gemengde Paddenstoelen"),
      new IngredientDto("Rode Ui"),
      new IngredientDto("Knoflook"),
      new IngredientDto("Zilveruitjes"),
      new IngredientDto("Cornichons"),
      new IngredientDto("Verse Bladpeterselie"),
      new IngredientDto("Olijfolie"),
      new IngredientDto("Kappertjes"),
      new IngredientDto("Whisky"),
      new IngredientDto("Gerookte Paprikapoeder"),
      new IngredientDto("Verse Gemberwortel"),
      new IngredientDto("Arachideolie"),
      new IngredientDto("Wortel"),
      new IngredientDto("Verse Rode Peper"),
      new IngredientDto("Ingelegde Sushigember"),
      new IngredientDto("Lente-uitjes"),
      new IngredientDto("Rode Misopasta"),
      new IngredientDto("Zoutarme Sojasaus"),
      new IngredientDto("Eiernoedels"),
      new IngredientDto("Shanghai of Baby Paksoi"),
      new IngredientDto("Sesamzaad")
    );
    List<Ingredient> savedIngredients = ingredientService.findOrCreateIngredients(
      ingredients.stream().map(
        (it) -> modelMapper.map(it, Ingredient.class)
      ).collect(Collectors.toList())
    );


    RecipeDto mushroomStroganoff = new RecipeDto(
      // name
      "Paddenstoelen Stroganoff",

      // description
      "Crunchy Cornichons, Geurige Kapptertjes, Romige Whiskysaus & Peterselie",

      // instructions
      "Bereid om te beginnen alle ingrediënten voor: maak de paddenstoelen schoon en scheur de grotere in stukjes. Pel de rode ui en knoflook, en snijd ze in dunne ringen en plakjes. Snijd de zilveruitjes en cornichons flinterdun. Pluk en snipper de peterselieblaadjes, en snijd de steeltjes fijn. " +
        "\n\nZet een grote, droge koekenpan met antiaanbaklaag met e paddenstoelen en rode ui op hoog vuur, schud de pan om ze uit te spreiden en bak ze 5 minuten terwijl je regelmatig roert (hierdoor komt de nootachtige smaak goed los). Sprenkel er  1 eetlepel olie over en doe de knoflook, zilveruitjes, cornichons, peterseliesteeltjes, en kappertjes erbij. Schenk na 3 minuten de whisky in de pan, kantel hem voorzichtig om de vlam in de pan te laten slaan, of steek de alcohol voorzichtig aan met een lucifer (pas op je wenkbrauwen!). Voeg nadat de vlammen gedoofd zijn ¼ eetlepel paprikapoeder, de crème fraîche en peterselie toe, en meng alles goed. Giet er een scheutje kokend water bij om het paddenstoelenmengsel een mooie, sausachtige consistentie te geven en voeg naar smaak zeezout en zwarte peper toe. " +
        "\n\nVerdeel de stroganoff over de borden, strooi er een snufje paprikapoeder op en geef er luchtige rijst bij.",

      // servings
      2,

      // ingredients
      List.of(
//        new IngredientDto(null, "Mushrooms", "500g"),
//        new IngredientDto("Stroganoff", "382l")
      ),

      // pictures
      List.of(),

      // steps
      List.of()
    );
    Long recipeId = recipeService.save(modelMapper.map(mushroomStroganoff, Recipe.class)).getId();

    List<RecipeIngredientDto> mushroomStroganoffIngredients = List.of(
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(0).getId()),
        "80g", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(1).getId()),
        "400g", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(2).getId()),
        "1", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(3).getId()),
        "2 tenen", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(4).getId()),
        "4", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(5).getId()),
        "2", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(6).getId()),
        "4 takjes", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(7).getId()),
        "", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(8).getId()),
        "1 el", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(9).getId()),
        "50 ml", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(10).getId()),
        "", ""
      )
    );
    recipeIngredientService.saveAll(
      mushroomStroganoffIngredients.stream().map(
        (it) -> modelMapper.map(it, RecipeIngredient.class)
      ).collect(Collectors.toList())
    );


    Recipe noodleSoup = new Recipe();
    noodleSoup.setName("Vlugge Noedelsoep met Paddenstoelen");
    noodleSoup.setDescription("Vlugge wortel-Gemberpickle, lente-uitjes & sesamstrooisel");
    noodleSoup.setInstructions(
      "Pel en schil de knoflook en gember, snijd ze in dunne plakjes en doe ze met 1 eetlepel olie in een grote, zware braadpan op hoog vuur. Bak ze 2 minuten, voeg het eekhoorntjesbrood en 1,5 liter kokend water toe, leg een deksel op de pan en laat 10 minuten op laag vuur koken. Boek intussen de wortel, rasp hem met de rode peper op een grove rasp en meng er de sushigember door. Maak de lente-uitjes schoon, snijd ze in dunne ringen en zet ze opzij." +
        "\n\nRoer wanneer de tijd erop zit de misopasta en 2 eetlepels sojasaus door de bouillon. Kook de eiernoedels volgens de aanwijzingen op de verpakking en verdeel ze over view warme kommen. Breng de bouillon op smaak met sojasaus en zwarte peper. Snijd de stronkjes paksoi door midden of in kwarten, doe ze met de paddenstoelen (die hebben verschillende vormen en maten, dus bepaal zelf welke je snijdt, scheurt of heel laat) bij de bouillon, en kook ze niet langer dan 1 minuut, om de smaak lekker vers te houden. Verdeel groenten over de kommen, schep er de dampende bouillon over en serveer de soep met de pickles, lente-uitjes en een schepje sesamzaad." +
        "\n\nEen kneepje limoensap is ook erg lekker."
    );
    noodleSoup.setServings(4);
    recipeId = recipeService.save(noodleSoup).getId();

    List<RecipeIngredientDto> noodleSoupIngredients = List.of(
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(3).getId()),
        "4 tenen", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(11).getId()),
        "4 cm", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(12).getId()),
        "", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(13).getId()),
        "1", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(14).getId()),
        "1", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(15).getId()),
        "1 tl", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(16).getId()),
        "2", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(17).getId()),
        "2 volle el", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(18).getId()),
        "", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(19).getId()),
        "200 g", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(20).getId()),
        "2 stronkjes", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(1).getId()),
        "250g", ""
      ),
      new RecipeIngredientDto(
        new RecipeIngredientKey(recipeId, savedIngredients.get(21).getId()),
        "1 el", ""
      )
    );
    recipeIngredientService.saveAll(
      noodleSoupIngredients.stream().map(
        (it) -> modelMapper.map(it, RecipeIngredient.class)
      ).collect(Collectors.toList())
    );
  }
}
