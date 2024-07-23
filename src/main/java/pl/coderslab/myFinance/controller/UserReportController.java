package pl.coderslab.myFinance.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.myFinance.model.UserReport;
import pl.coderslab.myFinance.model.UserReportId;
import pl.coderslab.myFinance.service.UserReportService;
import pl.coderslab.myFinance.service.UserService;
import pl.coderslab.myFinance.service.ReportService;


@Controller
@RequestMapping("/user-reports")
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String listUserReports(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("userReports", userReportService.findAll());
        }
        return "user-reports/list";
    }

    @GetMapping("/add")
    public String addUserReportForm(Model model) {
        model.addAttribute("userReport", new UserReport());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("reports", reportService.findAll());
        return "user-reports/add";
    }

    @PostMapping("/add")
    public String addUserReport(@ModelAttribute UserReport userReport) {
        userReportService.save(userReport);
        return "redirect:/user-reports";
    }

    @GetMapping("/edit/{userId}/{reportId}")
    public String editUserReportForm(@PathVariable Integer userId, @PathVariable Integer reportId, Model model) {
        UserReportId id = new UserReportId(userId, reportId);
        UserReport userReport = userReportService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid userReport Id:" + id));
        model.addAttribute("userReport", userReport);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("reports", reportService.findAll());
        return "user-reports/edit";
    }

    @PostMapping("/edit/{userId}/{reportId}")
    public String editUserReport(@PathVariable Integer userId, @PathVariable Integer reportId, @ModelAttribute UserReport userReport) {
        UserReportId id = new UserReportId(userId, reportId);
        userReport.setUser(userService.getUserById((long)userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId)));
        userReport.setReport(reportService.findById(reportId).orElseThrow(() -> new IllegalArgumentException("Invalid report Id:" + reportId)));
        userReportService.save(userReport);
        return "redirect:/user-reports";
    }

    @GetMapping("/delete/{userId}/{reportId}")
    public String deleteUserReport(@PathVariable Integer userId, @PathVariable Integer reportId) {
        UserReportId id = new UserReportId(userId, reportId);
        userReportService.delete(id);
        return "redirect:/user-reports";
    }
}