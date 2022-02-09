package Licencjat2.Licencjat2.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Oceny")
public class Oceny {
    public Oceny(){}
    public Oceny(Uzytkownik user, Kurs kurs){
        this.setUzytkownikOceny(user);
        this.setKursOceny(kurs);

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ocenaId;

    @ManyToOne
    @JoinColumn(
            name = "user",
            referencedColumnName = "uzytkownikId"
    )
    private Uzytkownik uzytkownikOceny;

    @ManyToOne
    @JoinColumn(
            name = "kurs",
            referencedColumnName = "kursId"
    )
    private Kurs kursOceny;

    public Long getOcenaId() {
        return ocenaId;
    }

    public void setOcenaId(Long ocenaId) {
        this.ocenaId = ocenaId;
    }

    public Uzytkownik getUzytkownikOceny() {
        return uzytkownikOceny;
    }

    public void setUzytkownikOceny(Uzytkownik uzytkownikOceny) {
        this.uzytkownikOceny = uzytkownikOceny;
    }

    public Kurs getKursOceny() {
        return kursOceny;
    }

    public void setKursOceny(Kurs kursOceny) {
        this.kursOceny = kursOceny;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Oceny){
            Oceny z = (Oceny) obj;
            if(this.uzytkownikOceny.equals(z.uzytkownikOceny)&&this.kursOceny.equals(z.kursOceny)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }
}
