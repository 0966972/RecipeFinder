package nl.hr.recipefinder;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("nl.hr.recipefinder")
@EntityScan("nl.hr.recipefinder")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RecipeFinderApplication {

  public static void main(String[] args) {
    SpringApplication.run(RecipeFinderApplication.class, args);
  }
}


