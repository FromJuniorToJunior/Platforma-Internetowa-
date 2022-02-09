package Licencjat2.Licencjat2.model;

import javax.persistence.*;

@Entity
@Table(name = "komentarz")
public class Komentarz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long komentarzId;
    @Column(nullable = false)
    private String koment;
    //ToDo relacje komentarz/user/kurs
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "uzytkownikId",
            referencedColumnName = "uzytkownikId"
    )
    private Uzytkownik autor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "kursId",
            referencedColumnName = "kursId"
    )
    private Kurs kurs;

    public Long getKomentarzId() {
        return komentarzId;
    }

    public void setKomentarzId(Long komentarzId) {
        this.komentarzId = komentarzId;
    }

    public String getKomentarz() {
        return koment;
    }

    public void setKomentarz(String komentarz) {
        this.koment = komentarz;
    }

    public Uzytkownik getAutor() {
        return autor;
    }

    public void setAutor(Uzytkownik autor) {
        this.autor = autor;
    }

    public Kurs getKurs() {
        return kurs;
    }

    public void setKurs(Kurs kurs) {
        this.kurs = kurs;
    }

    public String getKoment() {
        return koment;
    }

    public void setKoment(String koment) {
        this.koment = koment;
    }
}
