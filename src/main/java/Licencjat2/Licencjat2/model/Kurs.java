package Licencjat2.Licencjat2.model;

import Licencjat2.Licencjat2.Enums.Category;
import Licencjat2.Licencjat2.Enums.Difficulty;
import Licencjat2.Licencjat2.Enums.Language;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "kurs")
public class Kurs implements Comparable<Kurs> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kursId;
    @Column(nullable = false,length = 100)
    private String nazwa;
    @Column(nullable = false)
    private Category kategoria;
    @Column(nullable = false)
    private Language jezyk;
    @Column(nullable = false)
    private Difficulty poziomTrudnosci;
    @Lob
    private byte[] data;
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer disLike;
    @Column(nullable = false, columnDefinition = "int default 0")
    private int slike;

    @Override
    public int compareTo(Kurs o) {
        if (slike==o.slike && Math.abs(disLike-o.disLike)>0&&Math.abs(disLike-o.disLike)<100){
            return 0;
        }else if((slike!=0||disLike!=0)&&(double)slike/((double)slike+disLike)>o.slike/(o.slike+o.disLike)){
            return -1;
        }else{
            return 1;
        }
    }

    @OneToMany(mappedBy = "kursOceny")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Oceny> oceny;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "user",
            referencedColumnName = "uzytkownikId"
    )
    private Uzytkownik autor;
    @ManyToMany(
            cascade={
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "kursy_uzytkownicy",
            joinColumns = @JoinColumn(name="kursId"),
            inverseJoinColumns = @JoinColumn(name="uzytkownikId")
    )
    private List<Uzytkownik> uzytkownicy;
    @OneToMany(mappedBy = "kurs")
    private List<Komentarz> komentarz;
    @OneToMany(mappedBy = "kurs")
    private List<File> file;


    private int idx;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public List<File> getFile() {
        return file;
    }

    public void setFile(List<File> file) {
        this.file = file;
    }

    public Integer getDisLike() {
        return disLike;
    }

    public void setDisLike(Integer disLike) {
        this.disLike = disLike;
    }

    public int getSlike() {
        return slike;
    }

    public void setSlike(int slike) {
        this.slike = slike;
    }

    public Long getKursId() {
        return kursId;
    }

    public void setKursId(Long kursId) {
        this.kursId = kursId;
    }

    public Category getKategoria() {
        return kategoria;
    }

    public void setKategoria(Category kategoria) {
        this.kategoria = kategoria;
    }

    public Language getJezyk() {
        return jezyk;
    }

    public void setJezyk(Language jezyk) {
        this.jezyk = jezyk;
    }

    public Difficulty getPoziomTrudnosci() {
        return poziomTrudnosci;
    }

    public void setPoziomTrudnosci(Difficulty poziomTrudnosci) {
        this.poziomTrudnosci = poziomTrudnosci;
    }

    public Uzytkownik getAutor() {
        return autor;
    }

    public List<Oceny> getOceny() {
        return oceny;
    }

    public void setOceny(List<Oceny> oceny) {
        this.oceny = oceny;
    }

    public void setAutor(Uzytkownik autor) {
        this.autor = autor;
    }

    public List<Uzytkownik> getUzytkownicy() {
        return uzytkownicy;
    }

    public void setUzytkownicy(List<Uzytkownik> uzytkownicy) {
        this.uzytkownicy = uzytkownicy;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public List<Komentarz> getKomentarz() {
        return komentarz;
    }

    public void setKomentarz(List<Komentarz> komentarz) {
        this.komentarz = komentarz;
    }
    public void addKoment(Komentarz komentarz) {
        this.komentarz.add(komentarz);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Kurs))
            return false;
        Kurs z = (Kurs)obj;


        if((z.kursId==this.kursId||this.kursId==null)&&
                (z.nazwa.equals(this.nazwa)||this.nazwa.equals(""))&&
                (this.jezyk==(z.jezyk)||this.jezyk==null)&&
                (this.kategoria==z.kategoria||this.kategoria==null)&&
                (Objects.equals(this.autor.getImie(),z.autor.getImie())||this.autor.getImie()==null)&&
                (Objects.equals(this.autor.getNazwisko(),z.autor.getNazwisko())||this.autor.getNazwisko()==null)&&
                (this.poziomTrudnosci==(z.poziomTrudnosci)||this.poziomTrudnosci==null))
            return true;
        else
            return false;
        /*return this.kursId.equals(z.kursId);*/
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
