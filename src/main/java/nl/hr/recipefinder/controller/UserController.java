package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.model.dto.UserRequestDto;
import nl.hr.recipefinder.model.dto.UserResponseDto;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictError;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundError;
import nl.hr.recipefinder.model.httpexception.serverError.HttpInternalServerError;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/user")
public class UserController {

  private final UserService userService;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
    try {
      Optional<User> foundUser = userService.findUserById(id);

      if (foundUser.isPresent()) return ResponseEntity.ok(modelMapper.map(foundUser.get(), UserResponseDto.class));

      throw new HttpNotFoundError();
    } catch (Exception e) {
      throw new HttpNotFoundError();
    }
  }

  @GetMapping()
  public ResponseEntity<List<UserResponseDto>> getUsers() {
    try {
      List<User> users = userService.findAll();
      List<UserResponseDto> userDtos = new ArrayList<>();
      for (int i = 0; i < users.size(); i++) {
        userDtos.add(modelMapper.map(users.get(i), UserResponseDto.class));
      }
      return new ResponseEntity<>(userDtos, HttpStatus.OK);
    } catch (Exception e) {
      throw new HttpInternalServerError();
    }
  }

  @PostMapping()
  public ResponseEntity<User> createUser(@RequestBody UserRequestDto userRequestDto) {
    User mappedUser = modelMapper.map(userRequestDto, User.class);
    try {
      if (mappedUser.getRole() == null) mappedUser.setRole(Role.USER);

      mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
      userService.save(mappedUser);

      return new ResponseEntity<>(mappedUser, HttpStatus.CREATED);
    } catch (DataIntegrityViolationException e) {
      throw new HttpConflictError();
    } catch (DataAccessException e) {
      throw new HttpInternalServerError();
    }
  }
}
