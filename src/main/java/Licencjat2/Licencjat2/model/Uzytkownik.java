package Licencjat2.Licencjat2.model;

import Licencjat2.Licencjat2.Enums.Language;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "uzytkownik")
public class Uzytkownik {

    public Uzytkownik() {
        this.enabled = false;

    }

    public Uzytkownik(Long uzytkownikId, String imie, String nazwisko, String email, String password, Adres adres, List<Przedmioty> przedmioty) {
        this.uzytkownikId = uzytkownikId;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.password = password;
        this.adres = adres;
        this.przedmioty = przedmioty;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uzytkownikId;
    @Column(nullable = false, length = 60)
    private String imie;
    @Column(nullable = false, length = 60)
    private String nazwisko;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, length = 64)
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adres_id", referencedColumnName = "adresId")
    private Adres adres;
    @OneToMany(mappedBy = "autor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Kurs> kursa;
    @OneToMany(mappedBy = "uzytkownik")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Zainteresowania> zainteresowania;
    @OneToMany(mappedBy = "uzytkownikOceny")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Oceny> oceny;
    @ManyToMany(
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "uzytkownicy_przedmioty",
            joinColumns = @JoinColumn(name = "uzytkownikId"),
            inverseJoinColumns = @JoinColumn(name = "przedmiotId")
    )
    private List<Przedmioty> przedmioty;
    @ManyToMany(mappedBy = "uzytkownicy")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Kurs> kurs;
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    @Column(name = "language", nullable = true)
    private Language jezyk;
    @Column(name = "supportiveLanguage", nullable = true)
    private Language jezykDrugi;
    @Column(name = "img", columnDefinition = "varchar(255) default 'images/usersImages/defaultUser.png'")
    private String obraz;
    @Lob
    private byte[] data;

    //ToDo uzytkownik-komentarz
    @OneToMany(mappedBy = "autor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Komentarz> komentarz;

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUzytkownikId() {
        return uzytkownikId;
    }

    public void setUzytkownikId(Long uzytkownikId) {
        this.uzytkownikId = uzytkownikId;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<Przedmioty> getPrzedmioty() {
        return przedmioty;
    }

    public void setPrzedmioty(List<Przedmioty> przedmioty) {
        this.przedmioty = przedmioty;
    }

    public List<Kurs> getKursa() {
        return kursa;
    }

    public void setKursa(List<Kurs> kursa) {
        this.kursa = kursa;
    }

    public List<Kurs> getKurs() {
        return kurs;
    }

    public void setKurs(List<Kurs> kurs) {
        this.kurs = kurs;
    }

    public Language getJezyk() {
        return jezyk;
    }

    public void setJezyk(Language jezyk) {
        this.jezyk = jezyk;
    }

    public String getObraz() {
        return obraz;
    }

    public void setObraz(String obraz) {
        this.obraz = obraz;
    }

    public Language getJezykDrugi() {
        return jezykDrugi;
    }

    public void setJezykDrugi(Language jezykDrugi) {
        this.jezykDrugi = jezykDrugi;
    }

    public List<Zainteresowania> getZainteresowania() {
        return zainteresowania;
    }

    public void setZainteresowania(List<Zainteresowania> zainteresowania) {
        this.zainteresowania = zainteresowania;
    }

    public List<Komentarz> getKomentarz() {
        return komentarz;
    }

    public List<Oceny> getOceny() {
        return oceny;
    }

    public void setOceny(List<Oceny> oceny) {
        this.oceny = oceny;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setKomentarz(List<Komentarz> komentarz) {
        this.komentarz = komentarz;
    }
    public void addKoment(Komentarz komentarz) {
        this.komentarz.add(komentarz);
    }

    public String getFullName() {
        return getImie() + " " + getNazwisko();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Uzytkownik))
            return false;
        Uzytkownik z =(Uzytkownik) obj;

        return this.uzytkownikId.equals(z.uzytkownikId);
    }
    public String getImgData() {
        return Base64.getMimeEncoder().encodeToString(this.data);
    }
    public boolean check(){
        if(data!=null)
            return true;
        else
            return false;
    }
}
