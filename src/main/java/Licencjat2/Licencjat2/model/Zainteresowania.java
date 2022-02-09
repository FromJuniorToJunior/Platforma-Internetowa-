package Licencjat2.Licencjat2.model;

import Licencjat2.Licencjat2.Enums.Category;
import javassist.expr.Instanceof;

import javax.persistence.*;

@Entity
@Table(name = "zainteresowania")
public class Zainteresowania {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long zainteresowaniaId;
    @Column(name = "zainteresowanie",nullable = true)
    private Category kategoria;
    @ManyToOne
    @JoinColumn(
            name = "user",
            referencedColumnName = "uzytkownikId"
    )
    private Uzytkownik uzytkownik;
    public Zainteresowania(){}
    public Zainteresowania(Category cat, Uzytkownik user){
        this.uzytkownik=user;
        this.kategoria=cat;
    }



    public Long getZainteresowaniaId() {
        return zainteresowaniaId;
    }

    public void setZainteresowaniaId(Long zainteresowaniaId) {
        this.zainteresowaniaId = zainteresowaniaId;
    }

    public Category getKategoria() {
        return kategoria;
    }

    public void setKategoria(Category kategoria) {
        this.kategoria = kategoria;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Zainteresowania))
            return false;
        Zainteresowania zainteresowania = (Zainteresowania) obj;

        return zainteresowania.kategoria==this.kategoria;
    }
}
