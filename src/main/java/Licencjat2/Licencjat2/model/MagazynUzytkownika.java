package Licencjat2.Licencjat2.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MagazynUzytkownika extends JpaRepository<Uzytkownik,Long> {
    @Query("SELECT u FROM Uzytkownik u WHERE u.email = ?1")
    public Uzytkownik findByEmail(String email);
    @Query("SELECT u FROM Uzytkownik u WHERE u.verificationCode = ?1")
    public Uzytkownik findByVerificationCode(String code);


}
