package pl.coderslab.myFinance.controller;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.myFinance.model.Transaction;
import pl.coderslab.myFinance.service.TransactionService;
import pl.coderslab.myFinance.service.CategoryService;
import pl.coderslab.myFinance.service.UserService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listTransactions(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            List<Transaction> transactions = transactionService.findByUserId(userId);
            model.addAttribute("transactions", transactions);
        }
        return "transactions/list";
    }

    @GetMapping("/add")
    public String addTransactionForm(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("transaction", new Transaction());
            model.addAttribute("categories", categoryService.findByUserId(userId));
        }
        return "transactions/add";
    }

    @PostMapping("/add")
    public String addTransaction(@Valid @ModelAttribute Transaction transaction, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "transactions/add";
        }
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            transaction.setUser(userService.getUserById((long)userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId)));
            transactionService.save(transaction);
        }
        return "redirect:/transactions";
    }

    @GetMapping("/edit/{id}")
    public String editTransactionForm(@PathVariable Integer id, Model model, HttpSession session) {
        Transaction transaction = transactionService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction Id:" + id));
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("transaction", transaction);
            model.addAttribute("categories", categoryService.findByUserId(userId));
        }
        return "transactions/edit";
    }

    @PostMapping("/edit/{id}")
    public String editTransaction(@PathVariable Integer id, @Valid @ModelAttribute Transaction transaction, BindingResult result) {
        if (result.hasErrors()) {
            return "transactions/edit";
        }
        transaction.setId(id);
        transactionService.save(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteById(id);
        return "redirect:/transactions";
    }

    @GetMapping("/details/{id}")
    public String viewTransactionDetails(@PathVariable Integer id, Model model) {
        Transaction transaction = transactionService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction Id:" + id));
        model.addAttribute("transaction", transaction);
        return "transactions/details";
    }

    @GetMapping("/pdf")
    @ResponseBody
    public void generatePdf(HttpServletResponse response, HttpSession session) throws IOException {
        Integer userId = (Integer) session.getAttribute("userId");
        List<Transaction> transactions = transactionService.findByUserId(userId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        document.add(new Paragraph("Transactions List"));

        Table table = new Table(5);
        table.addCell("ID");
        table.addCell("Category");
        table.addCell("Amount");
        table.addCell("Date");
        table.addCell("Description");

        for (Transaction transaction : transactions) {
            table.addCell(transaction.getId().toString());
            table.addCell(transaction.getCategory().getName());
            table.addCell(transaction.getAmount().toString());
            table.addCell(transaction.getDate().toString());
            table.addCell(transaction.getDescription());
        }

        document.add(table);
        document.close();
    }
}