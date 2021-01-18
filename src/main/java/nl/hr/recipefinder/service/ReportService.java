package nl.hr.recipefinder.service;

import nl.hr.recipefinder.model.entity.Report;
import nl.hr.recipefinder.model.entity.ReportKey;
import nl.hr.recipefinder.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Optional<Report> findById(ReportKey id) {
        return reportRepository.findById(id);
    }

    public List<Report> findAllByUserId(Long userId) {
        return reportRepository.findAllByReportedUserId(userId);
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public void deleteAll(Iterable<Report> reports) {
        reportRepository.deleteAll(reports);
    }

    public Report save(Report report) {
        return reportRepository.save(report);
    }

}
