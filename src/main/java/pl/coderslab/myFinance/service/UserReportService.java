package pl.coderslab.myFinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.myFinance.model.UserReport;
import pl.coderslab.myFinance.model.UserReportId;
import pl.coderslab.myFinance.repository.UserReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserReportService {

    private final UserReportRepository userReportRepository;

    @Autowired
    public UserReportService(UserReportRepository userReportRepository) {
        this.userReportRepository = userReportRepository;
    }

    public List<UserReport> findAll() {
        return userReportRepository.findAll();
    }

    public Optional<UserReport> findById(UserReportId id) {
        return userReportRepository.findById(id);
    }

    public UserReport save(UserReport userReport) {
        return userReportRepository.save(userReport);
    }

    public void delete(UserReportId id) {
        userReportRepository.deleteById(id);
    }

    public List<UserReport> findByReportId(Integer reportId) {
        return userReportRepository.findByReportId(reportId);
    }
}