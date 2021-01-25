package nl.hr.recipefinder.controller;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.dto.ReportRequestDto;
import nl.hr.recipefinder.model.dto.ReportResponseDto;
import nl.hr.recipefinder.model.entity.Recipe;
import nl.hr.recipefinder.model.entity.Report;
import nl.hr.recipefinder.model.entity.ReportKey;
import nl.hr.recipefinder.model.entity.User;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpConflictException;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpForbiddenException;
import nl.hr.recipefinder.model.httpexception.clienterror.HttpNotFoundException;
import nl.hr.recipefinder.model.httpexception.servererror.HttpInternalServerException;
import nl.hr.recipefinder.service.AuthenticationService;
import nl.hr.recipefinder.service.RecipeService;
import nl.hr.recipefinder.service.ReportService;
import nl.hr.recipefinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:4200",
  allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token", "authorization", "content-type", "accept"})
@RequestMapping("/report")
public class ReportController {
  private final ReportService reportService;
  private final UserService userService;
  private final RecipeService recipeService;
  private final AuthenticationService authenticationService;
  private final ModelMapper modelMapper;

  @GetMapping()
  public ResponseEntity<List<Report>> getAllReports() {
    List<Report> reports = reportService.findAll();
    return new ResponseEntity<>(reports, HttpStatus.OK);
  }

  @GetMapping("/allowed/{targetUserId}")
  public ResponseEntity<Boolean> isReportingAllowed(@PathVariable Long targetUserId) {
    Long currentUserId = authenticationService.getAuthenticatedUser().getId();

    ReportKey id = new ReportKey(currentUserId, targetUserId);
    Optional<Report> existingReport = reportService.findById(id);

    return new ResponseEntity<>(existingReport.isEmpty(), HttpStatus.OK);
  }

  @Transactional
  @PostMapping()
  public ResponseEntity<ReportResponseDto> createReport(@RequestBody ReportRequestDto reportRequestDto) {
    try {
      Optional<User> foundReporter = userService.findUserById(reportRequestDto.getReportingUserId());
      Optional<Recipe> foundRecipe = recipeService.findById(reportRequestDto.getReportedRecipeId());

      if (foundRecipe.isEmpty() || foundReporter.isEmpty()) throw new HttpNotFoundException();

      ReportKey id = new ReportKey(foundReporter.get().getId(), foundRecipe.get().getUser().getId());
      Optional<Report> existingReport = reportService.findById(id);

      if (existingReport.isPresent())
        throw new HttpForbiddenException("You have already reported the user");

      Report report = reportService.save(
        new Report(
          id,
          null, null, reportRequestDto.getMessage(),
          foundReporter.get(), foundRecipe.get().getUser(), foundRecipe.get()
        )
      );
      return new ResponseEntity<>(modelMapper.map(report, ReportResponseDto.class), HttpStatus.CREATED);
    } catch (DataIntegrityViolationException e) {
      throw new HttpConflictException(e);
    } catch (DataAccessException e) {
      throw new HttpInternalServerException(e);
    }
  }
}

