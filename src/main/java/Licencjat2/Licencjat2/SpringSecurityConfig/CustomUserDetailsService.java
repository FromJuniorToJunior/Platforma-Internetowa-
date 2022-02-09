package Licencjat2.Licencjat2.SpringSecurityConfig;

import Licencjat2.Licencjat2.model.MagazynUzytkownika;
import Licencjat2.Licencjat2.model.Uzytkownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpSession;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    MagazynUzytkownika userRepo;
    @Autowired
    HttpSession session;


    public CustomUserDetailsService() {

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Uzytkownik user = userRepo.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("Użytkownik nie istnieje");
        }
        session.setAttribute("usera",s);
        return new CustomUserDetails(user);
    }
}
