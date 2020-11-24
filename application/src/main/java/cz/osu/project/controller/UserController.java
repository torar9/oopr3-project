package cz.osu.project.controller;

import cz.osu.project.database.entity.User;
import cz.osu.project.service.SecurityServiceImpl;
import cz.osu.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            User user =userService.create(email, fname, lname, password, passwordAgain);
            //securityService.autoLogin(user.getEmail(), user.getPassword());
        }
        catch (Exception e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("email", email);
            model.addAttribute("fname", fname);
            model.addAttribute("lname", lname);

            return "register";
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
}
