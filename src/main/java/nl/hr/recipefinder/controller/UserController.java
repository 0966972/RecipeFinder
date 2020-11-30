package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.repository.UserRepository;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }

  @RequestMapping("/user")
  public User getUser(HttpServletRequest request){
    String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
    String[] credentials = new String(Base64.getDecoder().decode(authToken)).split(":");

    User foundUser = userService.findUserByUsernameAndPassword(credentials[0], credentials[1]);
    return foundUser;
  }

  @GetMapping("/users")
  public List<User> getUsers() {
      return userService.findAll();
  }

  @PostMapping("/users")
  public boolean createUser(@RequestBody User user) {
    try {
      if (user.getRole() == null) user.setRole(Role.USER);
      userService.save(user);
      return true;
    }
    catch (Exception e){
      return false;
    }
  }
}
