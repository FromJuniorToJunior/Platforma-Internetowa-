package Licencjat2.Licencjat2.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="przedmioty")
public class Przedmioty {
    public Przedmioty(){/*konstruktor*/}

    public Przedmioty(Long przedmiotyId, String nazwa, List<Uzytkownik> uzytkownik) {
        super();
        this.przedmiotyId = przedmiotyId;
        this.nazwa = nazwa;
        this.uzytkownik = uzytkownik;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long przedmiotyId;
    @Column(nullable = false, length = 70)
    private String nazwa;
    @ManyToMany(mappedBy = "przedmioty")
    private List<Uzytkownik> uzytkownik;

    public Long getPrzedmiotyId() {
        return przedmiotyId;
    }

    public void setPrzedmiotyId(Long przedmiotyId) {
        this.przedmiotyId = przedmiotyId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public List<Uzytkownik> getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(List<Uzytkownik> uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
}
