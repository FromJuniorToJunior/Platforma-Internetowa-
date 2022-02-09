package Licencjat2.Licencjat2.controllers;

import Licencjat2.Licencjat2.Enums.Category;
import Licencjat2.Licencjat2.Enums.Difficulty;
import Licencjat2.Licencjat2.Enums.Language;
import Licencjat2.Licencjat2.model.*;
import Licencjat2.Licencjat2.supportiveClasses.PageIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.*;

@Controller
@RequestMapping("/")
public class MainAppController {

    @Autowired
    DataSource dataSource;
    @Autowired
    MagazynUzytkownika mu;
    @Autowired
    MagazynKursu ku;
    @Autowired
    HttpSession session;
    @Autowired
    MagazynContentu mc;

    List<Kurs> xxx;


    @GetMapping("/")
    public String homePage(@Param("index") Long index, Model model) {
        model.addAttribute("content", mc.getById(3L).getContent());
        if (index != null) {
            model.addAttribute("content", mc.getById(index).getContent());
        }
        return "homepage";
    }


    @GetMapping("/main")
    public String mainPage(Model model, @Param("page") String page) {
        Uzytkownik user = mu.findByEmail(session.getAttribute("usera").toString());
        List<Kurs> kursy = new ArrayList<>();
        LinkedList<Kurs> personalRecommendation = new LinkedList<>();
        // List<Kurs> kursAll = new ArrayList<>();
        //  kursAll=ku.findAll();
        // model.addAttribute("kursAll",kursAll);


        if (session.getAttribute("set") == "true" && xxx.size() > 0) {
            kursy = xxx;
        } else {
            kursy = ku.findAll();
        }
       /* for (int x = 0; x < kursy.size(); x++) {
            kursy.get(x).setIdx(x);
        }*/
        Collections.sort(kursy);
        //ToDo recommended courses
        Random rand = new Random();

        Kurs prot = new Kurs();
        int hobbys = user.getZainteresowania().size();
        if (hobbys != 0) {
            prot.setKategoria(user.getZainteresowania().get(rand.nextInt(hobbys)).getKategoria());
        }
        prot.setAutor(new Uzytkownik());
        prot.setNazwa("");
        prot.setJezyk(user.getJezyk());

        for (Kurs kurs : kursy) {
            if (prot.equals(kurs)) {
                personalRecommendation.add(kurs);
            }
        }
        if (personalRecommendation.size() < 4) {
            prot.setJezyk(user.getJezykDrugi());
            for (Kurs kurs : kursy) {
                if (prot.equals(kurs)) {
                    if (!personalRecommendation.contains(kurs))
                        personalRecommendation.add(kurs);
                }
            }
        }
        if (personalRecommendation.size() < 4) {
            Kurs tKurs = kursy.get(0);
            for (int x = 1; x < kursy.size(); x++) {
                if (tKurs.getSlike() < kursy.get(x).getSlike()) {
                    tKurs = kursy.get(x);
                }
            }
            personalRecommendation.add(tKurs);
        }
        while (personalRecommendation.size() > 4) {
            personalRecommendation.remove(rand.nextInt(personalRecommendation.size()));
        }
        model.addAttribute("rec", personalRecommendation);

        //Todo Paginacja
        int numberOfPages;
        List<PageIndex> pages = new ArrayList<>();
        List<Kurs> display = new ArrayList<>();
        if (kursy.size() % 8 != 0) {
            numberOfPages = (kursy.size() / 8) + 1;
        } else {
            numberOfPages = kursy.size() / 8;
        }
        for (int x = 0; x < numberOfPages; x++) {
            pages.add(new PageIndex("/main?page=" + x, x));
        }
        model.addAttribute("pages", pages);

        if (page != null) {
            if (kursy.size() % 8 == 0) {
                for (int x = Integer.parseInt(page) * 8; x < (x + 1) * 8; x++) {
                    display.add(kursy.get(x));
                }
            }else{
                if(Integer.parseInt(page)==0){
                    for(int x=0;x<8;x++){
                        display.add(kursy.get(x));
                    }
                }else if(Integer.parseInt(page)>0&&Integer.parseInt(page)<(pages.size()-1)){
                    for (int x = Integer.parseInt(page) * 8; x < Integer.parseInt(page)*8+8; x++) {
                       display.add(kursy.get(x));
                    }

                }else{
                    for (int x = Integer.parseInt(page) * 8; x < Integer.parseInt(page)*8+(kursy.size()%8); x++) {
                        display.add(kursy.get(x));
                    }

                }
            }
            for(int x=0;x< display.size();x++){
                display.get(x).setIdx(x);
            }
        }else{
            for(int x=0;x<8;x++){
                display.add(kursy.get(x));
                display.get(x).setIdx(x);
            }
        }


       /* List<PageIndex> pagesList = new ArrayList<>();
        List<Kurs> kursToDisplay = new ArrayList<>();
        int rowsNumber = kursy.size();
        int pages;
        if (rowsNumber % 5 != 0)
            pages = (rowsNumber / 5) + 1;
        else
            pages = rowsNumber / 5;

        for (int x = 1; x <= pages; x++) {
            pagesList.add(new PageIndex("main?page=" + x, x));
        }
        if (page != null) {
            if (Integer.parseInt(page) == 1 && pages == 1) {
                for (int x = 0; x < rowsNumber; x++) {
                    kursToDisplay.add(kursy.get(x));
                }
            } else if (Integer.parseInt(page) == 1 && pages > 1) {

                for (int x = 0; x < 5; x++) {
                    kursToDisplay.add(kursy.get(x));
                }

            } else {
                if (Integer.parseInt(page) == pages) {

                    int lastNumber = Integer.parseInt((rowsNumber + "").substring((rowsNumber + "").length() - 1, (rowsNumber + "").length()));
                    // System.out.println(lastNumber);
                    if (lastNumber > 5)
                        lastNumber = lastNumber - 5;

                    for (int x = 5 * (Integer.parseInt(page) - 1); x < 5 * (Integer.parseInt(page) - 1) + lastNumber; x++) {
                        kursToDisplay.add(kursy.get(x));
                    }

                } else {
                    for (int x = 5 * (Integer.parseInt(page) - 1); x < 5 * Integer.parseInt(page); x++) {
                        kursToDisplay.add(kursy.get(x));
                    }
                }
            }
        } else {
            if (pages == 1) {
                for (int x = 0; x < rowsNumber; x++) {
                    kursToDisplay.add(kursy.get(x));
                }
            } else {
                for (int x = 0; x < 5; x++) {
                    kursToDisplay.add(kursy.get(x));
                }
            }

        }
*/

        model.addAttribute("usera", user);
        //  model.addAttribute("kursy", kursToDisplay);
        //  model.addAttribute("pagesList", pagesList);
       // model.addAttribute("kursy", kursy);
        model.addAttribute("kursy", display);
        model.addAttribute("language", Language.values());
        model.addAttribute("category", Category.values());
        model.addAttribute("difficulty", Difficulty.values());
        model.addAttribute("kursSearch", new Kurs());
        //  System.out.println(session.getAttribute("usera").toString());

        return "main";
    }

