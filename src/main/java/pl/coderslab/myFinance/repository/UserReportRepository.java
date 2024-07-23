package pl.coderslab.myFinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.myFinance.model.UserReport;
import pl.coderslab.myFinance.model.UserReportId;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, UserReportId> {
    List<UserReport> findByReportId(Integer reportId);
}