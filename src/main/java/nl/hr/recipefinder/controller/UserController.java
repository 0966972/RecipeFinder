package nl.hr.recipefinder.controller;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.dto.UserRequestDto;
import nl.hr.recipefinder.model.dto.UserResponseDto;
import nl.hr.recipefinder.model.entity.FavoritesList;
import nl.hr.recipefinder.model.entity.Report;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.entity.Warning;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictException;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundException;
import nl.hr.recipefinder.model.httpexception.servererror.HttpInternalServerException;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.FavoritesListService;
import nl.hr.recipefinder.service.ReportService;
import nl.hr.recipefinder.service.UserService;
import nl.hr.recipefinder.service.WarningService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:4200",
        allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ReportService reportService;
    private final WarningService warningService;
    private final FavoritesListService favoritesListService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        try {
            Optional<User> foundUser = userService.findUserById(id);

            if (foundUser.isPresent())
                return ResponseEntity.ok(modelMapper.map(foundUser.get(), UserResponseDto.class));

            throw new HttpNotFoundException();
        } catch (Exception e) {
            throw new HttpNotFoundException(e);
        }
    }

    @Transactional
    @PatchMapping("/ban/{id}")
    public ResponseEntity<Boolean> banUser(@PathVariable Long id) {
        try {
            Optional<User> foundUser = userService.findUserById(id);

            if (foundUser.isPresent()) {
                User user = foundUser.get();
                user.setRole(Role.BANNED);
                userService.save(user);

                Iterable<Report> reports = reportService.findAllByUserId(user.getId());
                reportService.deleteAll(reports);

                Iterable<Warning> warnings = warningService.findAllByUserId(user.getId());
                warningService.deleteAll(warnings);

                return new ResponseEntity<>(true, HttpStatus.OK);
            }

            throw new HttpNotFoundException();
        } catch (Exception e) {
            throw new HttpNotFoundException(e);
        }
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        try {
            List<User> users = userService.findAll();
            List<UserResponseDto> userDTOs = new ArrayList<>();
            for (User user : users) {
                userDTOs.add(modelMapper.map(user, UserResponseDto.class));
            }
            return new ResponseEntity<>(userDTOs, HttpStatus.OK);
        } catch (Exception e) {
            throw new HttpInternalServerException(e);
        }
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto userRequestDto) {
        User mappedUser = modelMapper.map(userRequestDto, User.class);
        try {
            if (mappedUser.getRole() == null) mappedUser.setRole(Role.USER);

            mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
            User savedUser = userService.save(mappedUser);
            FavoritesList defaultFavoritesList = new FavoritesList(mappedUser.getUsername() + "'s favorieten", "Een lijst van mijn favoriete recepten.", savedUser, new ArrayList<>());
            favoritesListService.save(defaultFavoritesList);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new HttpConflictException(e);
        } catch (DataAccessException e) {
            throw new HttpInternalServerException(e);
        }
    }
}