    @PostMapping("/search")
    public RedirectView search(Model model, @Param("kursSearch") Kurs kurs, @RequestParam("imie") String imie,
                               @RequestParam("nazwisko") String nazwisko, @RequestParam("jezyka") String jezyk,
                               @RequestParam("kategoriaa") String kategoria,
                               @RequestParam("poziomTrudnoscia") String poziomTrudnosci) {
        List<Kurs> kursy = ku.findAll();
        List<Kurs> kursDoSesji = new ArrayList<>();


        /*if (!id.equals(""))
            kurs.setKursId(Long.parseLong(id));*/
        if (!jezyk.equals("null"))
            kurs.setJezyk(Language.valueOf(jezyk));
        if (!kategoria.equals("null")) {
            kurs.setKategoria(Category.valueOf(kategoria));
        }
        if (!poziomTrudnosci.equals("null"))
            kurs.setPoziomTrudnosci(Difficulty.valueOf(poziomTrudnosci));
        kurs.setAutor(new Uzytkownik());
        if (!imie.equals(""))
            kurs.getAutor().setImie(imie);
        if (!nazwisko.equals(""))
            kurs.getAutor().setNazwisko(nazwisko);


        if (kursy.contains(kurs)) {
            for (Kurs kursA : kursy) {
                if (kurs.equals(kursA)) {
                    kursDoSesji.add(kursA);
                }
            }
        }

        //  session.setAttribute("lista", kursDoSesji);
        this.xxx = kursDoSesji;
        session.setAttribute("set", "true");
        return new RedirectView("main");
    }


}
