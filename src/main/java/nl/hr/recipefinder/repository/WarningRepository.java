package nl.hr.recipefinder.repository;

import nl.hr.recipefinder.model.entity.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningRepository extends JpaRepository<Warning, Long> {
    List<Warning> findAllByWarnedUserId(Long warnedUserId);
}

