package pl.coderslab.myFinance.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.myFinance.model.User;
import pl.coderslab.myFinance.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "register";
        } if (userService.isUsernameOrEmailExists(user.getUsername(), user.getEmail())) {
            model.addAttribute("errorMessage", "Username or email already exists.");
            return "register";
        } else {
            userService.saveUser(user);
            model.addAttribute("successMessage", "User registered successfully. Moving to login.");
            return "register";
        }
    }

}
