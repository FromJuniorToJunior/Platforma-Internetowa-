package Licencjat2.Licencjat2.model;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MagazynKomentarzy extends JpaRepository<Komentarz,Long> {
    @Override
    void deleteById(Long aLong);
}
