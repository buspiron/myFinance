package pl.coderslab.myFinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.myFinance.model.User;
import pl.coderslab.myFinance.model.UserProfile;
import pl.coderslab.myFinance.service.UserProfileService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public String viewProfile(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            Optional<UserProfile> userProfileOpt = userProfileService.findById(userId);
            if (userProfileOpt.isPresent()) {
                model.addAttribute("userProfile", userProfileOpt.get());
            } else {
                model.addAttribute("errorMessage", "Profile not found.");
            }
        } else {
            return "redirect:/welcome";
        }
        return "profile/view";
    }

    @GetMapping("/edit")
    public String editProfile(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            Optional<UserProfile> userProfileOpt = userProfileService.findById(userId);
            if (userProfileOpt.isPresent()) {
                model.addAttribute("userProfile", userProfileOpt.get());
            } else {
                model.addAttribute("errorMessage", "Profile not found.");
                return "redirect:/profile";
            }
        } else {
            return "redirect:/welcome";
        }
        return "profile/edit";
    }


    @PostMapping("/edit")
    public String updateProfile(@Valid @ModelAttribute UserProfile userProfile, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "profile/edit";
        }
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            Optional<UserProfile> existingProfileOpt = userProfileService.findById(userId);
            if (existingProfileOpt.isPresent()) {
                UserProfile existingProfile = existingProfileOpt.get();
                userProfile.setProfileId(existingProfile.getProfileId());

                User user = new User();
                user.setId(userId);
                userProfile.setUser(user);

                userProfileService.editUserProfile(userProfile);
            } else {
                return "redirect:/profile?error=notfound";
            }
        }
        return "redirect:/profile";
    }
}