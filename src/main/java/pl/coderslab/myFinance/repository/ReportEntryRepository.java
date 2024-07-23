package pl.coderslab.myFinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.myFinance.model.ReportEntry;

import java.util.List;


@Repository
public interface ReportEntryRepository extends JpaRepository<ReportEntry, Integer> {

    List<ReportEntry> findByReportId(Integer reportId);
}
