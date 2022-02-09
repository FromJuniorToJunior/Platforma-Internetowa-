package Licencjat2.Licencjat2.controllers;

import Licencjat2.Licencjat2.Enums.Category;
import Licencjat2.Licencjat2.Enums.Difficulty;
import Licencjat2.Licencjat2.Enums.Language;
import Licencjat2.Licencjat2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping
public class CoursesController {
    @Autowired
    HttpSession session;
    @Autowired
    MagazynUzytkownika ma;
    @Autowired
    MagazynKursu mk;
    @Autowired
    MagazynKomentarzy mkom;
    @Autowired
    MagazynPlikow mp;
    @Autowired
    EntityManager em;
    @Autowired
    MagazynOceny mo;

    private Long id;


    @GetMapping("course")
    public String coursesMain(@Param("courseId") Long courseId, Model model) {
        this.id = courseId;




        if (AccountController.user == null) {
            AccountController.user = ma.findByEmail(session.getAttribute("usera").toString());
        }


        if (courseId != null) {


            Kurs kursik = mk.znajdz(courseId);
            List<Kurs> kursa = AccountController.user.getKursa();
            List<Kurs> kurs = AccountController.user.getKurs();
            if (!(kursa.contains(kursik) || kurs.contains(kursik))) {
                model.addAttribute("courseId", courseId);
                List<Komentarz> komentarze = mk.znajdz(courseId).getKomentarz();
                System.out.println(komentarze.size());
                model.addAttribute("komentarze", komentarze);

                return "courseRegistration";
            } else {
                Kurs kursFile = mk.znajdz(courseId);
                model.addAttribute("komentarzDoFormy", new Komentarz());
                model.addAttribute("courseId", courseId);
                model.addAttribute("files", kursFile.getFile());
                boolean xd = kursFile.getAutor().equals(AccountController.user);
                model.addAttribute("autor",xd);
                model.addAttribute("name", AccountController.user.getFullName());
                model.addAttribute("liked",!AccountController.user.getOceny().contains(
                        new Oceny(AccountController.user,mk.znajdz(courseId))));
                model.addAttribute("usersContact", mk.znajdz(courseId).getUzytkownicy());
                return "coursehomepage";
            }

        } else {
            model.addAttribute("kursyUsera", AccountController.user.getKurs());
            model.addAttribute("kursyWlasne", AccountController.user.getKursa());
            model.addAttribute("newCourse", new Kurs());
            model.addAttribute("language", Language.values());
            model.addAttribute("category", Category.values());
            model.addAttribute("difficulty", Difficulty.values());
        }

        return "courses";
    }

    @PostMapping("/courseRegistration")
    public RedirectView courseRegistration(@RequestParam Long cc) {
        System.out.println(cc);
        List<Kurs> a = AccountController.user.getKurs();
        List<Uzytkownik> uzytkowniks = mk.znajdz(cc).getUzytkownicy();
        uzytkowniks.add(AccountController.user);
        a.add(mk.znajdz(cc));

        AccountController.user.setKurs(a);
        mk.znajdz(cc).setUzytkownicy(uzytkowniks);

        mk.save(mk.znajdz(cc));


        return new RedirectView("course");
    }

    @PostMapping("/courseRegistrationDecline")
    public RedirectView courseRegistration() {
        return new RedirectView("course");
    }

    @PostMapping("/addNewCourse")
    public RedirectView addNewCourse(@Param("newCourse") Kurs course) {
        List<Kurs> kurs = AccountController.user.getKursa();
        course.setAutor(AccountController.user);
        kurs.add(course);
        //AccountController.user.setKursa(kurs);
        mk.save(course);

        return new RedirectView("course");
    }

    @PostMapping("/addComment")
    public RedirectView addComment(Model model, @Param("komentarzDoFormy") Komentarz komentarz) {
        komentarz.setAutor(AccountController.user);
        komentarz.setKurs(mk.znajdz(this.id));
        AccountController.user.addKoment(komentarz);
        mk.znajdz(this.id).addKoment(komentarz);
        // ma.save(AccountController.user);
        mkom.save(komentarz);
        //  mk.save(mk.znajdz(this.id));


        return new RedirectView("course");
    }

    @PostMapping("deleteCourse")
    public RedirectView deleteCourse(@RequestParam("courseId") Long courseId) {
        Kurs kursDelete = mk.znajdz(courseId);
        //pliki//kom
        List<Komentarz> komDel = mkom.findAll();
        List<File> fileDel = mp.findAll();
        AccountController.user.getKursa().remove(kursDelete);

        for (Komentarz kom : komDel
        ) {
            if (kom.getKurs().getKursId() == courseId) {
                mkom.deleteById(kom.getKomentarzId());
            }

        }
        for (File file : fileDel) {
            if (file.getKurs().getKursId() == courseId) {
                mp.deleteById(file.getFileId());
            }
        }
         mk.deleteById(courseId);


        return new RedirectView("course");
    }

    @GetMapping("like")
    public RedirectView like(@Param("courseId") Long courseId, Model model) {

        Kurs kurs = mk.znajdz(courseId);



        kurs.setSlike(kurs.getSlike()+1);
        Oceny ocena = new Oceny(AccountController.user,kurs);
        AccountController.user.getOceny().add(ocena);
        kurs.getOceny().add(ocena);
        mo.save(ocena);

        return new RedirectView("course?courseId="+courseId);
    }

    @GetMapping("disLike")
    public RedirectView disLike(@Param("courseId") Long courseId, Model model) {

        Kurs kurs = mk.znajdz(courseId);


        kurs.setDisLike(kurs.getDisLike()+1);
        Oceny ocena = new Oceny(AccountController.user,kurs);
        AccountController.user.getOceny().add(ocena);
        kurs.getOceny().add(ocena);
        mo.save(ocena);

        return new RedirectView("course?courseId="+courseId);
    }
}
