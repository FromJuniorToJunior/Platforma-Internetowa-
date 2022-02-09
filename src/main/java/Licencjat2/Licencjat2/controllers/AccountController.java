package Licencjat2.Licencjat2.controllers;

import Licencjat2.Licencjat2.Enums.Category;
import Licencjat2.Licencjat2.Enums.Language;
import Licencjat2.Licencjat2.model.*;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping
public class AccountController {
    @Autowired
    HttpSession session;
    @Autowired
    MagazynUzytkownika ma;
    @Autowired
    MagazynZainteresowan mz;
    @Autowired
    MagazynAdresu madd;

    public static Uzytkownik user;


    @GetMapping("account")
    public String account(Model model) {
       // if (user == null) {
            user = ma.findByEmail(session.getAttribute("usera").toString());
       // }
        model.addAttribute("usera", user);
        model.addAttribute("lang", Language.values());
        model.addAttribute("hobby", Category.values());
        return "account";
    }

    @PostMapping("/setLanguage")
    public RedirectView setLanguage(@ModelAttribute("languageFromForm") Language language) {
        user.setJezyk(language);
        ma.save(user);
        return new RedirectView("account");
    }

    @PostMapping("/setSecondLanguage")
    public RedirectView setSecondLanguage(Model model, @ModelAttribute("languageFromForm") Language language) {
        user.setJezykDrugi(language);
        ma.save(user);
        return new RedirectView("account");
    }

    @PostMapping("/setHobby")
    public RedirectView setHobby(@ModelAttribute("hobby") Category category) {
        Zainteresowania test = new Zainteresowania();
                test.setKategoria(category);
        if (!user.getZainteresowania().contains(test)) {
            Zainteresowania zaint = new Zainteresowania();
            zaint.setKategoria(category);
            zaint.setUzytkownik(user);
            user.getZainteresowania().add(zaint);

            mz.save(zaint);
        }
        return new RedirectView("account");
    }

    @PostMapping("/setAddress")
    public RedirectView setAddres(@ModelAttribute("usera") Uzytkownik usera) {
        user.getAdres().setKraj(usera.getAdres().getKraj());
        user.getAdres().setMiasto(usera.getAdres().getMiasto());
        user.getAdres().setUlica(usera.getAdres().getUlica());
        user.getAdres().setNumerBudynku(usera.getAdres().getNumerBudynku());
        user.getAdres().setNumerLokalu(usera.getAdres().getNumerLokalu());

        madd.save(user.getAdres());

        return new RedirectView("account");
    }
    @PostMapping("/addUserImage")
    public RedirectView uploadImage(@RequestParam("file") MultipartFile file){
        try {
            AccountController.user.setData(file.getBytes());

            ma.save(AccountController.user);
        }catch (Exception e){e.printStackTrace();}

        return new RedirectView("/account");

    }

}
