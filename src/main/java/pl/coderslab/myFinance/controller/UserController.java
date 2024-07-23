package pl.coderslab.myFinance.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.myFinance.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ResponseBody
    public String showUser(@PathVariable Long id){
        return userService.getUserById(id)
                .flatMap(user -> Optional.of(user.toString()))
                .orElse(String.format("User with id: %d not found!", id));

    }

    @GetMapping("/name/{username}")
    @ResponseBody
    public String showUsersByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .flatMap(user -> Optional.of(user.toString()))
                .orElse(String.format("User with id: %s not found!", username));
    }

}
