package nl.hr.recipefinder.controller;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.dto.WarningRequestDto;
import nl.hr.recipefinder.model.dto.WarningResponseDto;
import nl.hr.recipefinder.model.entity.Report;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.entity.Warning;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictException;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundException;
import nl.hr.recipefinder.model.httpexception.servererror.HttpInternalServerException;
import nl.hr.recipefinder.service.AuthenticationService;
import nl.hr.recipefinder.service.ReportService;
import nl.hr.recipefinder.service.UserService;
import nl.hr.recipefinder.service.WarningService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:4200",
        allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/warning")
public class WarningController {
    private final WarningService warningService;
    private final ReportService reportService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<List<Warning>> getAllWarnings() {
        List<Warning> warnings = warningService.findAll();
        return new ResponseEntity<>(warnings, HttpStatus.OK);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<List<WarningResponseDto>> getAllWarningsForCurrentUser() {
        Long currentUserId = authenticationService.getAuthenticatedUser().getId();
        List<Warning> warnings = warningService.findAllByUserId(currentUserId);

        List<WarningResponseDto> mappedWarnings = warnings.stream()
                .map(it -> modelMapper.map(warnings, WarningResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedWarnings, HttpStatus.OK);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<WarningResponseDto> createWarning(@RequestBody WarningRequestDto warningRequestDto) {
        try {
            Optional<User> warnedUser = userService.findUserById(warningRequestDto.getWarnedUserId());

            if (warnedUser.isEmpty()) throw new HttpNotFoundException();

            Iterable<Report> reports = reportService.findAllByUserId(warnedUser.get().getId());
            reportService.deleteAll(reports);

            Warning warning = warningService.save(
                    new Warning(warningRequestDto.getMessage(), warnedUser.get())
            );
            return new ResponseEntity<>(modelMapper.map(warning, WarningResponseDto.class), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new HttpConflictException(e);
        } catch (DataAccessException e) {
            throw new HttpInternalServerException(e);
        }
    }
}

