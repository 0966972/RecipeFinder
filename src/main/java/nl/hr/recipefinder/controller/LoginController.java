package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.UserDto;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.repository.UserRepository;
import nl.hr.recipefinder.security.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@CrossOrigin
public class LoginController {

  private UserRepository userRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  public LoginController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping("/login")
  public boolean login(@RequestBody UserDto userDto) {
    User mappedUser = modelMapper.map(userDto, User.class);
    User foundUser = userRepository.findUserByUsernameAndPassword(mappedUser.getUsername(), mappedUser.getPassword());
    if (foundUser != null) return true;
    return false;
  }
}
