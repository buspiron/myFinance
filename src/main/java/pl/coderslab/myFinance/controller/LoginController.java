package pl.coderslab.myFinance.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.myFinance.model.CustomUserDetails;
import pl.coderslab.myFinance.model.User;
import pl.coderslab.myFinance.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showLoginForm(@RequestParam(value = "errorMessage", required = false)String errorMessage, Model model) {
        if(errorMessage != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "login";
    }

//    @GetMapping("/loginSuccess")
//    public String postLogin(HttpSession session) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
//
//        Integer userId = userService.getUserByUsername(userDetails.getUsername())
//                .map(User::getId)
//                .orElse(0);
//
//        session.setAttribute("userId", userId);
//        return "redirect:/welcome";
//    }
}
