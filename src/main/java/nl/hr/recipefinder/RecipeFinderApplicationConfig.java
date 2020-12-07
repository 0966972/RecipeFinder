package nl.hr.recipefinder;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeFinderApplicationConfig {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
