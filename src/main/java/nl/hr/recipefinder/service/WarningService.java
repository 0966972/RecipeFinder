package nl.hr.recipefinder.service;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.model.entity.Warning;
import nl.hr.recipefinder.repository.WarningRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarningService {
    private final WarningRepository warningRepository;

    public Optional<Warning> findById(Long id) {
        return warningRepository.findById(id);
    }

    public List<Warning> findAll() {
        return warningRepository.findAll();
    }

    public void deleteAll(Iterable<Warning> warnings) {
        warningRepository.deleteAll(warnings);
    }

    public Warning save(Warning warning) {
        return warningRepository.save(warning);
    }

}
