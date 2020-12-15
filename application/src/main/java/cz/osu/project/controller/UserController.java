package cz.osu.project.controller;

import cz.osu.project.database.entity.User;
import cz.osu.project.exception.NotFoundException;
import cz.osu.project.exception.UserErrorException;
import cz.osu.project.service.SecurityServiceImpl;
import cz.osu.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityServiceImpl securityService;

    @GetMapping("/register")
    public String getRegister(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String postRegistration(Model model,
                               @RequestParam(name="email", required=true)String email,
                               @RequestParam(name="fname", required=true)String fname,
                               @RequestParam(name="lname", required=true)String lname,
                               @RequestParam(name="password", required=true)String password,
                               @RequestParam(name="passwordAgain", required=true)String passwordAgain) {

        try {
            User user = userService.create(email, fname, lname, password, passwordAgain);
            //securityService.autoLogin(user.getEmail(), user.getPassword());
        }
        catch (UserErrorException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("email", email);
            model.addAttribute("fname", fname);
            model.addAttribute("lname", lname);

            return "register";
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Neplatně zadané přihlašovací údaje!");
        if (logout != null)
            model.addAttribute("message", "Úspěšné odhlášení");

        return "login";
    }

    @GetMapping("/profile")
    public String getProfile(Authentication authentication, Model model) {
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile")
    public String postProfile(Authentication authentication, Model model,
                              @RequestParam(name="choice", required=true)String choice,
                              @RequestParam(name="fname", required=false)String fname,
                              @RequestParam(name="lname", required=false)String lname,
                              @RequestParam(name="currentPassword", required=false)String currentPassword,
                              @RequestParam(name="newPassword", required=false)String newPassword,
                              @RequestParam(name="passwordAgain", required=false)String passwordAgain) {
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", user);

        try {
            if(choice.equals("1")) {
                user.setFname(fname.trim());
                user.setLname(lname.trim());

                userService.save(user);
            }
            else if(choice.equals("2")) {
                userService.changePassword(user, currentPassword, newPassword.trim(), passwordAgain.trim());
            }
        }
        catch(UserErrorException e) {
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "profile";
    }

    @ExceptionHandler(value = NotFoundException.class)
    public String NotFoundException(NotFoundException e, Model model) {
        return "error";
    }

    @ExceptionHandler(value = Exception.class)
    public String basicException(NotFoundException e, Model model) {
        return "error";
    }
}
