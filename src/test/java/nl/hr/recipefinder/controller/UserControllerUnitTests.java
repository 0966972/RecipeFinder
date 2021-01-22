package nl.hr.recipefinder.controller;

import nl.hr.recipefinder.RecipeFinderApplication;
import nl.hr.recipefinder.model.dto.UserRequestDto;
import nl.hr.recipefinder.model.dto.UserResponseDto;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictException;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundException;
import nl.hr.recipefinder.model.httpexception.servererror.HttpInternalServerException;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("unit-tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RecipeFinderApplication.class})
class UserControllerUnitTests {

  @Autowired
  @InjectMocks
  UserController userController;

  @MockBean
  UserService userService;

  @MockBean
  ModelMapper modelMapper;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void banUser_whenResourceNonExistent_thenReturnsNotFound() {
    // arrange
    long input = 5000;
    Mockito.when(userService.findUserById(input)).thenReturn(Optional.empty());

    try {
      // act
      userController.banUser(input);
    } catch (Exception e) {
      // assert
      assertThat(e).isInstanceOf(HttpNotFoundException.class);
    }
    verify(userService, times(1)).findUserById(input);
  }

  @Test
  void banUser_whenResourceExists_thenReturnsTrue() {
    // arrange
    long input = 0;
    Optional<User> expectedOptional = Optional.of(new User("a", "b", Role.USER));
    Mockito.when(userService.findUserById(input)).thenReturn(expectedOptional);

    // act
    ResponseEntity<Boolean> result = userController.banUser(input);

    // assert
    assertThat(result.getBody()).isTrue();
    verify(userService, times(1)).findUserById(input);
  }


  @Test
  void getUser_whenResourceNonExistent_thenReturnsNotFound() {
    // arrange
    long input = 5000;
    Mockito.when(userService.findUserById(input)).thenReturn(Optional.empty());

    try {
      // act
      userController.getUser(input);
    } catch (Exception e) {
      // assert
      assertThat(e).isInstanceOf(HttpNotFoundException.class);
    }
    verify(userService, times(1)).findUserById(input);
  }

  @Test
  void getUser_whenResourceExists_thenReturnsUser() {
    // arrange
    long input = 0;
    Optional<User> expectedOptional = Optional.of(new User("a", "b", Role.USER));
    UserResponseDto expectedDto = new UserResponseDto(input, "a", Role.USER);
    Mockito.when(userService.findUserById(input)).thenReturn(expectedOptional);
    Mockito.when(modelMapper.map(expectedOptional.get(), UserResponseDto.class)).thenReturn(expectedDto);

    // act
    ResponseEntity<UserResponseDto> result = userController.getUser(input);

    // assert
    assertThat(result.getBody().getId()).isEqualTo(input);
    verify(userService, times(1)).findUserById(input);
    verify(modelMapper, times(1)).map(expectedOptional.get(), UserResponseDto.class);
  }

  @Test
  void getUsers_whenResourceExists_thenReturnsUsers() {
    // arrange
    long id0 = 0;
    long id1 = 1;
    User user1 = new User("a", "a", Role.USER);
    user1.setId(id0);
    User user2 = new User("b", "b", Role.USER);
    user2.setId(id1);

    List<User> users = new ArrayList<>();
    users.add(user1);
    users.add(user2);
    List<UserResponseDto> userDtos = new ArrayList<>();
    for (User user : users) {
      userDtos.add(new UserResponseDto(user.getId(), user.getUsername(), user.getRole()));
    }
    Mockito.when(userService.findAll()).thenReturn(users);
    Mockito.when(modelMapper.map(users.get(0), UserResponseDto.class)).thenReturn(userDtos.get(0));
    Mockito.when(modelMapper.map(users.get(1), UserResponseDto.class)).thenReturn(userDtos.get(1));

    // act
    ResponseEntity<List<UserResponseDto>> result = userController.getUsers();

    // assert
    assertThat(result.getBody().get(0)).isInstanceOf(UserResponseDto.class);
    verify(userService, times(1)).findAll();
    verify(modelMapper, times(1)).map(users.get(0), UserResponseDto.class);
    verify(modelMapper, times(1)).map(users.get(1), UserResponseDto.class);
  }

  @Test
  void getUsers_whenResourceNonExistent_thenReturnsEmptyList() {
    // arrange
    List<User> users = new ArrayList<>();
    Mockito.when(userService.findAll()).thenReturn(users);

    // act
    ResponseEntity<List<UserResponseDto>> result = userController.getUsers();

    // assert
    assertThat(result.getBody()).isEmpty();
    verify(userService, times(1)).findAll();
    verifyNoInteractions(modelMapper);
  }

  @Test
  void getUsers_whenRepositoryUnavailable_thenThrowsInternalServerError() {
    // arrange
    Mockito.when(userService.findAll()).thenThrow(new RuntimeException());
    try {
      // act
      userController.getUsers();
    } catch (Exception e) {
      // assert
      assertThat(e).isInstanceOf(HttpInternalServerException.class);
    }
    verifyNoInteractions(modelMapper);
  }

  @Test
  void createUser_whenUsernameAlreadyTaken_thenThrowsConflictError() {
    // arrange
    User duplicateUser = new User("a", "a", Role.USER);
    UserRequestDto userDto = new UserRequestDto("a", "a", Role.USER);
    Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(duplicateUser);
    doThrow(DataIntegrityViolationException.class).when(userService).save(duplicateUser);
    try {
      // act
      userController.createUser(userDto);
    } catch (Exception e) {
      // assert
      assertThat(e).isInstanceOf(HttpConflictException.class);
    }
    verify(userService, times(1)).save(duplicateUser);
    verify(modelMapper, times(1)).map(userDto, User.class);
  }

  @Test
  void createUser_whenRepositoryUnavailable_thenThrowsInternalServerError() {
    // arrange
    User duplicateUser = new User("a", "a", Role.USER);
    UserRequestDto userDto = new UserRequestDto("a", "a", Role.USER);
    Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(duplicateUser);
    doThrow(HttpInternalServerException.class).when(userService).save(duplicateUser);
    try {
      // act
      userController.createUser(userDto);
    } catch (Exception e) {
      // assert
      assertThat(e).isInstanceOf(HttpInternalServerException.class);
    }
    verify(userService, times(1)).save(duplicateUser);
    verify(modelMapper, times(1)).map(userDto, User.class);
  }
}
