package Licencjat2.Licencjat2.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface MagazynKursu extends JpaRepository<Kurs,Long> {
    @Query("SELECT k FROM Kurs k WHERE k.kursId=?1")
    public Kurs znajdz(Long id);

    @EntityGraph(attributePaths = "uzytkownicy")
    List<Kurs> findAll();

    @Override
    void deleteById(Long aLong);
}
