package nl.hr.recipefinder;

import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.RecipeService;
import nl.hr.recipefinder.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by maartendegoede on 07/12/2020.
 * Copyright © 2020 Maarten de Goede. All rights reserved.
 */
@Component
public class DataLoader implements ApplicationRunner {
  private final UserService userService;
  private final RecipeService recipeService;
  private final PasswordEncoder passwordEncoder;


  public DataLoader(
    UserService userService,
    RecipeService recipeService,
    PasswordEncoder passwordEncoder
  ) {
    this.userService = userService;
    this.recipeService = recipeService;
    this.passwordEncoder = passwordEncoder;
  }


  @Override
  public void run(ApplicationArguments args) {
    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword(passwordEncoder.encode("admin"));
    admin.setRole(Role.ADMIN);
    userService.save(admin);


    Recipe mushroomStroganoff = new Recipe();
    mushroomStroganoff.setName("Paddenstoelen Stroganoff");
    mushroomStroganoff.setDescription("Crunchy Cornichons, Geurige Kapptertjes, Romige Whiskysaus & Peterselie");
    mushroomStroganoff.setInstructions(
      "Bereid om te beginnen alle ingrediënten voor: maak de paddenstoelen schoon en scheur de grotere in stukjes. Pel de rode ui en knoflook, en snijd ze in dunne ringen en plakjes. Snijd de zilveruitjes en cornichons flinterdun. Pluk en snipper de peterselieblaadjes, en snijd de steeltjes fijn. " +
        "\n\nZet een grote, droge koekenpan met antiaanbaklaag met e paddenstoelen en rode ui op hoog vuur, schud de pan om ze uit te spreiden en bak ze 5 minuten terwijl je regelmatig roert (hierdoor komt de nootachtige smaak goed los). Sprenkel er  1 eetlepel olie over en doe de knoflook, zilveruitjes, cornichons, peterseliesteeltjes, en kappertjes erbij. Schenk na 3 minuten de whisky in de pan, kantel hem voorzichtig om de vlam in de pan te laten slaan, of steek de alcohol voorzichtig aan met een lucifer (pas op je wenkbrauwen!). Voeg nadat de vlammen gedoofd zijn ¼ eetlepel paprikapoeder, de crème fraîche en peterselie toe, en meng alles goed. Giet er een scheutje kokend water bij om het paddenstoelenmengsel een mooie, sausachtige consistentie te geven en voeg naar smaak zeezout en zwarte peper toe. " +
        "\n\nVerdeel de stroganoff over de borden, strooi er een snufje paprikapoeder op en geef er luchtige rijst bij."
    );
    mushroomStroganoff.setServings(2);
    recipeService.save(mushroomStroganoff);


    Recipe noodleSoup = new Recipe();
    noodleSoup.setName("Vlugge Noedelsoep met Paddenstoelen");
    noodleSoup.setDescription("Vlugge wortel-Gemberpickle, lente-uitjes & sesamstrooisel");
    noodleSoup.setInstructions(
      "Pel en schil de knoflook en gember, snijd ze in dunne plakjes en doe ze met 1 eetlepel olie in een grote, zware braadpan op hoog vuur. Bak ze 2 minuten, voeg het eekhoorntjesbrood en 1,5 liter kokend water toe, leg een deksel op de pan en laat 10 minuten op laag vuur koken. Boek intussen de wortel, rasp hem met de rode peper op een grove rasp en meng er de sushigember door. Maak de lente-uitjes schoon, snijd ze in dunne ringen en zet ze opzij." +
        "\n\nRoer wanneer de tijd erop zit de misopasta en 2 eetlepels sojasaus door de bouillon. Kook de eiernoedels volgens de aanwijzingen op de verpakking en verdeel ze over view warme kommen. Breng de bouillon op smaak met sojasaus en zwarte peper. Snijd de stronkjes paksoi door midden of in kwarten, doe ze met de paddenstoelen (die hebben verschillende vormen en maten, dus bepaal zelf welke je snijdt, scheurt of heel laat) bij de bouillon, en kook ze niet langer dan 1 minuut, om de smaak lekker vers te houden. Verdeel groenten over de kommen, schep er de dampende bouillon over en serveer de soep met de pickles, lente-uitjes en een schepje sesamzaad." +
        "\n\nEen kneepje limoensap is ook erg lekker."
    );
    noodleSoup.setServings(4);
    recipeService.save(noodleSoup);
  }
}
