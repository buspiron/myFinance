package pl.coderslab.myFinance.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.myFinance.model.BudgetItem;
import pl.coderslab.myFinance.model.Report;
import pl.coderslab.myFinance.model.User;
import pl.coderslab.myFinance.model.UserReport;
import pl.coderslab.myFinance.service.BudgetItemService;
import pl.coderslab.myFinance.service.ReportService;
import pl.coderslab.myFinance.service.UserReportService;

import java.util.List;


@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private BudgetItemService budgetItemService;

    @Autowired
    private UserReportService userReportService;

    @GetMapping
    public String listReports(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("reports", reportService.findByUserId(userId));
        }
        return "reports/list";
    }

    @GetMapping("/add")
    public String addReportForm(Model model) {
        model.addAttribute("report", new Report());
        return "reports/add";
    }

    @PostMapping("/add")
    public String addReport(@Valid @ModelAttribute Report report, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "reports/add";
        }
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            User user = new User();
            user.setId(userId);
            report.setUser(user);
            reportService.save(report);
        }
        return "redirect:/reports";
    }

    @GetMapping("/edit/{id}")
    public String editReportForm(@PathVariable Integer id, Model model) {
        Report report = reportService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid report Id:" + id));
        model.addAttribute("report", report);
        return "reports/edit";
    }

    @PostMapping("/edit/{id}")
    public String editReport(@PathVariable Integer id, @Valid @ModelAttribute Report report, BindingResult result) {
        if (result.hasErrors()) {
            return "reports/edit";
        }
        report.setId(id);
        reportService.save(report);
        return "redirect:/reports";
    }

    @GetMapping("/delete/{id}")
    public String deleteReport(@PathVariable Integer id) {
        reportService.deleteById(id);
        return "redirect:/reports";
    }

    @GetMapping("/details/{id}")
    public String reportDetails(@PathVariable Integer id, Model model) {
        Report report = reportService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid report Id:" + id));

        List<UserReport> userReports = userReportService.findByReportId(id);

        model.addAttribute("report", report);
        model.addAttribute("userReports", userReports);

        return "reports/report-details";
    }
}