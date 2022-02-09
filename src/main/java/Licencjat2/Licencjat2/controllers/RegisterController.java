package Licencjat2.Licencjat2.controllers;

import Licencjat2.Licencjat2.Enums.Category;
import Licencjat2.Licencjat2.SpringSecurityConfig.UserServices;
import Licencjat2.Licencjat2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterController {
    @Autowired
    private MagazynUzytkownika userRepo;
    @Autowired
    private UserServices service;
    @Autowired
    private MagazynZainteresowan mz;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
       // model.addAttribute("user", new Uzytkownik());
        model.addAttribute("hobbyList", Category.values());

        return "registrationForm";
    }
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (service.verify(code)) {
            return "verificationSuccess";
        } else {
            return "verificationFail";
        }
    }
//ToDo finish registration method / new form
    @PostMapping("/processRegister")
    public String processRegister(HttpServletRequest request,@Param("email") String email, @Param("password") String password,
                                  @Param("passwordAgain") String passwordAgain, @Param("name") String name,
                                  @Param("surname") String surname, @Param("gender") String gender,
                                  @Param("country") String country, @Param("city") String city,
                                  @Param("street") String street, @Param("bNumber") String bNumber,
                                  @Param("localNumber") String localNumber,
                                  @RequestParam(required = false, value = "hobby") String[] hobby)
            throws UnsupportedEncodingException, MessagingException {
        Uzytkownik user = new Uzytkownik();
        List<Zainteresowania> zainteresowania = new ArrayList<>();
        user.setNazwisko(surname);
        user.setImie(name);
        user.setPassword(password);
        user.setEmail(email);
        for(int x=0 ; x<hobby.length;x++){
            zainteresowania.add(new Zainteresowania(Category.valueOf(hobby[x]),user));
        }
        user.setZainteresowania(zainteresowania);
        Adres adres = new Adres();
        adres.setUlica(street);
        adres.setNumerLokalu(localNumber);
        adres.setMiasto(city);
        adres.setNumerBudynku(bNumber);
        adres.setKraj(country);


        service.register(user, getSiteURL(request), adres);
        return "registrationSuccessful";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
