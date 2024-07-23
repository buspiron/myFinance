package pl.coderslab.myFinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.myFinance.model.Report;
import pl.coderslab.myFinance.repository.ReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Optional<Report> findById(Integer reportId) {
        return reportRepository.findById(reportId);
    }

    public List<Report> findByUserId(Integer userId) {
        return reportRepository.findByUserId(userId);
    }

    public Report save(Report report) {
        return reportRepository.save(report);
    }

    public void deleteById(Integer reportId) {
        reportRepository.deleteById(reportId);
    }

    public Report updateReport(Report updatedReport) {
        Optional<Report> existingReportOpt = reportRepository.findById(updatedReport.getId());

        if (existingReportOpt.isPresent()) {
            Report existingReport = existingReportOpt.get();

            existingReport.setName(updatedReport.getName());
            existingReport.setCreationDate(updatedReport.getCreationDate());
            existingReport.setUser(updatedReport.getUser());

            return reportRepository.save(existingReport);
        } else {
            throw new IllegalArgumentException("Report not found with id: " + updatedReport.getId());
        }
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}