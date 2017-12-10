package my.onlineshop.controller;

import my.onlineshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private UserService userRepository;
    public LoginController(UserService userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String Login(@RequestParam(value = "reqEmail") String parEmail,
                        @RequestParam(value = "reqPassword") String parPassword,
                        ModelMap model){
        boolean isEmailExists = userRepository.existsByEmail(parEmail);
        boolean isPasswordCorrect = userRepository.existsByEmailAndPassword(parEmail, parPassword);
        model.addAttribute("errorEmail", isEmailExists?"":"Email tidak cocok dengan akun manapun.");
        model.addAttribute("errorPassword", isEmailExists && !isPasswordCorrect ?"":"Kata Sandi tidak cocok.");
        model.addAttribute("isLoginSuccess", isPasswordCorrect);
        return "redirect:/";
    }
}
