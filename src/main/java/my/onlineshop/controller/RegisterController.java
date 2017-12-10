package my.onlineshop.controller;
import my.onlineshop.library.Mail;
import my.onlineshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import my.onlineshop.library.Validate;
import my.onlineshop.model.UserModel;

import javax.mail.MessagingException;
import java.io.IOException;

@Controller
public class RegisterController {

    // INIT
    private UserService userRepository;
    private Validate validator;
    private Mail mailSender;
    public RegisterController(UserService userRepository){
        this.userRepository = userRepository;
        this.validator = new Validate(userRepository);
        this.mailSender = new Mail();
    }

    // PROCESS REGISTER
    @PostMapping("/register")
    public String Register(@RequestParam(value = "reqEmail") String parEmail,
                           @RequestParam(value = "reqName") String parName,
                           @RequestParam(value = "reqPassword") String parPassword,
                           ModelMap model) throws MessagingException, IOException {

        // DATA VALIDATION
        String validEmail = validator.Email(parEmail);
        String validName = validator.Name(parName);
        String validPassword = validator.Password(parPassword);
        boolean validRegister = (validEmail == null &&
                                validName == null &&
                                validPassword == null);

        // SEND REPORT
        model.addAttribute("errorEmail", validEmail);
        model.addAttribute("errorPassword", validPassword);
        model.addAttribute("errorName", validName);

        if(validRegister){
            String newToken = validator.GenerateRandomToken();
            UserModel newUser = new UserModel(parEmail, parName, parPassword, newToken);
            userRepository.save(newUser);
            return "redirect:activation?email=" + parEmail;
        }else{
            model.addAttribute("currEmail", parEmail);
            model.addAttribute("currPassword", parPassword);
            model.addAttribute("currName", parName);
            return "register";
        }
    }

    // SEND AND RESEND EMAIL ACTIVATION LINK
    @GetMapping("/activation")
    public String SendEmail(@RequestParam(value = "email") String parEmail,
                              @RequestParam(value = "resend", required = false) Boolean parResend,
                              ModelMap model) throws IOException, MessagingException {

        boolean isExist = userRepository.existsByEmail(parEmail);
        if(isExist){
            UserModel currUser = userRepository.getByEmail(parEmail);
            mailSender.sendVerifyMail(parEmail, currUser.getName(), currUser.getToken());
            model.addAttribute("resend", parResend);
            model.addAttribute("currEmail", parEmail);
            return "activation";
        }else{
            return "redirect:register";
        }
    }

    // VERIFY AND ACTIVATE NEW USER
    @GetMapping("/verify")
    public String Verify(@RequestParam(value="email") String parEmail,
                            @RequestParam(value="token") String parToken,
                            ModelMap model){
        boolean isExist = userRepository.existsByEmailAndToken(parEmail, parToken);
        if(isExist){
            UserModel user = userRepository.getByEmailAndToken(parEmail, parToken);
            user.setStatus(true);
            userRepository.save(user);
            model.addAttribute("isSuccess", true);
        }else{
            model.addAttribute("isSuccess", false);
        }
        return "verify";
    }

    @GetMapping("/register")
    public String View(){
        return "register";
    }
}
