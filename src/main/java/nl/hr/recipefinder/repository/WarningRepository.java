package nl.hr.recipefinder.repository;

import nl.hr.recipefinder.model.entity.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarningRepository extends JpaRepository<Warning, Long> {
}

