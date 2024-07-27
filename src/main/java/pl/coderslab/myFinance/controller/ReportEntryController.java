package pl.coderslab.myFinance.controller;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.myFinance.model.Report;
import pl.coderslab.myFinance.model.ReportEntry;
import pl.coderslab.myFinance.service.ReportEntryService;
import pl.coderslab.myFinance.service.ReportService;
import pl.coderslab.myFinance.service.CategoryService;
import pl.coderslab.myFinance.service.TransactionService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/report-entries")
public class ReportEntryController {

    private final ReportEntryService reportEntryService;
    private final ReportService reportService;
    private final CategoryService categoryService;
    private final TransactionService transactionService;

    @Autowired
    public ReportEntryController(ReportEntryService reportEntryService, ReportService reportService, CategoryService categoryService, TransactionService transactionService) {
        this.reportEntryService = reportEntryService;
        this.reportService = reportService;
        this.categoryService = categoryService;
        this.transactionService = transactionService;
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
        model.addAttribute("transactions", transactionService.findAll());
        model.addAttribute("reportId", reportId);
        return "report-entries/add";
    }

    @PostMapping("/add")
    public String addReportEntry(@RequestParam("reportId") Integer reportId, @Valid @ModelAttribute ReportEntry reportEntry, RedirectAttributes redirectAttributes) {
        if (reportId == null) {
            throw new IllegalArgumentException("Report ID must not be null");
        }
        Report report = reportService.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid report ID"));
        reportEntry.setReport(report);
        reportEntryService.save(reportEntry);
        redirectAttributes.addAttribute("reportId", reportId);
        return "redirect:/report-entries";
    }

    @GetMapping("/edit/{id}")
    public String editReportEntryForm(@PathVariable Integer id, Model model) {
        ReportEntry reportEntry = reportEntryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid report entry ID:" + id));
        model.addAttribute("reportEntry", reportEntry);
        model.addAttribute("reports", reportService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("transactions", transactionService.findAll());
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

    @GetMapping("/pdf")
    @ResponseBody
    public void generatePdf(@RequestParam("reportId") Integer reportId, HttpServletResponse response) throws IOException {
        List<ReportEntry> entries = reportEntryService.findByReportId(reportId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        document.add(new Paragraph("Entries List"));

        Table table = new Table(4);
        table.addCell("ID");
        table.addCell("Category");
        table.addCell("Amount");
        table.addCell("Description");

        for (ReportEntry entry : entries) {
            table.addCell(entry.getId().toString());
            table.addCell(entry.getCategory().getName());
            table.addCell(entry.getAmount().toString());
            table.addCell(entry.getDescription() != null ? entry.getDescription() : "");
        }

        document.add(table);
        document.close();
    }
}