package pl.coderslab.myFinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.myFinance.model.ReportEntry;
import pl.coderslab.myFinance.repository.ReportEntryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReportEntryService {

    private final ReportEntryRepository reportEntryRepository;

    @Autowired
    public ReportEntryService(ReportEntryRepository reportEntryRepository) {
        this.reportEntryRepository = reportEntryRepository;
    }

    public List<ReportEntry> findByReportId(Integer reportId) {
        return reportEntryRepository.findByReportId(reportId);
    }

    public Optional<ReportEntry> findById(Integer id) {
        return reportEntryRepository.findById(id);
    }

    public ReportEntry save(ReportEntry reportEntry) {
        return reportEntryRepository.save(reportEntry);
    }

    public void deleteById(Integer id) {
        reportEntryRepository.deleteById(id);
    }
}