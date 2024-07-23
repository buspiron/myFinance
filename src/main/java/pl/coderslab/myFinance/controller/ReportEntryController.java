package pl.coderslab.myFinance.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.myFinance.model.ReportEntry;
import pl.coderslab.myFinance.service.ReportEntryService;
import pl.coderslab.myFinance.service.ReportService;
import pl.coderslab.myFinance.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/report-entries")
public class ReportEntryController {

    private final ReportEntryService reportEntryService;
    private final ReportService reportService;
    private final CategoryService categoryService;

    @Autowired
    public ReportEntryController(ReportEntryService reportEntryService, ReportService reportService, CategoryService categoryService) {
        this.reportEntryService = reportEntryService;
        this.reportService = reportService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public String listReportEntries(@PathVariable Integer id, Model model) {
        List<ReportEntry> entries = reportEntryService.findByReportId(id);
        model.addAttribute("entries", entries);
        model.addAttribute("reportId", id);
        return "report-entries/list";
    }

    @GetMapping("/add")
    public String addReportEntryForm(@RequestParam("reportId") Integer reportId, Model model) {
        model.addAttribute("reportEntry", new ReportEntry());
        model.addAttribute("reports", reportService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("reportId", reportId);
        return "report-entries/add";
    }

    @PostMapping("/add")
    public String addReportEntry(@Valid @ModelAttribute ReportEntry reportEntry, RedirectAttributes redirectAttributes) {
        reportEntryService.save(reportEntry);
        redirectAttributes.addAttribute("reportId", reportEntry.getReport().getId());
        return "redirect:/report-entries";
    }

    @GetMapping("/edit/{id}")
    public String editReportEntryForm(@PathVariable Integer id, Model model) {
        ReportEntry reportEntry = reportEntryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid report entry ID:" + id));
        model.addAttribute("reportEntry", reportEntry);
        model.addAttribute("reports", reportService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "report-entries/edit";
    }

    @PostMapping("/edit/{id}")
    public String editReportEntry(@PathVariable Integer id, @Valid @ModelAttribute ReportEntry reportEntry, RedirectAttributes redirectAttributes) {
        reportEntry.setId(id);
        reportEntryService.save(reportEntry);
        redirectAttributes.addAttribute("reportId", reportEntry.getReport().getId());
        return "redirect:/report-entries";
    }

    @GetMapping("/delete/{id}")
    public String deleteReportEntry(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        ReportEntry reportEntry = reportEntryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid report entry ID:" + id));
        Integer reportId = reportEntry.getReport().getId();
        reportEntryService.deleteById(id);
        redirectAttributes.addAttribute("reportId", reportId);
        return "redirect:/report-entries";
    }
}
